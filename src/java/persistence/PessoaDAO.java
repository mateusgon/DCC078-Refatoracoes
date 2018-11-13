package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Pessoa;
import model.Restaurante;

public class PessoaDAO {

    private static final PessoaDAO instance = new PessoaDAO();
    private PreparedStatement operacaoLoginUsuarioSistema;
    private PreparedStatement operacaoCriarCliente;
    private PreparedStatement operacaoListarUsuario;
    private PreparedStatement operacaoExcluirCliente;
    private PreparedStatement operacaoBuscaSuperUsuario;
    private PreparedStatement operacaoBuscaFuncionariosRestaurante;
    private PreparedStatement operacaoBuscarUsuario;
    private PreparedStatement operacaoAtualizarUsuario;

    public static PessoaDAO getInstance() {
        return instance;
    }

    private PessoaDAO() {

    }

    public void Autentica(Pessoa pessoa) throws SQLException, ClassNotFoundException {
        operacaoLoginUsuarioSistema = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from pessoa where email = ? and senha = ?");
        operacaoLoginUsuarioSistema.clearParameters();
        operacaoLoginUsuarioSistema.setString(1, pessoa.getEmail());
        operacaoLoginUsuarioSistema.setString(2, pessoa.getSenha());
        ResultSet resultado = operacaoLoginUsuarioSistema.executeQuery();
        while (resultado.next()) {
            pessoa.setNome(resultado.getString("nome")).setEmail(resultado.getString("email")).setTelefone(resultado.getString("telefone")).setEndereco(resultado.getString("endereco")).setPessoaCod(resultado.getInt("pessoaCod")).setRestauranteCod(resultado.getInt("restauranteCod")).setTipoPessoa(resultado.getInt("tipoPessoa"));
        }
    }

    public void saveSuperUsuarioeFuncionario(Pessoa pessoa) throws SQLException, ClassNotFoundException {
        operacaoCriarCliente = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into pessoa(nome, endereco, telefone, email, tipoPessoa, senha, restaurantecod) values (?, ?, ?, ?, ?, ?, ?)");
        operacaoListarUsuario = DatabaseLocator.getInstance().getConnection().prepareStatement("select pessoacod from pessoa where email = ? and senha = ?");
        operacaoCriarCliente.clearParameters();
        operacaoListarUsuario.clearParameters();
        operacaoCriarCliente.setString(1, pessoa.getNome());
        operacaoCriarCliente.setString(2, pessoa.getEndereco());
        operacaoCriarCliente.setString(3, pessoa.getTelefone());
        operacaoCriarCliente.setString(4, pessoa.getEmail());
        operacaoCriarCliente.setInt(5, pessoa.getTipoPessoa());
        operacaoCriarCliente.setString(6, pessoa.getSenha());
        operacaoCriarCliente.setInt(7, pessoa.getRestauranteCod());
        operacaoCriarCliente.execute();
        operacaoListarUsuario.setString(1, pessoa.getEmail());
        operacaoListarUsuario.setString(2, pessoa.getSenha());
        ResultSet resultado = operacaoListarUsuario.executeQuery();
        resultado.next();
        pessoa.setPessoaCod(resultado.getInt("pessoacod"));
    }

    public void saveCliente(Pessoa pessoa) throws SQLException, ClassNotFoundException {
        operacaoCriarCliente = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into pessoa(nome, endereco, telefone, email, tipoPessoa, senha) values (?, ?, ?, ?, ?, ?)");
        operacaoListarUsuario = DatabaseLocator.getInstance().getConnection().prepareStatement("select pessoacod from pessoa where email = ? and senha = ?");
        operacaoCriarCliente.clearParameters();
        operacaoListarUsuario.clearParameters();
        operacaoCriarCliente.setString(1, pessoa.getNome());
        operacaoCriarCliente.setString(2, pessoa.getEndereco());
        operacaoCriarCliente.setString(3, pessoa.getTelefone());
        operacaoCriarCliente.setString(4, pessoa.getEmail());
        operacaoCriarCliente.setInt(5, pessoa.getTipoPessoa());
        operacaoCriarCliente.setString(6, pessoa.getSenha());
        operacaoCriarCliente.execute();
        operacaoListarUsuario.setString(1, pessoa.getEmail());
        operacaoListarUsuario.setString(2, pessoa.getSenha());
        ResultSet resultado = operacaoListarUsuario.executeQuery();
        resultado.next();
        pessoa.setPessoaCod(resultado.getInt("pessoacod"));
    }

    public void deleteSuperUsuario(Integer restauranteCod) throws SQLException, ClassNotFoundException {
        operacaoExcluirCliente = DatabaseLocator.getInstance().getConnection().prepareStatement("delete from pessoa where restauranteCod = ?");
        operacaoExcluirCliente.clearParameters();
        operacaoExcluirCliente.setInt(1, restauranteCod);
        operacaoExcluirCliente.execute();
    }
    
    public void deleteUsuario(Integer idUsuario) throws SQLException, ClassNotFoundException {
        operacaoExcluirCliente = DatabaseLocator.getInstance().getConnection().prepareStatement("delete from pessoa where pessoaCod = ?");
        operacaoExcluirCliente.clearParameters();
        operacaoExcluirCliente.setInt(1, idUsuario);
        operacaoExcluirCliente.execute();
    }

    public void buscaSuperUsuario(Restaurante restaurante) throws SQLException, ClassNotFoundException {
        operacaoBuscaSuperUsuario = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from pessoa where restauranteCod = ? and tipoPessoa = ?");
        operacaoBuscaSuperUsuario.clearParameters();
        operacaoBuscaSuperUsuario.setInt(1, restaurante.getRestaurantecod());
        operacaoBuscaSuperUsuario.setInt(2, 7);
        ResultSet resultado = operacaoBuscaSuperUsuario.executeQuery();
        while (resultado.next()) {
            Pessoa pessoa = new Pessoa();
            pessoa = pessoa.setNome(resultado.getString("nome")).setEmail(resultado.getString("email")).setTelefone(resultado.getString("telefone")).setEndereco(resultado.getString("endereco")).setPessoaCod(resultado.getInt("pessoaCod")).setRestauranteCod(resultado.getInt("restauranteCod")).setTipoPessoa(resultado.getInt("tipoPessoa"));
            restaurante.getSuperUsuarios().add(pessoa);
        }
    }

    public List<Pessoa> buscaFuncionarioRestaurante(Integer restauranteCod) throws SQLException, ClassNotFoundException {
        List<Pessoa> pessoas = new ArrayList<>();
        operacaoBuscaFuncionariosRestaurante = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from pessoa where restauranteCod = ? and tipoPessoa != ?");
        operacaoBuscaFuncionariosRestaurante.clearParameters();
        operacaoBuscaFuncionariosRestaurante.setInt(1, restauranteCod);
        operacaoBuscaFuncionariosRestaurante.setInt(2, 7);
        ResultSet resultado = operacaoBuscaFuncionariosRestaurante.executeQuery();
        while (resultado.next()) {
            Pessoa pessoa = new Pessoa();
            pessoa = pessoa.setNome(resultado.getString("nome")).setEmail(resultado.getString("email")).setTelefone(resultado.getString("telefone")).setEndereco(resultado.getString("endereco")).setPessoaCod(resultado.getInt("pessoaCod")).setRestauranteCod(resultado.getInt("restauranteCod")).setTipoPessoa(resultado.getInt("tipoPessoa"));
            pessoas.add(pessoa);
        }
        return pessoas;
    }

    public Pessoa buscaUsuario(Integer codigoUsuario) throws SQLException, ClassNotFoundException {
        operacaoBuscarUsuario = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from pessoa where pessoacod = ?");
        operacaoBuscarUsuario.clearParameters();
        operacaoBuscarUsuario.setInt(1, codigoUsuario);
        Pessoa pessoa = null;
        ResultSet resultado = operacaoBuscarUsuario.executeQuery();
        while (resultado.next()) {
            pessoa = new Pessoa();
            pessoa = pessoa.setNome(resultado.getString("nome")).setSenha(resultado.getString("senha")).setEmail(resultado.getString("email")).setTelefone(resultado.getString("telefone")).setEndereco(resultado.getString("endereco")).setPessoaCod(resultado.getInt("pessoaCod")).setRestauranteCod(resultado.getInt("restauranteCod")).setTipoPessoa(resultado.getInt("tipoPessoa"));
        }
        return pessoa;
    }
    
    public void updateUsuario (Pessoa pessoa) throws SQLException, ClassNotFoundException
    {
        operacaoAtualizarUsuario = DatabaseLocator.getInstance().getConnection().prepareStatement("update pessoa set nome = ?, senha = ?, email = ?, telefone = ?, endereco = ?, tipoPessoa = ? where pessoaCod = ?");
        operacaoAtualizarUsuario.clearParameters();
        operacaoAtualizarUsuario.setString(1, pessoa.getNome());
        operacaoAtualizarUsuario.setString(2, pessoa.getSenha());
        operacaoAtualizarUsuario.setString(3, pessoa.getEmail());
        operacaoAtualizarUsuario.setString(4, pessoa.getTelefone());
        operacaoAtualizarUsuario.setString(5, pessoa.getEndereco());
        operacaoAtualizarUsuario.setInt(6, pessoa.getTipoPessoa());
        operacaoAtualizarUsuario.setInt(7, pessoa.getPessoaCod());
        operacaoAtualizarUsuario.executeUpdate();
    }
}
