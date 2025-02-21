package com.example.avaliacao3.classes;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class Cliente implements Serializable {
    @PrimaryKey
    @NonNull
    private String cnpj;

    private String razaoSocial;
    private String nomeFantasia;
    private String contato;
    private String telefone;
    private String email;
    private Date ultimaVisita;
    private String CEP;
    private String logradouro;
    private String bairro;
    private String numero;
    private Long ibgeCidade;

    public Cliente() {}

    @NonNull
    public String getCnpj() { return cnpj; }
    public void setCnpj(@NonNull String cnpj) { this.cnpj = cnpj; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getContato() { return contato; }
    public void setContato(String contato) { this.contato = contato; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getUltimaVisita() { return ultimaVisita; }
    public void setUltimaVisita(Date ultimaVisita) { this.ultimaVisita = ultimaVisita; }

    public String getCEP() { return CEP; }
    public void setCEP(String CEP) { this.CEP = CEP; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Long getIbgeCidade() { return ibgeCidade; }
    public void setIbgeCidade(Long ibgeCidade) { this.ibgeCidade = ibgeCidade; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cnpj, cliente.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cnpj);
    }

    @Override
    public String toString() {
        return cnpj + " - " + nomeFantasia;
    }
}
