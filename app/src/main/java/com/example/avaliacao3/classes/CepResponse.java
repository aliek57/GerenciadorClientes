package com.example.avaliacao3.classes;

public class CepResponse {
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String cep;
    private String ibge;
    private String ddd;

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getLocalidade() { return localidade; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getIbge() { return ibge; }
    public void setIbge(String ibge) { this.ibge = ibge; }

    public String getDdd() { return ddd; }
    public void setDdd(String ddd) { this.ddd = ddd; }
}
