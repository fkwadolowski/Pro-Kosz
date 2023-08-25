package com.example.first_mgr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.first_mgr.databinding.ActivityMainBinding
import com.example.first_mgr.databinding.FirstPageBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindig: FirstPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = FirstPageBinding.inflate(layoutInflater)
        setContentView(bindig.root)
//odpalanie UI
        initUI()
    }

    private fun initUI() {
        bindig.fpBack.setOnClickListener() {

        }
        bindig.buttonSignin.setOnClickListener() {
            if (bindig.etLogin.getText().toString().equals("admin") &&
                bindig.etPassword.getText().toString().equals("admin")
            ) {
                Toast.makeText(
                    getApplicationContext(),
                    "Redirecting...", Toast.LENGTH_SHORT
                ).show();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT)
                    .show();
            }
            bindig.tvForgot.setOnClickListener() {

            }
            bindig.tvSignUp.setOnClickListener() {

            }
        }
    }
}