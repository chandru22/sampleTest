package comsample.sampleobserver.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import comsample.sampleobserver.R
import comsample.sampleobserver.managers.MyCartListManager
import comsample.sampleobserver.models.Product
import kotlinx.android.synthetic.main.row_product_list.view.*

class MyCartListAdapter(private val context: Context, private val productList: ArrayList<Product>, private val itemChangeListener: MyCartListManager.MyCartItemListener) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListAdapter.ProductViewHolder {
        return ProductListAdapter.ProductViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.row_product_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductListAdapter.ProductViewHolder, position: Int) {
        val product: Product = productList[position]

        holder.itemView.item_name.text = product.name
        holder.itemView.item_description.text = product.description
        holder.itemView.tv_count.text = product.count.toString()

        holder.itemView.btn_add.visibility = View.GONE
        holder.itemView.img_btn_increase.visibility = View.VISIBLE
        holder.itemView.img_btn_decrease.visibility = View.VISIBLE
        holder.itemView.tv_count.visibility = View.VISIBLE

        holder.itemView.img_btn_increase.setOnClickListener {
            if (holder.itemView.tv_count.text.toString().toInt() >= 0) {
                holder.itemView.tv_count.text = holder.itemView.tv_count.text.toString().toInt().plus(1).toString()
                product.count = holder.itemView.tv_count.text.toString().toInt()
                MyCartListManager.instance.changeCartList(product, MyCartListManager.CartType.UPDATE)
                itemChangeListener.onItemChanged()
            }
        }

        holder.itemView.img_btn_decrease.setOnClickListener {
            if (holder.itemView.tv_count.text.toString().toInt() > 0) {
                holder.itemView.tv_count.text = holder.itemView.tv_count.text.toString().toInt().minus(1).toString()
                product.count = holder.itemView.tv_count.text.toString().toInt()
                if (holder.itemView.tv_count.text.toString().toInt() == 0) {
                    MyCartListManager.instance.changeCartList(product, MyCartListManager.CartType.DELETE)
                    productList.remove(product)
                    notifyItemRemoved(position)
                } else {
                    MyCartListManager.instance.changeCartList(product, MyCartListManager.CartType.UPDATE)
                }
                itemChangeListener.onItemChanged()
            }
        }

    }


}