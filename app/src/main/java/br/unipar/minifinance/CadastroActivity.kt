package br.unipar.minifinance

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unipar.minifinance.database.DatabaseHelper

class CadastroActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DatabaseHelper(this)

        val edtUser = findViewById<EditText>(R.id.edtUsuario)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtSenha = findViewById<EditText>(R.id.edtSenha)
        val edtSenhaConfirm = findViewById<EditText>(R.id.edtSenhaConfirm)

        val btnCadastrar = findViewById<Button>(R.id.btnCad2)
        val btnVoltar = findViewById<Button>(R.id.btnVoltar)



        btnCadastrar.setOnClickListener {
            val username = edtUser.text.toString()
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()
            val senhaConfirma = edtSenhaConfirm.text.toString()

            if (username.isEmpty() || email.isEmpty() || senha.isEmpty() || senhaConfirma.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "E-mail inválido!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha != senhaConfirma) {
                Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = dbHelper.addUser(username, email, senha)

            if (!success) {
                Toast.makeText(this, "Erro ao registrar usuário.", Toast.LENGTH_SHORT).show()
                Log.e("CadastroActivity", "Falha ao registrar o usuário.")
                return@setOnClickListener
            }

            Toast.makeText(this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show()
            Log.d("CadastroActivity", "Usuário cadastrado: $username ($email)")

            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 1000)
        }

        btnVoltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()
        }

    }
}