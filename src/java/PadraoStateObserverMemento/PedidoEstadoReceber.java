package PadraoStateObserverMemento;

import PadraoTemplateMethod.MensagemRecebido;
import PadraoTemplateMethod.MensagemTemplate;
import java.util.Observable;

public class PedidoEstadoReceber extends Observable implements PedidoEstado {

    private final String nome;
    private final MensagemTemplate mensagem;
    
    public PedidoEstadoReceber() {
        this.nome = "Recebido";
        this.mensagem = new MensagemRecebido();
    }

    @Override
    public Boolean aberto(Pedido a) {
        return false;
    }

    @Override
    public Boolean preparando(Pedido a) {
        return false;
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
        return nome;
    }

    @Override
    public MensagemTemplate getMensagem() {
        return this.mensagem;
    }

}
