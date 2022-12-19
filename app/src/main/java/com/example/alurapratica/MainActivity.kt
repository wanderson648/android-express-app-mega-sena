package com.example.alurapratica

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewAndListener()
    }





    private fun setupViewAndListener() {
        val editText = findViewById<TextView>(R.id.txt_number)
        val textResult = findViewById<TextView>(R.id.txt_generate_result)
        val btnGenerate = findViewById<TextView>(R.id.btn_generate)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", "Nehuma registro salvo")

        result?.let {
            textResult.text = "Ultima aposta: $result"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerate(text, textResult)
        }
    }

    private fun numberGenerate(text: String, textView: TextView) {
        if (fieldIsEmpty(text)) return

        val qtd = text.toInt()
        if (fieldNumberIsRange(qtd)) return

        val numbers = mutableSetOf<Int>()
        val random = java.util.Random()

        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (numbers.size == qtd)
                break
        }
        textView.text = numbers.joinToString(" - ")

        prefs.edit().apply {
            putString("result", textView.text.toString())
            apply()
        }
    }

    private fun fieldNumberIsRange(qtd: Int): Boolean = if (qtd !in 6..15) {
        Toast.makeText(
            this,
            "Write a input number",
            Toast.LENGTH_LONG
        ).show()
        true
        } else false

    private fun fieldIsEmpty(text: String): Boolean = if (text.isEmpty()) {
        Toast.makeText(
            this,
            "Write a input number",
            Toast.LENGTH_LONG
        ).show()
        true
    } else false





}


