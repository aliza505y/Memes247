package com.example.memes247

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.memes247.databinding.FragmentShuffleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStream

class ShuffleFragment : Fragment() {

    private var _binding: FragmentShuffleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShuffleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadRandomMeme()

        binding.btnRefresh.setOnClickListener {
            loadRandomMeme()
        }

        binding.btnDownload.setOnClickListener {
            saveMemeToGallery()
        }
    }

    private fun loadRandomMeme() {
        // 1. Show the loading bar before we do anything
        binding.progressBar.visibility = View.VISIBLE
        binding.imageView.setImageDrawable(null) // Clear previous image

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getRandomMeme()
                
                withContext(Dispatchers.Main) {
                    binding.memeName.text = response.title
                    
                    binding.imageView.load(response.url) {
                        // CROSSFADE REMOVED to fix the download bug!
                        
                        // 2. Hide the loading bar only when the image succeeds or fails
                        listener(
                            onSuccess = { _, _ ->
                                binding.progressBar.visibility = View.GONE
                            },
                            onError = { _, _ ->
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    // Hide progress bar if the API call itself fails
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Failed to fetch meme", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveMemeToGallery() {
        val drawable = binding.imageView.drawable
        
        // This check will now pass because crossfade is gone!
        if (drawable !is BitmapDrawable) {
            Toast.makeText(requireContext(), "Image not ready yet!", Toast.LENGTH_SHORT).show()
            return
        }
        val bitmap = drawable.bitmap

        val filename = "Meme_${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        var imageUri: Uri? = null
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                put(MediaStore.MediaColumns.IS_PENDING, 1) 
            }
        }

        val contentResolver = requireContext().contentResolver
        try {
            contentResolver.also { resolver ->
                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }

            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                Toast.makeText(requireContext(), "Meme saved to Gallery!", Toast.LENGTH_SHORT).show()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                imageUri?.let { contentResolver.update(it, contentValues, null, null) }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to save meme", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
