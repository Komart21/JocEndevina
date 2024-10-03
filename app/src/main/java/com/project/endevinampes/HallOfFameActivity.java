package com.project.endevinampes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class HallOfFameActivity extends AppCompatActivity {

    private ListView hallOfFameListView;
    private Button backButton; // Botón para regresar a MainActivity
    private ArrayAdapter<String> hallOfFameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);

        hallOfFameListView = findViewById(R.id.hallOfFameListView);
        backButton = findViewById(R.id.backButton); // Asumiendo que tienes un botón en tu layout

        // Obtener los registros de MainActivity
        hallOfFameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MainActivity.playerRecords);
        hallOfFameListView.setAdapter(hallOfFameAdapter);

        // Configurar el botón para volver
        backButton.setOnClickListener(v -> finish()); // Termina la actividad y vuelve a MainActivity
    }
}
