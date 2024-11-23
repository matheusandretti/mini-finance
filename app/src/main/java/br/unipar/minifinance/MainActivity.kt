package br.unipar.minifinance

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unipar.minifinance.database.DatabaseHelper

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DatabaseHelper(this)

        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)

        btnEntrar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)
            finish()
        }

        btnCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)

            startActivity(intent)
            finish()
        }

    }
}