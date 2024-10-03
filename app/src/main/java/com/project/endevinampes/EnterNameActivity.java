package com.project.endevinampes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EnterNameActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Button saveButton;
    private int playerAttempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);

        nameEditText = findViewById(R.id.nameEditText);
        saveButton = findViewById(R.id.saveButton);

        // Recoger intent para obtener los intentos
        playerAttempts = getIntent().getIntExtra("playerAttempts", 0);

        // Configurar el botón para guardar el nombre y regresar
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = nameEditText.getText().toString().trim();
                if (!playerName.isEmpty()) {
                    PlayerRecord newRecord = new PlayerRecord(playerName, playerAttempts, 1); // Crear nuevo registro
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("newPlayerRecord", newRecord); // Pasar el registro de vuelta
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish(); // Cierra la actividad y vuelve a MainActivity
                }
            }
        });
    }
}
