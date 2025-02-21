package com.example.avaliacao3.classes;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CidadeComClientes {
    @Embedded
    public Cidade cidade;
    @Relation(parentColumn = "ibge", entityColumn = "ibgeCidade")
    public List<Cliente> clientes;
}
