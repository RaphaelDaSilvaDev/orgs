package com.raphaelsilva.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.raphaelsilva.orgs.R
import com.raphaelsilva.orgs.database.AppDatabase
import com.raphaelsilva.orgs.databinding.ActivityProductFormBinding
import com.raphaelsilva.orgs.exeptions.CoroutineException
import com.raphaelsilva.orgs.extensions.loadImage
import com.raphaelsilva.orgs.model.Product
import com.raphaelsilva.orgs.ui.dialog.ImageFormDialog
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ProductFormActivity : AppCompatActivity(R.layout.activity_product_form) {
    private val binding by lazy {
        ActivityProductFormBinding.inflate(layoutInflater)
    }

    private val daoProduct by lazy {
        AppDatabase.instance(this).productDao()
    }

    private var url: String? = null
    private var productUid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar Produto"

        val productUID = intent.getIntExtra(PRODUCT_UID_KEY, 0)

        lifecycleScope.launch(CoroutineException.handler(this)) {
            daoProduct.getProductById(productUID).collect { product ->
                if (product != null) {
                    productUid = product.uid
                    title = "Editar Produto"
                    bindingProduct(product)
                }
            }
        }

        onSave()
        bindingImage()
    }

    private fun onSave() {
        val formButtonAdd = binding.formButtonAdd
        formButtonAdd.setOnClickListener {
            val newProduct = product()
            lifecycleScope.launch(CoroutineException.handler(this)) {
                daoProduct.save(newProduct)
                finish()
            }
        }

    }

    private fun bindingProduct(product: Product) {
        binding.titleInput.editText?.setText(product.title)
        binding.descriptionInput.editText?.setText(product.description)
        binding.priceInput.editText?.setText(product.price.toPlainString())
        binding.imageInput.loadImage(product.image)
        binding.formButtonAdd.text = "Atualizar Produto"
        url = product.image
    }

    private fun bindingImage() {
        val imageInput = binding.imageInput
        imageInput.setOnClickListener {
            ImageFormDialog(this).show(url) { image ->
                imageInput.loadImage(image)
                url = image
            }
        }
    }

    private fun product(): Product {
        val titleValue = binding.titleInput.editText?.text.toString()
        val descriptionValue = binding.descriptionInput.editText?.text.toString()
        val priceValue = binding.priceInput.editText?.text.toString()
        val imageValue = url

        val price = if (priceValue.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(priceValue)
        }

        return Product(
            uid = productUid,
            title = titleValue,
            description = descriptionValue,
            price = price,
            image = imageValue
        )
    }
}