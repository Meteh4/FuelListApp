package com.metoly.roomtest.Fragments

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.metoly.roomtest.Model.Fuel.Fuel
import com.metoly.roomtest.ViewModel.FuelViewModel
import com.metoly.roomtest.ViewModel.FuelViewModelFactory
import com.metoly.roomtest.databinding.FragmentAddEditFuelBinding
import java.io.ByteArrayOutputStream

class AddEditFuelFragment : Fragment() {

    private var _binding: FragmentAddEditFuelBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FuelViewModel by viewModels { FuelViewModelFactory(requireActivity().application) }
    private var fuelId: Int? = null
    private var photoByteArray: ByteArray? = null

    private val takePhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val data: android.content.Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as? android.graphics.Bitmap
            imageBitmap?.let {

                val outputStream = ByteArrayOutputStream()
                imageBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, outputStream)
                photoByteArray = outputStream.toByteArray()

                binding.imageView.setImageBitmap(imageBitmap)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditFuelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { args ->
            val price = args.getDouble("price", 0.0)
            val type = args.getString("type", "")

            binding.editTextPrice.setText(price.toString())
            binding.editTextType.setText(type.toString())
        }

        val args = arguments
        if (args != null && args.containsKey("fuel_id")) {
            fuelId = args.getInt("fuel_id")
            if (fuelId != null) {
                viewModel.getFuelById(fuelId!!).observe(viewLifecycleOwner) { fuel ->
                    binding.editTextPrice.setText(fuel.price.toString())
                    binding.editTextLiter.setText(fuel.liter.toString())

                    fuel.photo?.let { photoByteArray ->
                        val bitmap = android.graphics.BitmapFactory.decodeByteArray(photoByteArray, 0, photoByteArray.size)
                        binding.imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }

        binding.buttonTakePhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

        binding.buttonSave.setOnClickListener {
            val price = binding.editTextPrice.text.toString().toDoubleOrNull()
            val liter = binding.editTextLiter.text.toString().toDoubleOrNull()
            val type = binding.editTextType.text.toString()
            if (price != null && liter != null) {
                val totalPrice = price * liter

                if (fuelId != null) {
                    val updatedFuel = Fuel(id = fuelId!!, price = price, liter = liter, totalPrice = totalPrice, photo = photoByteArray, type = type)
                    viewModel.update(updatedFuel)
                } else {
                    val newFuel = Fuel(price = price, liter = liter, totalPrice = totalPrice, photo = photoByteArray, type= type)
                    viewModel.insert(newFuel)
                }

                findNavController().navigateUp()
                showToast("Fuel data saved successfully!")
            } else {
                showToast("Please enter valid price and liter values!")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = android.content.Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhoto.launch(takePictureIntent)
    }
}
