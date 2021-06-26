package org.eaa.example.apptrabalhodm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eaa.example.apptrabalhodm.model.Pergunta;

import java.sql.SQLOutput;

public class MenuInicialActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Perguntas = database.getReference("Perguntas/1");

    TextView textViewPergunta;
    RadioButton radioButtonA;
    RadioButton radioButtonB;
    RadioButton radioButtonC;
    RadioButton radioButtonD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        textViewPergunta = findViewById(R.id.textViewPergunta);
        radioButtonA = findViewById(R.id.radioButtonA);
        radioButtonB = findViewById(R.id.radioButtonB);
        radioButtonC = findViewById(R.id.radioButtonC);
        radioButtonD = findViewById(R.id.radioButtonD);


        Perguntas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Pergunta pergunta1 = snapshot.getValue(Pergunta.class);
                radioButtonA.setText(pergunta1.getAlternativaA());
                radioButtonB.setText(pergunta1.getAlternativaB());
                radioButtonC.setText(pergunta1.getAlternativaC());
                radioButtonD.setText(pergunta1.getAlternativaD());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}