package club.androidexpress.fitnesstracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(private val mainItens: MutableList<MainItem>, val listener: OnItemClickListener) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MainItem) {
            val txtName = itemView.findViewById<TextView>(R.id.imc_text_name)
            val imgIcon = itemView.findViewById<ImageView>(R.id.item_img_icon)
            val container: LinearLayout = itemView.findViewById(R.id.btn_imc) as LinearLayout

            container.setOnClickListener {
                listener.onClick(item.id)
            }

            txtName.setText(item.textStringId)
            imgIcon.setImageResource(item.drawableId)
            container.setBackgroundColor(item.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val mainCurrent: MainItem = mainItens[position]
        holder.bind(mainCurrent)
    }

    override fun getItemCount(): Int = mainItens.size

}