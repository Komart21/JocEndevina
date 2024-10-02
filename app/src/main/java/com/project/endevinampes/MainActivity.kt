package com.project.endevinampes
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var guessEditText: EditText
    private lateinit var guessButton: Button
    private lateinit var attemptsTextView: TextView
    private lateinit var historyTextView: TextView
    private lateinit var scrollView: ScrollView
    private var randomNumber: Int = 0
    private var attempts: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialitzar els elements de la UI
        guessEditText = findViewById(R.id.guessEditText)
        guessButton = findViewById(R.id.guessButton)
        attemptsTextView = findViewById(R.id.attemptsTextView)
        historyTextView = findViewById(R.id.historyTextView)
        scrollView = findViewById(R.id.scrollView)

        // Generar un número aleatori entre 1 i 100
        generateRandomNumber()

        // Listener pel botó "Enviar"
        guessButton.setOnClickListener {
            validateGuess()
        }

        // Listener per detectar l'ENTER al teclat
        guessEditText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                validateGuess()
                return@setOnKeyListener true
            }
            false
        }
    }

    // Mètode per validar l'entrada de l'usuari
    private fun validateGuess() {
        val userInput = guessEditText.text.toString()

        if (userInput.isEmpty()) {
            Toast.makeText(this, "Introdueix un número!", Toast.LENGTH_SHORT).show()
            return
        }

        val guess = userInput.toInt()
        attempts++

        when {
            guess < randomNumber -> {
                Toast.makeText(this, "El número és major!", Toast.LENGTH_SHORT).show()
            }
            guess > randomNumber -> {
                Toast.makeText(this, "El número és menor!", Toast.LENGTH_SHORT).show()
            }
            else -> {
                showEndGameDialog()
            }
        }

        // Actualitzar l'historial
        updateHistory(guess)
        guessEditText.setText("") // Esborrar l'entrada
    }

    // Mètode per mostrar l'AlertDialog quan l'usuari endevina el número
    private fun showEndGameDialog() {
        AlertDialog.Builder(this)
            .setTitle("Felicitats!")
            .setMessage("Has endevinat el número amb $attempts intents.")
            .setPositiveButton("Nova partida") { _, _ ->
                resetGame()
            }
            .setCancelable(false)
            .show()
    }

    // Mètode per generar un nou número aleatori
    private fun generateRandomNumber() {
        randomNumber = Random().nextInt(100) + 1
        attempts = 0
        attemptsTextView.text = "Intents: $attempts"
        historyTextView.text = ""
    }

    // Mètode per actualitzar l'historial d'intents
    private fun updateHistory(guess: Int) {
        val history = historyTextView.text.toString()
        historyTextView.text = "$history\nIntent $attempts: $guess"
        attemptsTextView.text = "Intents: $attempts"

        // Desplaçar el ScrollView al final
        scrollView.post { scrollView.fullScroll(View.FOCUS_DOWN) }
    }

    // Mètode per reiniciar el joc
    private fun resetGame() {
        generateRandomNumber()
    }
}
