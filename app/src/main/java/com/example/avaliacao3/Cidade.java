package com.example.avaliacao3;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Cidade implements Serializable {
    @PrimaryKey
    private Long ibge;
    private String nome;
    private int ddd;

    public Cidade() {}

    public Long getIbge() { return ibge; }
    public void setIbge(Long ibge) { this.ibge = ibge; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getDdd() { return ddd; }
    public void setDdd(int ddd) { this.ddd = ddd; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cidade)) return false;
        Cidade cidade = (Cidade) o;
        return Objects.equals(ibge, cidade.ibge);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ibge);
    }

    @Override
    public String toString() { return nome; }
}
