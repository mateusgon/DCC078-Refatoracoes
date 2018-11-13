package action;

import PadraoComposite.Bebida;
import PadraoComposite.Combo;
import PadraoComposite.ItemDeVenda;
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
            instanciaCombo(produto, combo);
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

    public void instanciaCombo(Produto produto, ItemDeVenda combo) throws Exception {
        switch (produto.getTipoItem()) {
            case 1:
                ItemDeVenda entrada = new PratoDeEntrada();
                entrada = entrada.setCodigo(produto.getProdutocod()).setNome(produto.getNome()).setValor(produto.getValor()).setDificuldade(produto.getDificuldade()).setRestaurantecod(idRestaurante).setQuantidade(produto.getQuantidade());
                combo.adicionar(entrada);
                break;
            case 2:
                ItemDeVenda principal = new PratoPrincipal();
                principal = principal.setCodigo(produto.getProdutocod()).setNome(produto.getNome()).setValor(produto.getValor()).setDificuldade(produto.getDificuldade()).setRestaurantecod(idRestaurante).setQuantidade(produto.getQuantidade());
                combo.adicionar(principal);
                break;
            case 3:
                ItemDeVenda bebida = new Bebida();
                bebida = bebida.setCodigo(produto.getProdutocod()).setNome(produto.getNome()).setValor(produto.getValor()).setDificuldade(produto.getDificuldade()).setRestaurantecod(idRestaurante).setQuantidade(produto.getQuantidade());
                combo.adicionar(bebida);
                break;
            case 4:
                ItemDeVenda sobremesa = new Sobremesa();
                sobremesa = sobremesa.setCodigo(produto.getProdutocod()).setNome(produto.getNome()).setValor(produto.getValor()).setDificuldade(produto.getDificuldade()).setRestaurantecod(idRestaurante).setQuantidade(produto.getQuantidade());
                combo.adicionar(sobremesa);
                break;
            default:
                break;
        }
    }

}
