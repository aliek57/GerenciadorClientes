package com.example.avaliacao3.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.avaliacao3.classes.Cliente;
import com.example.avaliacao3.classes.Database;
import com.example.avaliacao3.interfaces.ClienteDAO;

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

