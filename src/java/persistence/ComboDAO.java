package persistence;

import PadraoComposite.Combo;
import PadraoComposite.ItemDeVenda;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComboDAO {

    private static final ComboDAO instance = new ComboDAO();
    private PreparedStatement insereCombo;
    private PreparedStatement buscaCombo;
    private PreparedStatement insereComboProduto;
    private PreparedStatement excluirCombo;
    private PreparedStatement excluirComboProduto;

    public static ComboDAO getInstance() {
        return instance;
    }

    private ComboDAO() {

    }

    public void saveCombo(ItemDeVenda combo) throws ClassNotFoundException, SQLException, Exception {
        insereCombo = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into combo (nome, valor, dificuldade, restaurantecod, ativado) values (?, ?, ?, ?, ?)");
        insereCombo.clearParameters();
        insereCombo.setString(1, combo.getNome());
        insereCombo.setDouble(2, combo.getValor());
        insereCombo.setInt(3, combo.getDificuldade());
        insereCombo.setInt(4, combo.getRestaurantecod());
        insereCombo.setInt(5, 1);
        insereCombo.execute();

        buscaCombo = DatabaseLocator.getInstance().getConnection().prepareStatement("select combocod from combo where nome = ? and valor = ? and dificuldade = ? and restaurantecod = ?");
        buscaCombo.clearParameters();
        buscaCombo.setString(1, combo.getNome());
        buscaCombo.setDouble(2, combo.getValor());
        buscaCombo.setInt(3, combo.getDificuldade());
        buscaCombo.setInt(4, combo.getRestaurantecod());
        ResultSet resutaldo = buscaCombo.executeQuery();
        resutaldo.next();
        combo.setCodigo(resutaldo.getInt("combocod"));
    }

    public void saveComboProduto(ItemDeVenda combo) throws ClassNotFoundException, SQLException, Exception {
        List<ItemDeVenda> itens = combo.getItens();
        insereComboProduto = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into combo_produto (combocod, produtocod, quantidade) values (?, ?, ?)");
        for (ItemDeVenda iten : itens) {
            insereComboProduto.clearParameters();
            insereComboProduto.setInt(1, combo.getCodigo());
            insereComboProduto.setInt(2, iten.getCodigo());
            insereComboProduto.setInt(3, iten.getQuantidade());
            insereComboProduto.execute();
        }
    }

    public void saveComboDeCombo(ItemDeVenda combo) throws ClassNotFoundException, SQLException, Exception {
        List<ItemDeVenda> itens = combo.getItens();
        insereComboProduto = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into combo_decombo (quantidade, combocod_criador, combocod_receptor) values (?, ?, ?)");
        for (ItemDeVenda iten : itens) {
            insereComboProduto.clearParameters();
            insereComboProduto.setInt(1, iten.getQuantidade());
            insereComboProduto.setInt(2, combo.getCodigo());
            insereComboProduto.setInt(3, iten.getCodigo());
            insereComboProduto.execute();
        }
    }

    public List<ItemDeVenda> searchCombo(Integer idRest) throws ClassNotFoundException, SQLException {
        buscaCombo = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from combo where restaurantecod = ?");
        buscaCombo.clearParameters();
        buscaCombo.setInt(1, idRest);
        ArrayList<ItemDeVenda> combos = new ArrayList<>();
        ResultSet resultado = buscaCombo.executeQuery();
        while (resultado.next()) {
            ItemDeVenda combo = new Combo();
            combo = combo.setCodigo(resultado.getInt("combocod")).setNome(resultado.getString("nome")).setValor(resultado.getDouble("valor")).setDificuldade(resultado.getInt("dificuldade")).setRestaurantecod(idRest).setAtivado(resultado.getInt("ativado"));
            combos.add(combo);
        }
        return combos;
    }

    public List<Integer> searchComboProduto(Integer comboCod) throws ClassNotFoundException, SQLException {
        buscaCombo = DatabaseLocator.getInstance().getConnection().prepareStatement("select produtocod from combo_produto where combocod = ?");
        buscaCombo.clearParameters();
        buscaCombo.setInt(1, comboCod);
        ArrayList<Integer> idProdutos = new ArrayList<>();
        ResultSet resultado = buscaCombo.executeQuery();
        while (resultado.next()) {
            Integer idProduto = resultado.getInt("produtoCod");
            idProdutos.add(idProduto);
        }
        return idProdutos;
    }

    public List<Integer> searchComboDeCombo(Integer comboCod) throws ClassNotFoundException, SQLException {
        buscaCombo = DatabaseLocator.getInstance().getConnection().prepareStatement("select combo_receptor from combo_decombo where combo_criador = ?");
        buscaCombo.clearParameters();
        buscaCombo.setInt(1, comboCod);
        ArrayList<Integer> idProdutos = new ArrayList<>();
        ResultSet resultado = buscaCombo.executeQuery();
        while (resultado.next()) {
            Integer idProduto = resultado.getInt("combo_receptor");
            idProdutos.add(idProduto);
        }
        return idProdutos;
    }

    public void searchComboEspecifico(Integer idCOmbo, ItemDeVenda combo) throws ClassNotFoundException, SQLException {
        buscaCombo = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from combo where combocod = ?");
        buscaCombo.clearParameters();
        buscaCombo.setInt(1, idCOmbo);
        ResultSet resultado = buscaCombo.executeQuery();
        while (resultado.next()) {
            combo.setCodigo(resultado.getInt("combocod")).setNome(resultado.getString("nome")).setValor(resultado.getDouble("valor")).setDificuldade(resultado.getInt("dificuldade")).setRestaurantecod(resultado.getInt("restaurantecod")).setAtivado(resultado.getInt("ativado"));
        }
    }

    public Integer searchDificuldade(Integer idCombo) throws ClassNotFoundException, SQLException {
        buscaCombo = DatabaseLocator.getInstance().getConnection().prepareStatement("select dificuldade from combo where combocod = ?");
        buscaCombo.clearParameters();
        buscaCombo.setInt(1, idCombo);
        Integer dificuldade = -1;
        ResultSet resultado = buscaCombo.executeQuery();
        while (resultado.next()) {
            dificuldade = resultado.getInt("dificuldade");
        }
        return dificuldade;
    }

    public void deleteCombo(Integer idCombo) throws ClassNotFoundException, SQLException {
        excluirCombo = DatabaseLocator.getInstance().getConnection().prepareStatement("update combo set ativado = ? where combocod = ?");
        excluirCombo.clearParameters();
        excluirCombo.setInt(1, 0);
        excluirCombo.setInt(2, idCombo);
        excluirCombo.execute();
    }

    public void updateDificuldade(Integer idCombo, Integer dificuldade) throws ClassNotFoundException, SQLException {
        excluirCombo = DatabaseLocator.getInstance().getConnection().prepareStatement("update combo set dificuldade = ? where combocod = ?");
        excluirCombo.clearParameters();
        excluirCombo.setInt(1, dificuldade);
        excluirCombo.setInt(2, idCombo);
        excluirCombo.execute();
    }
}
