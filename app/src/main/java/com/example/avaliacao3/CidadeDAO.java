package com.example.avaliacao3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
