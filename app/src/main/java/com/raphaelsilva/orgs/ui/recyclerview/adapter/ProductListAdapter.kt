package com.raphaelsilva.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raphaelsilva.orgs.databinding.ProductItemBinding
import com.raphaelsilva.orgs.extensions.convertToBRLCurrency
import com.raphaelsilva.orgs.extensions.loadImage
import com.raphaelsilva.orgs.model.Product


class ProductListAdapter(
    private val context: Context,
    products: List<Product> = emptyList(),
    var itemClick: (product: Product) -> Unit = {}
) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private val products = products.toMutableList()

    inner class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var product: Product

        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    itemClick(product)
                }
            }
        }

        fun bind(product: Product) {
            this.product = product
            val title = binding.title
            title.text = product.title
            val description = binding.description
            description.text = product.description
            val price = binding.price
            price.text = product.price.convertToBRLCurrency()
            val imageCover = binding.productImage
            val visible = if (product.image != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
            imageCover.visibility = visible
            imageCover.loadImage(product.image)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = ProductItemBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    fun update(productsList: List<Product>) {
        products.clear()
        products.addAll(productsList)
        notifyDataSetChanged()
    }
}
