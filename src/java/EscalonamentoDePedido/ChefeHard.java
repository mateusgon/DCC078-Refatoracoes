package EscalonamentoDePedido;

public class ChefeHard extends Funcionario {

    public ChefeHard(Integer pessoaCod, Integer restauranteCod, String nome, String endereco, String email, String telefone) {
        super(pessoaCod, restauranteCod, nome, endereco, email, telefone);
        listaPedidos.add(TipoPedidoHard.getTipoPedidoHard());
    }

}
