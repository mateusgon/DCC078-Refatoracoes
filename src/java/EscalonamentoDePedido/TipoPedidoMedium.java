package EscalonamentoDePedido;

public class TipoPedidoMedium implements TipoPedido {

    private static TipoPedidoMedium tipoPedidoMedium = new TipoPedidoMedium();

    public static TipoPedidoMedium getTipoPedidoMedium() {
        return tipoPedidoMedium;
    }

    @Override
    public String getTipoPedido() {
        return "Medium";
    }
    
    
}
