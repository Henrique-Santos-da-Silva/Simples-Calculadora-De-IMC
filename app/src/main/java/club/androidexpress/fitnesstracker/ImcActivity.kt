package club.androidexpress.fitnesstracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_imc.*

class ImcActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        btn_imc_send.setOnClickListener {
            val dialog = ImcDialogFragment()
            val bundle = Bundle()
            val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight: Int = Integer.parseInt(edit_imc_weight.text.toString())
            val height: Int = Integer.parseInt(edit_imc_height.text.toString())

            val result: Double = calculate(weight, height)
            val imcStatusResult: Int = imcResponse(result)

//            val dialog = AlertDialog.Builder(this)
//                .setTitle(getString(R.string.imc_response, result))
//                .setPositiveButton(android.R.string.ok,
//                    DialogInterface.OnClickListener { dialog, id ->
//
//                })
//                .create()
//
//            dialog.show()

            bundle.putDouble("calcResult", result)
            bundle.putInt("imcMessage", imcStatusResult)
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, dialog.tag)

            inputManager.hideSoftInputFromWindow(edit_imc_weight.windowToken, 0)
            inputManager.hideSoftInputFromWindow(edit_imc_height.windowToken, 0)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_list) {
            val intent = Intent(this, ListCalcActivity::class.java)
            intent.putExtra("type", "imc")
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @StringRes
    private fun imcResponse(imc: Double): Int {
        return when {
            imc < 15 -> R.string.imc_severely_low_weight
            imc < 16 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25 -> R.string.normal
            imc < 30 -> R.string.imc_high_weight
            imc < 35 -> R.string.imc_so_high_weight
            imc < 40 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }
    }

    private fun calculate(weight: Int, height: Int): Double {
        return weight / ((height.toDouble() / 100) * (height.toDouble() / 100))
    }


    private fun validate(): Boolean {
        return (!edit_imc_height.text.toString().startsWith("0") &&
                !edit_imc_weight.text.toString().startsWith("0") &&
                edit_imc_height.text.toString().isNotEmpty() &&
                edit_imc_weight.text.toString().isNotEmpty())
    }
}