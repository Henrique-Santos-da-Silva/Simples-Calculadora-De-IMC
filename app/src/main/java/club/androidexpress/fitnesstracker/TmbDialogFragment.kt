package club.androidexpress.fitnesstracker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import club.androidexpress.fitnesstracker.sqlite.SqlHelper
import kotlinx.android.synthetic.main.tmb_dialog.view.*

class TmbDialogFragment: DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.tmb_dialog, container, false)
        val result: Double? = arguments?.getDouble("calcTmbResult")

        view.txt_tmbCalcResult.text = resources.getString(R.string.tmb_response, result)

        view.btn_tmb_dialog_confirm.setOnClickListener { dialog?.dismiss() }

        view.btn_tmb_dialog_save.setOnClickListener {
            Thread(Runnable {
                val calcId: Long = SqlHelper(context).addItem("tmb", result)

                this.activity?.runOnUiThread {
                    if (calcId > 0) {
                        Toast.makeText(context, R.string.calc_saved, Toast.LENGTH_LONG).show()
                        val intent = Intent(context, ListCalcActivity::class.java)
                        intent.putExtra("type", "tmb")
                        startActivity(intent)
                    }
                }
            }).start()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}