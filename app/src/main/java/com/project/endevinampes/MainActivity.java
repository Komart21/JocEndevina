package com.project.endevinampes;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText guessEditText;
    private Button guessButton;
    private Button hallOfFameButton;
    private TextView attemptsTextView;
    private TextView historyTextView;
    private ScrollView scrollView;

    private int randomNumber;
    private int attempts;
    public static final List<String> playerRecords = new ArrayList<>(); // Lista para almacenar los resultados
    private String playerName; // Variable para el nombre del jugador

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar elementos de la UI
        guessEditText = findViewById(R.id.guessEditText);
        guessButton = findViewById(R.id.guessButton);
        hallOfFameButton = findViewById(R.id.hallOfFameButton);
        attemptsTextView = findViewById(R.id.attemptsTextView);
        historyTextView = findViewById(R.id.historyTextView);
        scrollView = findViewById(R.id.scrollView);

        // Generar un número aleatorio
        generateRandomNumber();

        // Listener para el botón "Enviar"
        guessButton.setOnClickListener(v -> validateGuess());

        // Listener para detectar el "ENTER" en el teclado
        guessEditText.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                validateGuess();
                return true;
            }
            return false;
        });

        // Manejar el botón de Hall of Fame
        hallOfFameButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HallOfFameActivity.class);
            startActivity(intent);
        });
    }

    // Método para validar la entrada del usuario
    private void validateGuess() {
        String userInput = guessEditText.getText().toString();

        if (userInput.isEmpty()) {
            Toast.makeText(this, "Introduce un número!", Toast.LENGTH_SHORT).show();
            return;
        }

        int guess = Integer.parseInt(userInput);
        attempts++;

        if (guess < randomNumber) {
            Toast.makeText(this, "El número es mayor!", Toast.LENGTH_SHORT).show();
        } else if (guess > randomNumber) {
            Toast.makeText(this, "El número es menor!", Toast.LENGTH_SHORT).show();
        } else {
            showEndGameDialog();
        }

        // Actualizar el historial
        updateHistory(guess);
        guessEditText.setText(""); // Limpiar el campo de entrada
    }

    // Método para mostrar el AlertDialog cuando el usuario adivina el número
    private void showEndGameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¡Felicidades!")
                .setMessage("Has adivinado el número con " + attempts + " intentos. Introduce tu nombre para guardar el resultado.")
                .setPositiveButton("OK", (dialog, which) -> showUsernameDialog())
                .setCancelable(false)
                .show();
    }

    // Método para mostrar el diálogo para pedir el nombre del usuario
    private void showUsernameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Introduce tu nombre");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            playerName = input.getText().toString(); // Guardar el nombre del jugador
            saveGameResult(); // Guardar el resultado del juego
            dialog.dismiss();
            resetGame(); // Reiniciar el juego después de guardar el resultado
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Método para generar un nuevo número aleatorio
    private void generateRandomNumber() {
        randomNumber = new Random().nextInt(100) + 1;
        attempts = 0;
        attemptsTextView.setText("Intentos: " + attempts);
        historyTextView.setText("");
    }

    // Método para actualizar el historial de intentos
    private void updateHistory(int guess) {
        String history = historyTextView.getText().toString();
        historyTextView.setText(history + "\nIntent " + attempts + ": " + guess);
        attemptsTextView.setText("Intentos: " + attempts);

        // Desplazar el ScrollView al final
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }

    // Método para reiniciar el juego
    private void resetGame() {
        generateRandomNumber(); // Generar un nuevo número aleatorio
        attempts = 0; // Reiniciar los intentos
        guessEditText.setText(""); // Limpiar el campo de entrada
        Toast.makeText(this, "¡Nueva partida comenzada!", Toast.LENGTH_SHORT).show();
    }

    // Método para guardar los resultados de la partida
    private void saveGameResult() {
        if (playerName != null && !playerName.isEmpty()) {
            String result = playerName + ": " + attempts + " intentos";
            playerRecords.add(result); // Añadir el resultado a la lista
        }
    }
}
