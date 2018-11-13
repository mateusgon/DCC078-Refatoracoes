package PadraoStateObserverMemento;

import PadraoTemplateMethod.MensagemEnviado;
import PadraoTemplateMethod.MensagemTemplate;
import java.util.Observable;

public class PedidoEstadoEnviar extends Observable implements PedidoEstado {

    private final String nome;
    private final MensagemTemplate mensagem;

    public PedidoEstadoEnviar() {
        this.nome = "Enviado";
        this.mensagem = new MensagemEnviado();
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
        a.setEstado(new PedidoEstadoReceber());
        return true;
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
