package com.raphaelsilva.orgs.extensions

import android.widget.ImageView
import coil.load
import com.raphaelsilva.orgs.R

fun ImageView.loadImage(url: String? = null) {
    load(url) {
        fallback(R.drawable.imagem_padrao)
        error(R.drawable.imagem_padrao)
        placeholder(R.drawable.imagem_padrao)
    }
}