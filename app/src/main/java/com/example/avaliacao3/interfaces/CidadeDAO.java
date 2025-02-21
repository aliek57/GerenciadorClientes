package com.example.avaliacao3.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.avaliacao3.classes.Cidade;
import com.example.avaliacao3.classes.CidadeComClientes;

import java.util.List;

@Dao
public interface CidadeDAO {
    @Insert
    void addCidade(Cidade cidade);

    @Query("SELECT * FROM Cidade order by nome")
    LiveData<List<Cidade>> buscarTodasCidades();

    @Query("SELECT * FROM Cidade WHERE ibge = :ibge LIMIT 1")
    LiveData<Cidade> buscarCidadePorIbge(Long ibge);

    @Query("SELECT * FROM Cidade WHERE ibge = :ibge")
    LiveData<CidadeComClientes> buscarCidadeComClientes(Long ibge);
}
