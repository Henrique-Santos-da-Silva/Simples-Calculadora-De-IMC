package club.androidexpress.fitnesstracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_imc.*
import kotlinx.android.synthetic.main.activity_tmb.*

class TmbActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tmb)

        btn_tmb_send.setOnClickListener {
            val dialog = TmbDialogFragment()
            val bundle = Bundle()
            val inputManager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight: Int = Integer.parseInt(edit_tmb_weight.text.toString())
            val height: Int = Integer.parseInt(edit_tmb_height.text.toString())
            val age: Int = Integer.parseInt(edit_tmb_age.text.toString())


            val result: Double = calculateTmb(weight, height, age)
            val tmbStatusResult: Double = tmbResponse(result)

            Log.d("Teste", "Teste: $tmbStatusResult")

            bundle.putDouble("calcTmbResult", tmbStatusResult)
            dialog.arguments = bundle
            dialog.show(supportFragmentManager, dialog.tag)

            inputManager.hideSoftInputFromWindow(edit_tmb_weight.windowToken, 0)
            inputManager.hideSoftInputFromWindow(edit_tmb_height.windowToken, 0)
            inputManager.hideSoftInputFromWindow(edit_tmb_age.windowToken, 0)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_list) {
            val intent = Intent(this, ListCalcActivity::class.java)
            intent.putExtra("type", "tmb")
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun tmbResponse (tmb: Double): Double {
        val index: Int = spinner_tmb_lifestyle.selectedItemPosition
        return when(index) {
            0 -> tmb * 1.2
            1 -> tmb * 1.375
            2 -> tmb * 1.55
            3 -> tmb * 1.725
            4 -> tmb * 1.9
            else -> 0.00
        }
    }

    private fun calculateTmb(weight: Int, height: Int, age: Int): Double {
        return 66 + (weight * 13.8) + (5 * height) - (6.8 * age)
    }


    private fun validate(): Boolean {
        return (!edit_tmb_height.text.toString().startsWith("0") &&
                !edit_tmb_weight.text.toString().startsWith("0") &&
                !edit_tmb_age.text.toString().startsWith("0") &&
                edit_tmb_height.text.toString().isNotEmpty() &&
                edit_tmb_weight.text.toString().isNotEmpty() &&
                edit_tmb_age.text.toString().isNotEmpty())
    }
}