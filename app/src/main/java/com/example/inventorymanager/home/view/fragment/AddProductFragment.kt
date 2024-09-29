package com.example.inventorymanager.home.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inventorymanager.databinding.FragmentAddProductBinding
import com.example.inventorymanager.home.model.Product
import com.example.inventorymanager.common.NavigationHelper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.UUID

class AddProductFragment : Fragment() {

    lateinit var binding : FragmentAddProductBinding

    private lateinit var navigationHelper: NavigationHelper
    private lateinit var profileImageView: CircleImageView
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private var selectedImageUri: Uri? = null
    private lateinit var databaseReference: DatabaseReference

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigationHelper = NavigationHelper(findNavController())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductBinding.inflate(layoutInflater)

        val profileImageView: CircleImageView = binding.profileImage


        binding.iconSelectUnit.setOnClickListener { view ->
            // Create a PopupMenu
            val popupMenu = PopupMenu(binding.root.context, view)

            // Inflate the menu resource with Edit and View options
            popupMenu.menu.add(0, 1, 0, "ML")
            popupMenu.menu.add(0, 2, 1, "KG")
            popupMenu.menu.add(0, 3, 2, "Units")


            // Set a click listener for menu items
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    1 -> {
                        Toast.makeText(binding.root.context, "ML clicked", Toast.LENGTH_SHORT).show()
                        binding.iconSelectUnit.text = "ML"
                        true
                    }

                    2 -> {
                        Toast.makeText(binding.root.context, "KG clicked", Toast.LENGTH_SHORT).show()
                        binding.iconSelectUnit.text = "KG"
                        true
                    }

                    3 -> {
                        Toast.makeText(binding.root.context, "Units clicked", Toast.LENGTH_SHORT).show()
                        binding.iconSelectUnit.text = "Units"
                        true
                    }

                    else -> false
                }
            }
            popupMenu.show()
        }

        profileImageView.setOnClickListener {
            openImagePicker()
        }

        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri: Uri? = data?.data

                // Display the selected image in CircleImageView
                selectedImageUri?.let {
                    val inputStream = requireContext().contentResolver.openInputStream(it)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    profileImageView.setImageBitmap(bitmap)
                }
            }
        }

        binding.btnAdd.setOnClickListener {
            saveProduct()
        }

        return binding.root
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        imagePickerLauncher.launch(intent)
    }

    private fun saveProduct() {
        val productName = binding.etCompanyName.text.toString()
        val stock = binding.stock.editText?.text.toString().toIntOrNull() ?: 0
        val unit = binding.iconSelectUnit.text.toString()
        val category = binding.etLastName.text.toString()
        val price = binding.etAlias.text.toString().toDoubleOrNull() ?: 0.0

        if (productName.isNotEmpty()) {
            uploadImageToFirebase { imageUrl ->
                val productId = UUID.randomUUID().toString()
                val product = Product(productId, productName, stock, unit, category, price, imageUrl)
                databaseReference.child(productId).setValue(product).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Product saved successfully", Toast.LENGTH_SHORT).show()
                        // Navigate back or clear the form
                    } else {
                        Toast.makeText(context, "Failed to save product", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(context, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImageToFirebase(callback: (String) -> Unit) {
        selectedImageUri?.let { uri ->
            val storageReference = FirebaseStorage.getInstance().getReference("ProductImages/${UUID.randomUUID()}")
            storageReference.putFile(uri).addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener { imageUrl ->
                    callback(imageUrl.toString())
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show()
            }
        }
    }
}