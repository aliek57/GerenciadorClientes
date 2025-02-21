package com.example.avaliacao3;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ClienteViewModel extends AndroidViewModel {
    private ClienteDAO clienteDAO;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        clienteDAO = Database.getInstance(application).getClienteDao();
    }

    public void addCliente(Cliente cliente) {
        new Thread(() -> clienteDAO.addCliente(cliente)).start();
    }
}

