package com.raphaelsilva.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raphaelsilva.orgs.database.AppDatabase
import com.raphaelsilva.orgs.databinding.ActivityProductsListBinding
import com.raphaelsilva.orgs.ui.recyclerview.adapter.ProductListAdapter

class ProductListActivity : AppCompatActivity() {
    private val adapter = ProductListAdapter(context = this)

    private val binding by lazy {
        ActivityProductsListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        settingsRecyclerView()
        settingsFab()


    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instance(this)
        val daoProducts = db.productDao()
        adapter.update(daoProducts.getAll())
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