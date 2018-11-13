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
            instanciaCombo(produto, combo);
        }
        request.setAttribute("combo", combo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-ver-combo.jsp");
        dispatcher.forward(request, response);

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
