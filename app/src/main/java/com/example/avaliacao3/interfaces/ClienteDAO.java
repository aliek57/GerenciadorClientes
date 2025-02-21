package com.example.avaliacao3.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.avaliacao3.classes.Cliente;

import java.util.List;

@Dao
public interface ClienteDAO {
    @Insert
    void addCliente(Cliente cliente);

    @Query("SELECT * FROM Cliente WHERE ibgeCidade = :ibgeCidade ORDER BY ultimaVisita ASC")
    LiveData<List<Cliente>> buscarClientesPorCidade(Long ibgeCidade);

    @Query("SELECT * FROM Cliente WHERE cnpj = :cnpj LIMIT 1")
    LiveData<Cliente> buscarClientePorCnpj(String cnpj);

    @Query("SELECT * FROM Cliente WHERE ibgeCidade = :ibgeCidade ORDER BY ultimaVisita ASC")
    LiveData<List<Cliente>> buscarClientesComVisitaRecente(Long ibgeCidade);
}
