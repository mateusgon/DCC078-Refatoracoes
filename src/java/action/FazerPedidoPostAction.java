package action;

import PadraoComposite.Combo;
import PadraoComposite.ItemDeVenda;
import PadraoComposite.ItemDeVendaFactory;
import PadraoStateObserverMemento.Cliente;
import PadraoStateObserverMemento.Pedido;
import PadraoStateObserverMemento.PedidoEstadoAberto;
import PadraoStrategy.MetodoPagamento;
import PadraoStrategy.MetodoPagamentoCartaoCredito;
import PadraoStrategy.MetodoPagamentoCartaoDebito;
import PadraoStrategy.MetodoPagamentoDinheiro;
import controller.Action;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pessoa;
import model.Produto;
import persistence.ComboDAO;
import persistence.PedidoDAO;
import persistence.PessoaDAO;
import persistence.ProdutoDAO;

public class FazerPedidoPostAction implements Action {

    Integer pagamento;
    Integer idRestaurante;
    Integer idUsr;
    Pedido pedido;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        pedido = new Pedido();

        pagamento = Integer.parseInt(request.getParameter("pagamento"));
        idUsr = Integer.parseInt(request.getParameter("idUsr"));
        idRestaurante = Integer.parseInt(request.getParameter("idRest"));

        pedido = pedido.setIdCliente(idUsr).setIdRestaurante(idRestaurante).setNumeroPedido(PedidoDAO.getInstance().savePedido(pedido)).setEstado(new PedidoEstadoAberto(pedido));;
        pedido.saveToMemento();

        String[] posicoes = request.getParameterValues("entrada");
        String[] posicoes2 = request.getParameterValues("principal");
        String[] posicoes3 = request.getParameterValues("bebida");
        String[] posicoes4 = request.getParameterValues("sobremesa");
        String[] posicoes5 = request.getParameterValues("combos");

        cadastrarItensDeVendaNoPedido(posicoes);
        cadastrarItensDeVendaNoPedido(posicoes2);
        cadastrarItensDeVendaNoPedido(posicoes3);
        cadastrarItensDeVendaNoPedido(posicoes4);

        PedidoDAO.getInstance().savePedidoProduto(pedido.getItens(), pedido.getNumeroPedido());

        cadastrarItensDeVendaNoPedido(posicoes5);
        
        Pessoa pessoa = PessoaDAO.getInstance().buscaUsuario(idUsr);
        Cliente cliente = new Cliente(pessoa.getPessoaCod(), pessoa.getTipoPessoa(), pessoa.getNome(), pessoa.getEndereco(), pessoa.getEmail(), null, pessoa.getTelefone(), pedido);
        cliente.notificarAbertura();

        setDificuldade();
        calculaValor();
        PedidoDAO.getInstance().updatePedido(pedido);

        request.setAttribute("idRest", idRestaurante);
        request.setAttribute("nomeUsuario", cliente.getNome());
        request.setAttribute("valor", pedido.getValor());
        request.setAttribute("idPedido", pedido.getNumeroPedido());
        request.setAttribute("idUsr", pedido.getIdCliente());
        RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-cliente-confirmar-pedido.jsp");
        dispatcher.forward(request, response);
    }

    private void cadastrarItensDeVendaNoPedido(String[] posicoes) throws SQLException, ClassNotFoundException {
        if (posicoes != null && posicoes.length > 0) {
            Integer[] requisicao = new Integer[posicoes.length];
            for (int i = 0; i < posicoes.length; i = i + 2) {
                requisicao[i] = Integer.parseInt(posicoes[i]);
                requisicao[i + 1] = Integer.parseInt(posicoes[i + 1]);
                if (requisicao[i + 1] > 0) {
                    instanciaItemDeVenda(ProdutoDAO.getInstance().listProduto(requisicao[i]), requisicao[i + 1]);
                }
            }
        }
    }

    private void cadastrarCombosDeVendaNoPedido(String[] posicoes) throws SQLException, ClassNotFoundException, Exception {
        if (posicoes != null && posicoes.length > 0) {
            Integer[] requisicao5 = new Integer[posicoes.length];
            for (int i = 0; i < posicoes.length; i = i + 2) {
                requisicao5[i] = Integer.parseInt(posicoes[i]);
                requisicao5[i + 1] = Integer.parseInt(posicoes[i + 1]);
                if (requisicao5[i + 1] > 0) {
                    ItemDeVenda combo = new Combo();
                    ComboDAO.getInstance().searchComboEspecifico(requisicao5[i], combo);
                    List<Integer> idProdutos = ComboDAO.getInstance().searchComboProduto(requisicao5[i]);
                    combo.setQuantidade(requisicao5[i + 1]);
                    for (Integer idProduto : idProdutos) {
                        Produto produto = ProdutoDAO.getInstance().listProduto(idProduto);
                        produto.setRestaurantecod(idRestaurante);
                        ItemDeVenda itemDeVenda = ItemDeVendaFactory.instanciarItemDeVenda(produto);
                        itemDeVenda.setQuantidade(requisicao5[i + 1]);
                        combo.adicionar(itemDeVenda);
                    }
                    PedidoDAO.getInstance().saveComboProduto(combo, pedido.getNumeroPedido());
                    pedido.getItens().add(combo);
                }
            }
        }
    }

    private void instanciaItemDeVenda(Produto produto, Integer quantidade) {
        ItemDeVenda itemDeVenda = ItemDeVendaFactory.instanciarItemDeVenda(produto);
        itemDeVenda.setQuantidade(quantidade);
        pedido.getItens().add(itemDeVenda);
    }

    public void setDificuldade() {
        List<ItemDeVenda> itens = pedido.getItens();
        for (ItemDeVenda iten : itens) {
            if (iten.getDificuldade() == 3) {
                pedido.setDificuldade(3);
                break;
            }
            pedido.setDificuldade(iten.getDificuldade());
        }
    }

    public void calculaValor() {
        MetodoPagamento metodo;
        List<ItemDeVenda> itens = pedido.getItens();
        Double valor = 0.0;
        for (ItemDeVenda iten : itens) {
            valor = valor + iten.getValor() * iten.getQuantidade();
        }
        switch (pagamento) {
            case 1: {
                metodo = new MetodoPagamentoCartaoCredito();
                pedido.setValor(metodo.obterValor(valor));
                break;
            }
            case 2: {
                metodo = new MetodoPagamentoCartaoDebito();
                pedido.setValor(metodo.obterValor(valor));
                break;
            }
            case 3: {
                metodo = new MetodoPagamentoDinheiro();
                pedido.setValor(metodo.obterValor(valor));
                break;
            }
        }
    }

}
