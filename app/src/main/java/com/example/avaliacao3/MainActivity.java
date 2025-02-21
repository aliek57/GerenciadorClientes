package com.example.avaliacao3;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Database db;
    private CidadeDAO cidadeDAO;
    private ClienteDAO clienteDAO;

    private ListView listaClientes;
    private Spinner spCidades;
    private ClienteAdapter clienteAdapter;

    private List<Cidade> cidades = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        spCidades = findViewById(R.id.sp_cidades);
        listaClientes = findViewById(R.id.lista_clientes);

        Button btnAddCliente = findViewById(R.id.btn_add_cliente);
        btnAddCliente.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastroClienteActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = Database.getInstance(this);
        cidadeDAO = db.getCidadeDao();
        clienteDAO = db.getClienteDao();

        preencherSpinnerCidades();

        spCidades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cidade cidadeSelecionada = cidades.get(position);
                Long ibgeCidade = cidadeSelecionada.getIbge();
                buscarClientesPorCidade(ibgeCidade);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        clienteAdapter = new ClienteAdapter(this, new ArrayList<>());
        listaClientes.setAdapter(clienteAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void preencherSpinnerCidades() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                cidadeDAO.buscarTodasCidades().observe(MainActivity.this, new Observer<List<Cidade>>() {
                    @Override
                    public void onChanged(List<Cidade> listaCidades) {
                        if (listaCidades != null && !listaCidades.isEmpty()) {
                            cidades = listaCidades;

                            List<String> nomesCidades = new ArrayList<>();
                            for (Cidade cidade : cidades) {
                                nomesCidades.add(cidade.getNome());
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, nomesCidades);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spCidades.setAdapter(adapter);
                        }
                    }
                });
            }
        });
    }

    private void buscarClientesPorCidade(Long ibgeCidade) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                clienteDAO.buscarClientesPorCidade(ibgeCidade).observe(MainActivity.this, new Observer<List<Cliente>>() {
                    @Override
                    public void onChanged(List<Cliente> clientes) {
                        clienteAdapter.setClientes(clientes);
                        clienteAdapter = new ClienteAdapter(MainActivity.this, clientes);
                        listaClientes.setAdapter(clienteAdapter);
                    }
                });
            }
        });
    }

    public class ClienteAdapter extends ArrayAdapter<Cliente> {
        public ClienteAdapter(MainActivity context, List<Cliente> clientes) {
            super(context, 0, clientes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_cliente, parent, false);
            }

            Cliente cliente = getItem(position);
            TextView nomeCliente = convertView.findViewById(R.id.tv_clienteNome);
            Button btnLigar = convertView.findViewById(R.id.btn_ligar);

            nomeCliente.setText(cliente.getNomeFantasia());
            btnLigar.setOnClickListener(v -> ligarParaCliente(cliente.getTelefone()));

            nomeCliente.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ActivityVisitas.class);
                intent.putExtra("cliente_cnpj", cliente.getCnpj());
                startActivity(intent);
            });

            return convertView;
        }

        public void setClientes(List<Cliente> clientes) {
            clear();
            addAll(clientes);
            notifyDataSetChanged();
        }
    }

    private void ligarParaCliente(String telefone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + telefone));
        startActivity(intent);
    }
}