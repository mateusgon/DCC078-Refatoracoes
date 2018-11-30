package action;

import PadraoComposite.Bebida;
import PadraoComposite.Combo;
import PadraoComposite.ItemDeVenda;
import PadraoComposite.ItemDeVendaFactory;
import PadraoComposite.PratoDeEntrada;
import PadraoComposite.PratoPrincipal;
import PadraoComposite.Sobremesa;
import controller.Action;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Produto;
import persistence.ComboDAO;
import persistence.ProdutoDAO;

public class VerComboAction implements Action {

    Integer idCombo;
    Integer idRestaurante;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        idCombo = Integer.parseInt(request.getParameter("id"));
        idRestaurante = Integer.parseInt(request.getParameter("id2"));
        ItemDeVenda combo = new Combo();
        ComboDAO.getInstance().searchComboEspecifico(idCombo, combo);
        List<Integer> idProdutos = ComboDAO.getInstance().searchComboProduto(combo.getCodigo());
        for (Integer idProduto : idProdutos) {
            Produto produto = ProdutoDAO.getInstance().listProduto(idProduto);
            produto.setQuantidade(ComboDAO.getInstance().searchProdutoComboQuantidade(combo.getCodigo(), produto.getProdutocod()));
            ItemDeVenda itemDeVenda = ItemDeVendaFactory.instanciaItemDeVenda(produto.getTipoItem());
            itemDeVenda.setCodigo(produto.getProdutocod()).setNome(produto.getNome())
                    .setValor(produto.getValor()).setDificuldade(produto.getDificuldade())
                    .setRestaurantecod(idRestaurante).setQuantidade(produto.getQuantidade());
            combo.adicionar(itemDeVenda);
        }
        List<ItemDeVenda> combos = new ArrayList<>();
        List<Integer> idCombos = ComboDAO.getInstance().searchComboDeCombo(idCombo);
        for (Integer idCombo1 : idCombos) {
            ItemDeVenda comb = new Combo();
            ComboDAO.getInstance().searchComboEspecifico(idCombo1, comb);
            comb.setQuantidade(ComboDAO.getInstance().searchComboDeComboQuantidade(combo.getCodigo(), idCombo1));
            combos.add(comb);
        }
        request.setAttribute("combos", combos);
        request.setAttribute("combo", combo);
        request.setAttribute("idRest", idRestaurante);
        RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-ver-combo.jsp");
        dispatcher.forward(request, response);
    }
}
