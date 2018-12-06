package action;

import PadraoComposite.ItemDeVenda;
import PadraoComposite.ItemDeVendaFactory;
import controller.Action;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Produto;
import persistence.ComboDAO;
import persistence.ProdutoDAO;

public class VerCardapioAction implements Action {

    private static ItemDeVenda itemDeVenda;
    private static final List<ItemDeVenda> pratosDeEntrada = new ArrayList<>();
    private static final List<ItemDeVenda> pratosPrincipais = new ArrayList<>();
    private static final List<ItemDeVenda> bebidas = new ArrayList<>();
    private static final List<ItemDeVenda> sobremesas = new ArrayList<>();
    List<ItemDeVenda> combos = new ArrayList<>();
    Integer idRestaurante;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        pratosDeEntrada.clear();
        pratosPrincipais.clear();
        bebidas.clear();
        sobremesas.clear();
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

    public void preencherListaTipoDoItemDeVenda(Produto produto) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        itemDeVenda = ItemDeVendaFactory.instanciarItemDeVenda(produto);
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "setPratoDeEntrada");
        map.put(2, "setPratoPrincipal");
        map.put(3, "setBebida");
        map.put(4, "setSobremesa");
        Class classe = Class.forName("action.VerCardapioAction");
        Object objeto = this;
        Method metodo = classe.getMethod(map.get(produto.getTipoItem()));
        metodo.invoke(objeto);
    }

    public static void setPratoDeEntrada() {
        pratosDeEntrada.add(itemDeVenda);
    }

    public static void setPratoPrincipal() {
        pratosPrincipais.add(itemDeVenda);
    }

    public static void setBebida() {
        bebidas.add(itemDeVenda);
    }

    public static void setSobremesa() {
        sobremesas.add(itemDeVenda);
    }

}
