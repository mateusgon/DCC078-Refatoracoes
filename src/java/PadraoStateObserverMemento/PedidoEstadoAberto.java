package PadraoStateObserverMemento;

import PadraoTemplateMethod.MensagemAberto;
import PadraoTemplateMethod.MensagemTemplate;
import java.util.Observable;

public class PedidoEstadoAberto extends Observable implements PedidoEstado {

    private final Integer codigo;
    private final String nome;
    private final MensagemTemplate mensagem;

    public PedidoEstadoAberto() {
        this.codigo = 1;
        this.nome = "Aberto";
        this.mensagem = new MensagemAberto();
    }

    public PedidoEstadoAberto(Pedido pedido) {
        this.codigo = 1;
        this.nome = "Aberto";
        this.mensagem = new MensagemAberto();
    }
    
    @Override
    public Boolean aberto(Pedido a) {
        return false;
    }

    @Override
    public Boolean preparando(Pedido a) {
        a.setEstado(new PedidoEstadoPreparar());
        return true;
    }

    @Override
    public Boolean pronto(Pedido a) {
        return false;
    }

    @Override
    public Boolean enviado(Pedido a) {
        return false;
    }

    @Override
    public Boolean recebido(Pedido a) {
        return false;
    }

    @Override
    public String getNomeEstado() {
        return this.nome;
    }

    @Override
    public MensagemTemplate getMensagem() {
        return this.mensagem;
    }

    @Override
    public Integer getCodigoEstado() {
        return this.codigo;
    }

}
