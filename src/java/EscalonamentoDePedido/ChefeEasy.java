package EscalonamentoDePedido;

public class ChefeEasy extends Funcionario {

    public ChefeEasy() {
    }
    
    public ChefeEasy(Integer pessoaCod, Integer restauranteCod, String nome, String endereco, String email, String telefone) {
        super(pessoaCod, restauranteCod, nome, endereco, email, telefone);
        listaPedidos.add(TipoPedidoEasy.getTipoPedidoEasy());
    }

}
