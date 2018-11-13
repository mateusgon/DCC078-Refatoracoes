package PadraoStateObserverMemento;

import PadraoTemplateMethod.MensagemAberto;
import PadraoTemplateMethod.MensagemEnviado;
import PadraoTemplateMethod.MensagemPreparado;
import PadraoTemplateMethod.MensagemPronto;
import PadraoTemplateMethod.MensagemRecebido;
import PadraoTemplateMethod.MensagemTemplate;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Mensagem;
import persistence.MensagemDAO;

public class Cliente implements Observer {

    private Integer pessoaCod, tipoPessoa;
    private String nome, endereco, email, senha, telefone;
    private Observable pedido;

    public Cliente(Integer pessoaCod, Integer tipoPessoa, String nome, String endereco, String email, String senha, String telefone, Observable pedido) {
        this.pessoaCod = pessoaCod;
        this.tipoPessoa = tipoPessoa;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.pedido = pedido;
        pedido.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        Pedido p = (Pedido) getPedido();
        Mensagem mensagemEnviada = new Mensagem();
        mensagemEnviada.setMensagem(p.getEstado().getMensagem().getEstadoPedido(p));
        mensagemEnviada.setIdReceptor(pessoaCod);
        try {
            MensagemDAO.getInstance().saveMensagem(mensagemEnviada);
            System.out.println("Mensagem enviada com sucesso");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
        }
    }

    public Observable getPedido() {
        return this.pedido;
    }

    public Integer getPessoaCod() {
        return pessoaCod;
    }

    public Cliente setPessoaCod(Integer pessoaCod) {
        this.pessoaCod = pessoaCod;
        return this;
    }

    public Integer getTipoPessoa() {
        return tipoPessoa;
    }

    public Cliente setTipoPessoa(Integer tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Cliente setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEndereco() {
        return endereco;
    }

    public Cliente setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Cliente setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public Cliente setSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public Cliente setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public Cliente setPedido(Observable pedido) {
        this.pedido = pedido;
        return this;
    }

    public void notificarAbertura() throws ClassNotFoundException, SQLException {
        Pedido pedido2 = (Pedido) pedido;
        MensagemTemplate mensagem = new MensagemAberto();
        Mensagem mensagemEnviada = new Mensagem();
        mensagemEnviada.setMensagem(mensagem.getEstadoPedido(pedido2));
        mensagemEnviada.setIdReceptor(pessoaCod);
        MensagemDAO.getInstance().saveMensagem(mensagemEnviada);
    }

}
