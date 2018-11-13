package PadraoStateObserverMemento;

import PadraoTemplateMethod.MensagemPronto;
import PadraoTemplateMethod.MensagemTemplate;
import java.util.Observable;

public class PedidoEstadoPronto extends Observable implements PedidoEstado {

    private final String nome;
    private final MensagemTemplate mensagem;

    public PedidoEstadoPronto() {
        this.nome = "Pronto";
        this.mensagem = new MensagemPronto();
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
        a.setEstado(new PedidoEstadoEnviar());
        return true;
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
