package action;

import PadraoComposite.ItemDeVenda;
import PadraoComposite.ItemDeVendaFactory;
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

public class VerCardapioAction implements Action {

    List<ItemDeVenda> pratosDeEntrada = new ArrayList<>();
    List<ItemDeVenda> pratosPrincipais = new ArrayList<>();
    List<ItemDeVenda> bebidas = new ArrayList<>();
    List<ItemDeVenda> sobremesas = new ArrayList<>();
    List<ItemDeVenda> combos = new ArrayList<>();
    Integer idRestaurante;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        idRestaurante = Integer.parseInt(request.getParameter("id"));
        List<Produto> produtos = ProdutoDAO.getInstance().listAllFromRestaurante(idRestaurante);
        for (Iterator i = produtos.iterator(); i.hasNext();) {
            Produto produto = (Produto) i.next();
            preencherListaTipoDoItemDeVenda(produto);
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
                produto.setQuantidade(ComboDAO.getInstance().searchProdutoComboQuantidade(combo.getCodigo(), produto.getProdutocod()));
                produto.setRestaurantecod(idRestaurante);
                ItemDeVenda itemDeVenda = ItemDeVendaFactory.instanciarItemDeVenda(produto);
                combo.adicionar(itemDeVenda);
            }
        }
        request.setAttribute("combos", combos);
        request.setAttribute("idRest", idRestaurante);
        RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-cliente-listar-combos.jsp");
        dispatcher.forward(request, response);

    }

    public void preencherListaTipoDoItemDeVenda(Produto produto) {
        ItemDeVenda itemDeVenda = ItemDeVendaFactory.instanciarItemDeVenda(produto);
        if (produto.getTipoItem().equals(1))
        {
            pratosDeEntrada.add(itemDeVenda);
        }
        else if (produto.getTipoItem().equals(2))
        {
            pratosPrincipais.add(itemDeVenda);
        }
        else if (produto.getTipoItem().equals(3))
        {
            bebidas.add(itemDeVenda);
        }
        else
        {
            sobremesas.add(itemDeVenda);
        }
    }
}
