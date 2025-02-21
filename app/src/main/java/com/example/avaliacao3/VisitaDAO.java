package com.example.avaliacao3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VisitaDAO {
    @Insert
    void addVisita(Visita visita);

    @Query("SELECT * FROM Visita WHERE cliente_cnpj = :cnpj ORDER BY data ASC")
    LiveData<List<Visita>> buscarVisitasPorCliente(String cnpj);
}
