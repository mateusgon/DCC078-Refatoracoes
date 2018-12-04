package EscalonamentoDePedido;

public class ChefeHard extends Funcionario {

    public ChefeHard() {
    }

    public ChefeHard(Integer pessoaCod, Integer restauranteCod, String nome, String endereco, String email, String telefone) {
        super(pessoaCod, restauranteCod, nome, endereco, email, telefone);
        listaPedidos.add(TipoPedidoHard.getTipoPedidoHard());
        listaPedidos.add(TipoPedidoMedium.getTipoPedidoMedium());
        listaPedidos.add(TipoPedidoEasy.getTipoPedidoEasy());
    }

}
