package com.example.avaliacao3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivityVisitas extends AppCompatActivity {
    private String cnpj;
    private VisitaDAO visitaDAO;
    private ListView listaVisitas;
    private VisitaAdapter visitaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitas);

        cnpj = getIntent().getStringExtra("cliente_cnpj");

        visitaDAO = Database.getInstance(this).getVisitaDao();
        listaVisitas = findViewById(R.id.lista_visitas);

        visitaAdapter = new VisitaAdapter(this, new ArrayList<>());
        listaVisitas.setAdapter(visitaAdapter);

        Button btnAdicionarVisita = findViewById(R.id.btn_adicionar_visita);
        btnAdicionarVisita.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityVisitas.this, RegistrarVisitaActivity.class);
            intent.putExtra("cliente_cnpj", cnpj);
            startActivity(intent);
        });

        Button btnVoltar = findViewById(R.id.btn_voltar);
        btnVoltar.setOnClickListener(v -> finish());

        buscarVisitas();
    }

    private void buscarVisitas() {
        visitaDAO.buscarVisitasPorCliente(cnpj).observe(this, visitas -> {
            if (visitas.isEmpty()) {
                listaVisitas.setVisibility(View.GONE);
            } else {
                visitaAdapter.setVisitas(visitas);
                listaVisitas.setVisibility(View.VISIBLE);
            }
        });
    }

    public class VisitaAdapter extends BaseAdapter {
        private Context context;
        private List<Visita> visitas;

        public VisitaAdapter(Context context, List<Visita> visitas) {
            this.context = context;
            this.visitas = visitas;
        }

        @Override
        public int getCount() { return visitas.size(); }

        @Override
        public Object getItem(int position) { return visitas.get(position); }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_visita, parent, false);
            }

            Visita visita = visitas.get(position);

            TextView tvNomeVisita = convertView.findViewById(R.id.tv_nome_visita);
            TextView tvDataVisita = convertView.findViewById(R.id.tv_data_visita);
            TextView tvValorVisita = convertView.findViewById(R.id.tv_valor_visita);
            RatingBar ratingBarSatisfacao = convertView.findViewById(R.id.rating_satisfacao_visita);

            tvNomeVisita.setText("Visita " + visita.getId());
            String dataFormatada = formatData(visita.getData());
            tvDataVisita.setText(dataFormatada);
            tvValorVisita.setText("R$ " + visita.getValorPedido());
            ratingBarSatisfacao.setRating(visita.getSatisfacao());

            long seteDiasAtras = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000);
            long dataVisitaLong = visita.getData().getTime();

            if (dataVisitaLong >= seteDiasAtras) {
                convertView.setBackgroundColor(Color.parseColor("#D4EDDA"));
            } else {
                convertView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
            }

            return convertView;
        }

        private String formatData(Date data) {
            try {
                SimpleDateFormat desiredFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                return desiredFormat.format(data);
            } catch (Exception e) {
                e.printStackTrace();
                return "Data inválida";
            }
        }

        public void setVisitas(List<Visita> visitas) {
            this.visitas = visitas;
            notifyDataSetChanged();
        }
    }
}

