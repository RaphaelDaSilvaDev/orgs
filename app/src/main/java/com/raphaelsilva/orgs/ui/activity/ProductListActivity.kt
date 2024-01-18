package com.raphaelsilva.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.raphaelsilva.orgs.database.AppDatabase
import com.raphaelsilva.orgs.databinding.ActivityProductsListBinding
import com.raphaelsilva.orgs.exeptions.CoroutineException
import com.raphaelsilva.orgs.ui.recyclerview.adapter.ProductListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListActivity : AppCompatActivity() {
    private val adapter = ProductListAdapter(context = this)

    private val binding by lazy {
        ActivityProductsListBinding.inflate(layoutInflater)
    }

    private val daoProducts by lazy {
        val db = AppDatabase.instance(this)
        db.productDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        settingsRecyclerView()
        settingsFab()
        lifecycleScope.launch(CoroutineException.handler(this)) {
            daoProducts.getAll().collect{product ->
                adapter.update(product)
            }
        }
    }

    private fun settingsFab() {
        val buttonAdd = binding.buttonAdd
        buttonAdd.setOnClickListener {
            openProductForm()
        }
    }

    private fun openProductForm() {
        val intent = Intent(this, ProductFormActivity::class.java)
        startActivity(intent)
    }

    private fun settingsRecyclerView() {
        val itemsList = binding.itemsList
        itemsList.adapter = adapter
        adapter.itemClick = {
            val intent = Intent(this, ProductShowActivity::class.java).apply {
                putExtra(PRODUCT_UID_KEY, it.uid)
            }
            startActivity(intent)
        }
    }
}