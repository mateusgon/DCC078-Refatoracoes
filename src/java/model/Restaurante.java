package model;

//Ok

import java.util.ArrayList;
import java.util.List;

public class Restaurante {

    private String nome, nomeFantasia, telefone, endereco, sigla;
    private Integer restaurantecod;
    private List<Pessoa> superUsuarios;
    private List<Pessoa> funcionarios;
    private Integer ativado;

    public Restaurante() {
        superUsuarios = new ArrayList<>();
        funcionarios = new ArrayList<>();
    }

    public Integer getRestaurantecod() {
        return restaurantecod;
    }

    public Restaurante setRestaurantecod(Integer restaurantecod) {
        this.restaurantecod = restaurantecod;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Restaurante setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public Restaurante setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public Restaurante setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public String getEndereco() {
        return endereco;
    }

    public Restaurante setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public String getSigla() {
        return sigla;
    }

    public Restaurante setSigla(String sigla) {
        this.sigla = sigla;
        return this;
    }

    public List<Pessoa> getSuperUsuarios() {
        return superUsuarios;
    }

    public Restaurante setSuperUsuarios(List<Pessoa> superUsuarios) {
        this.superUsuarios = superUsuarios;
        return this;
    }

    public List<Pessoa> getFuncionarios() {
        return funcionarios;
    }

    public Restaurante setFuncionarios(List<Pessoa> funcionarios) {
        this.funcionarios = funcionarios;
        return this;
    }

    public Integer getAtivado() {
        return ativado;
    }

    public Restaurante setAtivado(Integer ativado) {
        this.ativado = ativado;
        return this;
    }

    
    
}
