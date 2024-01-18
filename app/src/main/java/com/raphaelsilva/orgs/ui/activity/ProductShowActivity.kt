package com.raphaelsilva.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.raphaelsilva.orgs.R
import com.raphaelsilva.orgs.database.AppDatabase
import com.raphaelsilva.orgs.databinding.ActivityShowProductBinding
import com.raphaelsilva.orgs.exeptions.CoroutineException
import com.raphaelsilva.orgs.extensions.convertToBRLCurrency
import com.raphaelsilva.orgs.extensions.loadImage
import com.raphaelsilva.orgs.model.Product
import kotlinx.coroutines.launch

class ProductShowActivity : AppCompatActivity() {
    private lateinit var product: Product

    private val binding by lazy {
        ActivityShowProductBinding.inflate(layoutInflater)
    }

    private val daoProduct by lazy {
        AppDatabase.instance(this).productDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val productUID = intent.getIntExtra(PRODUCT_UID_KEY, 0)

        lifecycleScope.launch(CoroutineException.handler(this)) {
            daoProduct.getProductById(productUID).collect { loadedProduct ->
                if (loadedProduct != null) {
                    product = loadedProduct
                    bindingFields(loadedProduct)
                } else {
                    finish()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.show_item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (::product.isInitialized) {

            when (item.itemId) {
                R.id.show_item_menu_edit -> {
                    Intent(this, ProductFormActivity::class.java).apply {
                        putExtra(PRODUCT_UID_KEY, product.uid)
                        startActivity(this)
                    }
                }

                R.id.show_item_menu_remove -> {
                    lifecycleScope.launch(CoroutineException.handler(this)) {
                        daoProduct.delete(product)
                        finish()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindingFields(product: Product) {
        with(binding) {
            showProductTitle.text = product.title
            showProductDescription.text = product.description
            showProductPrice.text = product.price.convertToBRLCurrency()
            showProductImage.loadImage(product.image)
        }
    }
}