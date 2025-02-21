package com.example.avaliacao3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.avaliacao3.classes.CepResponse;
import com.example.avaliacao3.classes.Cidade;
import com.example.avaliacao3.classes.Cliente;
import com.example.avaliacao3.classes.Database;
import com.example.avaliacao3.R;
import com.example.avaliacao3.classes.RetrofitInstance;
import com.example.avaliacao3.interfaces.CidadeDAO;
import com.example.avaliacao3.interfaces.OnIbgeReceivedListener;
import com.example.avaliacao3.interfaces.ViaCepService;
import com.example.avaliacao3.models.ClienteViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroClienteActivity extends AppCompatActivity {
    private Button btnBuscarCEP, btnSalvar;
    private EditText edtCnpj, edtrazaoSocial, edtNome, edtContato, edtTelefone,
            edtEmail, edtCep, edtLog, edtBairro, edtNum;
    private ClienteViewModel clienteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        btnBuscarCEP = findViewById(R.id.btn_buscar_cep);
        btnSalvar = findViewById(R.id.btn_salvar);

        edtBairro = findViewById(R.id.edt_bairro);
        edtCep = findViewById(R.id.edt_cep);
        edtLog = findViewById(R.id.edt_logradouro);
        edtNum = findViewById(R.id.edt_numero);
        edtEmail = findViewById(R.id.edt_email);
        edtTelefone = findViewById(R.id.edt_telefone);
        edtContato = findViewById(R.id.edt_contato);
        edtNome = findViewById(R.id.edt_nome_fantasia);
        edtrazaoSocial = findViewById(R.id.edt_razao_social);
        edtCnpj = findViewById(R.id.edt_cnpj);

        clienteViewModel = new ViewModelProvider(this).get(ClienteViewModel.class);

        btnBuscarCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cep = edtCep.getText().toString();
                if (cep.isEmpty()) {
                    Toast.makeText(CadastroClienteActivity.this, "CEP inválido", Toast.LENGTH_SHORT).show();
                    return;
                }
                buscarCep(cep);
            }
        });

        btnSalvar.setOnClickListener(view -> {
            String cnpj = edtCnpj.getText().toString().trim();
            String razaoSocial = edtrazaoSocial.getText().toString().trim();
            String nomeFantasia = edtNome.getText().toString().trim();
            String contato = edtContato.getText().toString().trim();
            String telefone = edtTelefone.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String cep = edtCep.getText().toString().trim();
            String logradouro = edtLog.getText().toString().trim();
            String bairro = edtBairro.getText().toString().trim();
            String numero = edtNum.getText().toString().trim();

            verificarCidade(cep, ibgeCidade -> {
                Cliente cliente = new Cliente();
                cliente.setCnpj(cnpj);
                cliente.setRazaoSocial(razaoSocial);
                cliente.setNomeFantasia(nomeFantasia);
                cliente.setContato(contato);
                cliente.setTelefone(telefone);
                cliente.setEmail(email);
                cliente.setCEP(cep);
                cliente.setLogradouro(logradouro);
                cliente.setBairro(bairro);
                cliente.setNumero(numero);
                cliente.setIbgeCidade(ibgeCidade);

                clienteViewModel.addCliente(cliente);
                Toast.makeText(this, "Cliente salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();

                Intent intent = new Intent(CadastroClienteActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        });
    }

    private void verificarCidade(String cep, OnIbgeReceivedListener listener) {
        ViaCepService service = RetrofitInstance.getRetrofitInstance().create(ViaCepService.class);
        Call<CepResponse> call = service.getEnderecoPorCep(cep);

        call.enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(Call<CepResponse> call, Response<CepResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Long ibgeCidade = Long.parseLong(response.body().getIbge());
                        Database db = Database.getInstance(CadastroClienteActivity.this);
                        CidadeDAO cidadeDAO = db.getCidadeDao();

                        cidadeDAO.buscarCidadePorIbge(ibgeCidade).observe(CadastroClienteActivity.this, cidade -> {
                            if (cidade == null) {
                                final Cidade c = new Cidade();
                                c.setIbge(ibgeCidade);
                                c.setNome(response.body().getLocalidade());
                                c.setDdd(Integer.parseInt(response.body().getDdd()));
                                new Thread() {
                                    public void run() {
                                        Looper.prepare();
                                        cidadeDAO.addCidade(c);
                                        Looper.loop();
                                    }
                                }.start();
                                cidade = c;
                            }

                            Long finalIbge = cidade.getIbge();
                            listener.onIbgeReceived(finalIbge);

                        });
                    } catch (NumberFormatException e) {
                        runOnUiThread(() -> Toast.makeText(CadastroClienteActivity.this, "IBGE inválido!", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(CadastroClienteActivity.this, "Erro ao buscar IBGE!", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onFailure(Call<CepResponse> call, Throwable t) {
                runOnUiThread(() -> Toast.makeText(CadastroClienteActivity.this, "Falha ao conectar à API!", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void buscarCep(String cep) {
        RetrofitInstance.getRetrofitInstance()
                .create(ViaCepService.class)
                .getEnderecoPorCep(cep)
                .enqueue(new Callback<CepResponse>() {
                    @Override
                    public void onResponse(Call<CepResponse> call, Response<CepResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            CepResponse endereco = response.body();
                            edtLog.setText(endereco.getLogradouro());
                            edtBairro.setText(endereco.getBairro());
                            edtNum.requestFocus();
                            Toast.makeText(CadastroClienteActivity.this, "CEP encontrado!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CadastroClienteActivity.this, "CEP não encontrado!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CepResponse> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(CadastroClienteActivity.this, "Erro ao buscar o CEP: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}