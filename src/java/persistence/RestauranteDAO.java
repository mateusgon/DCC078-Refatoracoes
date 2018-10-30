package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Restaurante;


public class RestauranteDAO {

    private static RestauranteDAO instance = new RestauranteDAO();
    private PreparedStatement operacaoSalvarRestaurante;
    private PreparedStatement operacaoListarRestaurante;
    private PreparedStatement operacaoListAll;
    private PreparedStatement operacaoExcluirRestaurante;
    
    public static RestauranteDAO getInstance() {
        return instance;
    }

    public void save(Restaurante restaurante) throws SQLException, ClassNotFoundException {
        operacaoSalvarRestaurante = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into restaurante(nome, nomeFantasia, telefone, endereco, sigla) values (?, ?, ?, ?, ?)");
        operacaoListarRestaurante = DatabaseLocator.getInstance().getConnection().prepareStatement("select restaurantecod from restaurante where nome = ? and nomeFantasia = ?");
        operacaoListarRestaurante.clearParameters();
        operacaoSalvarRestaurante.clearParameters();
        operacaoSalvarRestaurante.setString(1, restaurante.getNome());
        operacaoSalvarRestaurante.setString(2, restaurante.getNomeFantasia());
        operacaoSalvarRestaurante.setString(3, restaurante.getTelefone());
        operacaoSalvarRestaurante.setString(4, restaurante.getEndereco());
        operacaoSalvarRestaurante.setString(5, restaurante.getSigla());
        operacaoSalvarRestaurante.execute();
        operacaoListarRestaurante.setString(1, restaurante.getNome());
        operacaoListarRestaurante.setString(2, restaurante.getNomeFantasia());
        ResultSet resultado = operacaoListarRestaurante.executeQuery();
        resultado.next();
        restaurante.setRestaurantecod(resultado.getInt("restaurantecod"));
     
    }

    private void closeResources(Connection conn, Statement st) {
        try {
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {

        }
    }

    public List<Restaurante> listAll() throws ClassNotFoundException, SQLException {

        List<Restaurante> restaurantes = new ArrayList<>();
        operacaoListAll = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from restaurante");
        operacaoListAll.clearParameters();
        ResultSet resultado = operacaoListAll.executeQuery();
        while (resultado.next()) {
            Restaurante restaurante = new Restaurante(resultado.getString("nome"), resultado.getString("nomefantasia"), resultado.getString("telefone"), resultado.getString("endereco"), resultado.getString("sigla"));
            restaurante.setRestaurantecod(resultado.getInt("restaurantecod"));
            restaurantes.add(restaurante);
        }
        return restaurantes;
    }

    public void delete(Integer restauranteCod) throws SQLException, ClassNotFoundException {
        operacaoExcluirRestaurante = DatabaseLocator.getInstance().getConnection().prepareStatement("delete from restaurante where restaurantecod = ?");
        operacaoExcluirRestaurante.clearParameters();
        operacaoExcluirRestaurante.setInt(1, restauranteCod);
        operacaoExcluirRestaurante.execute();
    }
}
