package com.raphaelsilva.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.raphaelsilva.orgs.R
import com.raphaelsilva.orgs.database.AppDatabase
import com.raphaelsilva.orgs.databinding.ActivityProductsListBinding
import com.raphaelsilva.orgs.exeptions.CoroutineException
import com.raphaelsilva.orgs.ui.recyclerview.adapter.ProductListAdapter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class ProductListActivity : UserBaseActivity() {
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
        lifecycleScope.launch {
            launch {
                user.filterNotNull().collect {user ->
                    updateAdapter(user.id)
                }
            }
        }
        settingsRecyclerView()
        settingsFab()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.product_list_logout_icon -> {
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateAdapter(userId: String) {
        lifecycleScope.launch(CoroutineException.handler(this)) {
            daoProducts.getAllByUserId(userId).collect { product ->
                adapter.update(product)
            }
        }
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
}