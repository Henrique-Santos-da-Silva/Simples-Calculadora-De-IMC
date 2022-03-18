package club.androidexpress.fitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import club.androidexpress.fitnesstracker.sqlite.Register
import club.androidexpress.fitnesstracker.sqlite.SqlHelper
import kotlinx.android.synthetic.main.activity_list_calc.*

class ListCalcActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        val extras: Bundle? = intent.extras
        if (extras != null) {
            val type: String? = extras.getString("type")
            val registers: MutableList<Register> = SqlHelper(this).getRegisterBy(type)
            val imcAdapter = ResultCalcHistoricAdapter(registers)
            recycle_view_list.adapter = imcAdapter
            recycle_view_list.layoutManager = LinearLayoutManager(this)

        }
    }
}