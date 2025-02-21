package com.example.avaliacao3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.avaliacao3.R;
import com.example.avaliacao3.classes.Visita;
import com.example.avaliacao3.models.VisitaViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RegistrarVisitaActivity extends AppCompatActivity {
    private Button btnSalvarVisita;
    private EditText edtData, edtValor, edtObs;
    private RatingBar ratSatisfacao;
    private VisitaViewModel visitaViewModel;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_visita);

        btnSalvarVisita = findViewById(R.id.btn_salvarVisita);

        edtData = findViewById(R.id.edt_data);
        edtValor = findViewById(R.id.edt_valor);
        edtObs = findViewById(R.id.edt_observacao);
        ratSatisfacao = findViewById(R.id.rating_satisfacao);

        visitaViewModel = new ViewModelProvider(this).get(VisitaViewModel.class);

        String clienteCnpj = getIntent().getStringExtra("cliente_cnpj");

        btnSalvarVisita.setOnClickListener(view -> {
            String dataStr = edtData.getText().toString().trim();
            String valorStr = edtValor.getText().toString().trim();
            String observacao = edtObs.getText().toString().trim();
            float satisfacaoF = ratSatisfacao.getRating();

            if (dataStr.isEmpty() || valorStr.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                Date data = dateFormat.parse(dataStr);
                if (data == null) throw new ParseException("Data inválida", 0);

                double valor = Double.parseDouble(valorStr);
                int satisfacao = Math.round(satisfacaoF);

                Visita visita = new Visita();
                visita.setData(data);
                visita.setValorPedido(valor);
                visita.setObservacao(observacao);
                visita.setSatisfacao(satisfacao);
                visita.setCnpj(clienteCnpj);

                new Thread(() -> {
                    visitaViewModel.addVisita(visita);

                    runOnUiThread(() -> {
                        Toast.makeText(this, "Visita registrada com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrarVisitaActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    });
                }).start();
            } catch (ParseException e) {
                Toast.makeText(this, "Formato de data inválido! Use dd/MM/yyyy", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valor inválido!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
