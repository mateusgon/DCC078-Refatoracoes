package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Restaurante;

public class RestauranteDAO {

    private static final RestauranteDAO instance = new RestauranteDAO();
    private PreparedStatement operacaoSalvarRestaurante;
    private PreparedStatement operacaoListarRestaurante;
    private PreparedStatement operacaoListAll;
    private PreparedStatement operacaoUpdate;
    private PreparedStatement operacaoExcluirRestaurante;

    public static RestauranteDAO getInstance() {
        return instance;
    }

    private RestauranteDAO() {
    }

    public void save(Restaurante restaurante) throws SQLException, ClassNotFoundException {
        operacaoSalvarRestaurante = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into restaurante(nome, nomeFantasia, telefone, endereco, sigla, ativado) values (?, ?, ?, ?, ?, ?)");
        operacaoListarRestaurante = DatabaseLocator.getInstance().getConnection().prepareStatement("select restaurantecod from restaurante where nome = ? and nomeFantasia = ?");
        operacaoListarRestaurante.clearParameters();
        operacaoSalvarRestaurante.clearParameters();
        operacaoSalvarRestaurante.setString(1, restaurante.getNome());
        operacaoSalvarRestaurante.setString(2, restaurante.getNomeFantasia());
        operacaoSalvarRestaurante.setString(3, restaurante.getTelefone());
        operacaoSalvarRestaurante.setString(4, restaurante.getEndereco());
        operacaoSalvarRestaurante.setString(5, restaurante.getSigla());
        operacaoSalvarRestaurante.setInt(6, 1);
        operacaoSalvarRestaurante.execute();
        operacaoListarRestaurante.setString(1, restaurante.getNome());
        operacaoListarRestaurante.setString(2, restaurante.getNomeFantasia());
        ResultSet resultado = operacaoListarRestaurante.executeQuery();
        resultado.next();
        restaurante.setRestaurantecod(resultado.getInt("restaurantecod"));

    }

    public List<Restaurante> listAll() throws ClassNotFoundException, SQLException {

        List<Restaurante> restaurantes = new ArrayList<>();
        operacaoListAll = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from restaurante");
        operacaoListAll.clearParameters();
        ResultSet resultado = operacaoListAll.executeQuery();
        while (resultado.next()) {
            Restaurante restaurante = new Restaurante();
            restaurante = restaurante.setNome(resultado.getString("nome")).setNomeFantasia(resultado.getString("nomefantasia")).setTelefone(resultado.getString("telefone")).setEndereco(resultado.getString("endereco")).setSigla(resultado.getString("sigla")).setRestaurantecod(resultado.getInt("restaurantecod")).setAtivado(resultado.getInt("ativado"));
            restaurantes.add(restaurante);
        }
        return restaurantes;
    }

    public Restaurante listarRestaurante(Integer codigoRestaurante) throws ClassNotFoundException, SQLException {
        operacaoListarRestaurante = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from restaurante where restaurantecod = ?");
        operacaoListarRestaurante.clearParameters();
        operacaoListarRestaurante.setInt(1, codigoRestaurante);
        Restaurante restaurante = null;
        ResultSet resultado = operacaoListarRestaurante.executeQuery();
        while (resultado.next()) {
            restaurante = new Restaurante();
            restaurante = restaurante.setNome(resultado.getString("nome")).setNomeFantasia(resultado.getString("nomefantasia")).setTelefone(resultado.getString("telefone")).setEndereco(resultado.getString("endereco")).setSigla(resultado.getString("sigla")).setRestaurantecod(resultado.getInt("restaurantecod")).setAtivado(resultado.getInt("ativado"));
        }
        return restaurante;
    }

    public void update(Restaurante restaurante) throws ClassNotFoundException, SQLException {
        operacaoUpdate = DatabaseLocator.getInstance().getConnection().prepareStatement("update restaurante set nome = ?, nomefantasia = ?, telefone = ?, endereco = ?, sigla = ? where restaurantecod = ?");
        operacaoUpdate.clearParameters();
        operacaoUpdate.setString(1, restaurante.getNome());
        operacaoUpdate.setString(2, restaurante.getNomeFantasia());
        operacaoUpdate.setString(3, restaurante.getTelefone());
        operacaoUpdate.setString(4, restaurante.getEndereco());
        operacaoUpdate.setString(5, restaurante.getSigla());
        operacaoUpdate.setInt(6, restaurante.getRestaurantecod());
        operacaoUpdate.executeUpdate();
    }

    public void delete(Integer restauranteCod) throws SQLException, ClassNotFoundException {
        operacaoExcluirRestaurante = DatabaseLocator.getInstance().getConnection().prepareStatement("update restaurante set ativado = ? where restaurantecod = ?");
        operacaoExcluirRestaurante.clearParameters();
        operacaoExcluirRestaurante.setInt(1, 0);
        operacaoExcluirRestaurante.setInt(2, restauranteCod);
        operacaoExcluirRestaurante.execute();
    }
}
