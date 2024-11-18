package br.unipar.minifinance

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WalletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_wallet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val icoHome = findViewById<Button>(R.id.IcoHOME)
        val icoMov2 = findViewById<Button>(R.id.icoMov2)
        val icoMovimento = findViewById<Button>(R.id.IcoMOVIMENTO)

        icoHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)

            startActivity(intent)
            finish()
        }

        icoMovimento.setOnClickListener {
            movimento()
        }

        icoMov2.setOnClickListener {
            movimento()
        }

    }
    fun movimento() {
        val intent = Intent(this, MovementActivity::class.java)

        startActivity(intent)
        finish()
    }

}