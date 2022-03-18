package club.androidexpress.fitnesstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        btn_imc.setOnClickListener {
//            val intent: Intent = Intent(this, ImcActivity::class.java)
//            startActivity(intent)
//        }

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(MainItem(1, R.drawable.ic_baseline_wb_sunny_24, R.string.label_imc, Color.GREEN))
        mainItems.add(MainItem(2, R.drawable.ic_baseline_tag_faces_24, R.string.tmb, Color.YELLOW))


        val mainAdapter = MainAdapter(mainItems, object: OnItemClickListener {
            override fun onClick(id: Int) {
               when(id) {
                   1 -> startActivity(Intent(this@MainActivity, ImcActivity::class.java))
                   2 -> startActivity(Intent(this@MainActivity, TmbActivity::class.java))
                   else -> Log.e("TESTE ERROR", "Id da Activity não encontrado")
               }
            }
        })

        rv_main.adapter = mainAdapter
        rv_main.layoutManager = GridLayoutManager(this, 2)
    }
}

