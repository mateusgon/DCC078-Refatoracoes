package model;

//Ok

public class Pessoa {

    private Integer pessoaCod, restauranteCod, tipoPessoa;
    private String nome, endereco, email, telefone, senha;
    private Boolean notificado;

    public Pessoa() {
    }

    public int getPessoaCod() {
        return pessoaCod;
    }

    public Pessoa setPessoaCod(int pessoaCod) {
        this.pessoaCod = pessoaCod;
        return this;
    }

    public int getRestauranteCod() {
        return restauranteCod;
    }

    public Pessoa setRestauranteCod(int restauranteCod) {
        this.restauranteCod = restauranteCod;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Pessoa setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEndereco() {
        return endereco;
    }

    public Pessoa setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Pessoa setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public Pessoa setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public Integer getTipoPessoa() {
        return tipoPessoa;
    }

    public Pessoa setTipoPessoa(Integer tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public Pessoa setSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public Boolean getNotificado() {
        return notificado;
    }

    public Pessoa setNotificado(Boolean notificado) {
        this.notificado = notificado;
        return this;
    }
}
