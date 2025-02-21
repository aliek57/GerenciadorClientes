package com.example.avaliacao3.classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(foreignKeys = @ForeignKey(entity = Cliente.class,
        parentColumns = "cnpj",
        childColumns = "cliente_cnpj",
        onDelete = ForeignKey.CASCADE))
public class Visita implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "cliente_cnpj")
    private String cnpj;

    private Date data;
    private int satisfacao;
    private double valorPedido;
    private String observacao;

    public Visita() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public int getSatisfacao() { return satisfacao; }
    public void setSatisfacao(int satisfacao) { this.satisfacao = satisfacao; }

    public double getValorPedido() { return valorPedido; }
    public void setValorPedido(double valorPedido) { this.valorPedido = valorPedido; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visita)) return false;
        Visita visita = (Visita) o;
        return Objects.equals(id, visita.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Visita ao cliente " + cnpj + " em " + data.toString()
                + " (Valor: R$ " + valorPedido + ")";
    }
}

