package EscalonamentoDePedido;

public class ChefeMedium extends Funcionario {

    public ChefeMedium(Integer pessoaCod, Integer restauranteCod, String nome, String endereco, String email, String telefone) {
        super(pessoaCod, restauranteCod, nome, endereco, email, telefone);
        listaPedidos.add(TipoPedidoMedium.getTipoPedidoMedium());
    }

}
