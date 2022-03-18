package club.androidexpress.fitnesstracker

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import club.androidexpress.fitnesstracker.sqlite.Register
import club.androidexpress.fitnesstracker.sqlite.SqlHelper
import kotlinx.android.synthetic.main.data_saved_item.view.*
import kotlinx.android.synthetic.main.delete_dialog.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ResultCalcHistoricAdapter(private val registers: MutableList<Register>) : RecyclerView.Adapter<ResultCalcHistoricAdapter.ImcHistoricViewHolder>(), OnAdapterItemClickListener {
    inner class ImcHistoricViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind(register: Register, onAdapterItemClickListener: OnAdapterItemClickListener) {
            var formatted: String = ""
            val resultText = itemView.findViewById<TextView>(R.id.list_txt_result)
            val dateText = itemView.findViewById<TextView>(R.id.list_txt_datetime)

            try {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("pt", "BR"))
                val dateSaved: Date = sdf.parse(register.createdDate)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR"))
                formatted = dateFormat.format(dateSaved)
            } catch (e: ParseException) {

            }

            // (itemView as TextView).text = itemView.context.getString(R.string.list_response, register.response, formatted)
            resultText.text = itemView.context.getString(R.string.result_list, register.response)
            dateText.text = itemView.context.getString(R.string.result_datetime_list, formatted)

            itemView.setOnLongClickListener {
                onAdapterItemClickListener.onLongClick(adapterPosition, register.type, register.id, itemView.context)
                return@setOnLongClickListener false
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImcHistoricViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_saved_item, parent, false)
        return ImcHistoricViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImcHistoricViewHolder, position: Int) {
        val register = registers[position]
        holder.bind(register, this)
    }

    override fun getItemCount(): Int = registers.size


    override fun onLongClick(position: Int, type: String, id: Int, context: Context) {
        val deleteDialog = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null, false)
        val alertDialog = AlertDialog.Builder(context).create()
        deleteDialog.btn_dialog_cancel.setOnClickListener {
            alertDialog.dismiss()
        }

        deleteDialog.btn_dialog_delete_confirm.setOnClickListener {
            val calcId: Int = SqlHelper(context).removeItem(type, id)

            if (calcId > 0) {
                Toast.makeText(context, R.string.calc_removed, Toast.LENGTH_LONG).show()
                registers.removeAt(position)
                notifyDataSetChanged()

                alertDialog.dismiss()
            }
        }

        alertDialog.setView(deleteDialog)
        alertDialog.show()

    }

}