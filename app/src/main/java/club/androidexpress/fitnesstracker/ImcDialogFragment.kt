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
import kotlinx.android.synthetic.main.imc_dialog.view.*

class ImcDialogFragment: DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.imc_dialog, container, false)
        val result: Double? = arguments?.getDouble("calcResult")
        val message: Int? = arguments?.getInt("imcMessage")


        view.txt_imcCalcResult.text = resources.getString(R.string.imc_response, result)
        view.txt_imcMessage.text = resources.getString(message!!)

        view.btn_dialog_confirm.setOnClickListener {
            dialog?.dismiss()
        }

        view.btn_dialog_save.setOnClickListener {
            Thread(Runnable {
                val calcId = SqlHelper(context).addItem("imc", result)

                this.activity?.runOnUiThread {
                    if (calcId > 0) {
                        Toast.makeText(context, R.string.calc_saved, Toast.LENGTH_LONG).show()
                        val intent = Intent(context, ListCalcActivity::class.java)
                        intent.putExtra("type", "imc")
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