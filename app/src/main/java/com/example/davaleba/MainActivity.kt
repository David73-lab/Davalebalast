package com.example.davaleba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var pass : EditText
    lateinit var butt1 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email = findViewById(R.id.plainTextEmail)
        pass = findViewById(R.id.plainTextPassword)
        butt1 = findViewById(R.id.butt1)

        butt1.setOnClickListener {

            val mail = email.text.toString()
            val password = pass.text.toString()

            if (mail.isEmpty() || password.isEmpty()) {
                Toast.makeText(this,"sheavse!", Toast.LENGTH_SHORT).show()
            }
            else {

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(mail,password).addOnCompleteListener{task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this,SecondActivity::class.java))
                            finish()
                        }
                        else {
                            Toast.makeText(this,"Error!", Toast.LENGTH_SHORT).show()
                        }
                    }

            }

        }

    }
}