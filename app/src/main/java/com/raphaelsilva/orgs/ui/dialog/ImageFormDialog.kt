package com.raphaelsilva.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.raphaelsilva.orgs.databinding.DialogAddImageBinding
import com.raphaelsilva.orgs.extensions.loadImage

class ImageFormDialog(private val context: Context) {

    fun show(imageUrl: String? = null, whenLoadImage: (image: String) -> Unit) {
        DialogAddImageBinding.inflate(LayoutInflater.from(context)).apply {
            val loadingButton = refreshImageDialog
            val urlInput = urlImageInputDialog
            val imageView = dialogAddImage

            imageUrl.let {
                urlInput.editText?.setText(it)
                imageView.loadImage((it))
            }

            loadingButton.setOnClickListener {
                imageView.loadImage(urlInput.editText?.text.toString())
            }

            AlertDialog.Builder(context)
                .setView(root)
                .setNegativeButton("Cancelar") { _, _ ->
                }
                .setPositiveButton("Aplicar") { _, _ ->
                    val url = urlInput.editText?.text.toString()
                    whenLoadImage(url)
                }
                .show()
        }
    }
}