package action;

import PadraoComposite.Bebida;
import PadraoComposite.ItemDeVenda;
import PadraoComposite.PratoDeEntrada;
import PadraoComposite.PratoPrincipal;
import PadraoComposite.Sobremesa;
import controller.Action;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Produto;
import persistence.ComboDAO;
import persistence.ProdutoDAO;

public class FazerPedidoAction implements Action {

    List<ItemDeVenda> pratosDeEntrada = new ArrayList<>();
    List<ItemDeVenda> pratosPrincipais = new ArrayList<>();
    List<ItemDeVenda> bebidas = new ArrayList<>();
    List<ItemDeVenda> sobremesas = new ArrayList<>();
    List<ItemDeVenda> combos = new ArrayList<>();
    Integer idRestaurante;
    Integer idUsuario;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        idRestaurante = Integer.parseInt(request.getParameter("id"));
        idUsuario = Integer.parseInt(request.getParameter("id2"));
        List<Produto> produtos = ProdutoDAO.getInstance().listAllFromRestaurante(idRestaurante);
        for (Iterator i = produtos.iterator(); i.hasNext();) {
            Produto produto = (Produto) i.next();
            instanciaObjeto(produto);
        }
        request.setAttribute("entradas", pratosDeEntrada);
        request.setAttribute("principais", pratosPrincipais);
        request.setAttribute("bebidas", bebidas);
        request.setAttribute("sobremesas", sobremesas);
        List<ItemDeVenda> combos = ComboDAO.getInstance().searchCombo(idRestaurante);
        for (Iterator i = combos.iterator(); i.hasNext();) {
            ItemDeVenda combo = (ItemDeVenda) i.next();
            List<Integer> idProdutos = ComboDAO.getInstance().searchComboProduto(combo.getCodigo());
            for (Integer idProduto : idProdutos) {
                Produto produto = ProdutoDAO.getInstance().listProduto(idProduto);
                instanciaCombo(produto, combo);
            }
        }
        request.setAttribute("combos", combos);
        request.setAttribute("idRest", idRestaurante);
        request.setAttribute("idUsuario", idUsuario);
        RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-cliente-fazer-pedido.jsp");
        dispatcher.forward(request, response);

    }

    public void instanciaObjeto(Produto produto) {
        switch (produto.getTipoItem()) {
            case 1:
                ItemDeVenda entrada = new PratoDeEntrada();
                entrada = entrada.setCodigo(produto.getProdutocod()).setNome(produto.getNome()).setValor(produto.getValor()).setDificuldade(produto.getDificuldade()).setRestaurantecod(idRestaurante);
                pratosDeEntrada.add(entrada);
                break;
            case 2:
                ItemDeVenda principal = new PratoPrincipal();
                principal = principal.setCodigo(produto.getProdutocod()).setNome(produto.getNome()).setValor(produto.getValor()).setDificuldade(produto.getDificuldade()).setRestaurantecod(idRestaurante);
                pratosPrincipais.add(principal);
                break;
            case 3:
                ItemDeVenda bebida = new Bebida();
                bebida = bebida.setCodigo(produto.getProdutocod()).setNome(produto.getNome()).setValor(produto.getValor()).setDificuldade(produto.getDificuldade()).setRestaurantecod(idRestaurante);
                bebidas.add(bebida);
                break;
            case 4:
                ItemDeVenda sobremesa = new Sobremesa();
                sobremesa = sobremesa.setCodigo(produto.getProdutocod()).setNome(produto.getNome()).setValor(produto.getValor()).setDificuldade(produto.getDificuldade()).setRestaurantecod(idRestaurante);
                sobremesas.add(sobremesa);
                break;
            default:
                break;
        }
    }

    public void instanciaCombo(Produto produto, ItemDeVenda combo) throws Exception {
        switch (produto.getTipoItem()) {
            case 1:
                ItemDeVenda entrada = new PratoDeEntrada();
                entrada = entrada.setCodigo(produto.getProdutocod()).setNome(produto.getNome()).setValor(produto.getValor()).setDificuldade(produto.getDificuldade()).setRestaurantecod(idRestaurante);
                combo.adicionar(entrada);
                break;
            case 2:
                ItemDeVenda principal = new PratoPrincipal();
                principal = principal.setCodigo(produto.getProdutocod()).setNome(produto.getNome()).setValor(produto.getValor()).setDificuldade(produto.getDificuldade()).setRestaurantecod(idRestaurante);
                combo.adicionar(principal);
                break;
            case 3:
                ItemDeVenda bebida = new Bebida();
                bebida = bebida.setCodigo(produto.getProdutocod()).setNome(produto.getNome()).setValor(produto.getValor()).setDificuldade(produto.getDificuldade()).setRestaurantecod(idRestaurante);
                combo.adicionar(bebida);
                break;
            case 4:
                ItemDeVenda sobremesa = new Sobremesa();
                sobremesa = sobremesa.setCodigo(produto.getProdutocod()).setNome(produto.getNome()).setValor(produto.getValor()).setDificuldade(produto.getDificuldade()).setRestaurantecod(idRestaurante);
                combo.adicionar(sobremesa);
                break;
            default:
                break;
        }
    }
}
