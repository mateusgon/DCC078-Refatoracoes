package persistence;

import EscalonamentoDePedido.TipoPedido;
import EscalonamentoDePedido.TipoPedidoFactory;
import PadraoComposite.Combo;
import PadraoComposite.ItemDeVenda;
import PadraoComposite.ItemDeVendaFactory;
import PadraoStateObserverMemento.Cliente;
import PadraoStateObserverMemento.Pedido;
import PadraoStateObserverMemento.PedidoEstado;
import PadraoStateObserverMemento.EstadoFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Pessoa;
import model.Produto;

public class PedidoDAO {

    private static final PedidoDAO instance = new PedidoDAO();
    private PreparedStatement inserePedido;
    private PreparedStatement buscaPedido;
    private PreparedStatement buscaPedidoCombo;
    private PreparedStatement buscaPedidoProduto;
    private PreparedStatement atualizaPedido;

    public static PedidoDAO getInstance() {
        return instance;
    }

    private PedidoDAO() {
    }

    public Integer savePedido(Pedido pedido) throws SQLException, ClassNotFoundException {
        inserePedido = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into pedido (estado, datapedido, restaurantecod, pessoacod) values (?, ?, ?, ?)");
        inserePedido.clearParameters();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Calendar cal = Calendar.getInstance();
        Date data = new Date();
        cal.setTime(data);
        java.sql.Timestamp dataSqlCriacao;
        dataSqlCriacao = new java.sql.Timestamp(data.getTime());
        inserePedido.setInt(1, 1);
        inserePedido.setTimestamp(2, dataSqlCriacao);
        inserePedido.setInt(3, pedido.getIdRestaurante());
        inserePedido.setInt(4, pedido.getIdCliente());
        inserePedido.execute();

        buscaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("select pedidocod from pedido where estado = ? and datapedido = ? and restaurantecod = ? and pessoacod = ?");
        buscaPedido.clearParameters();
        buscaPedido.setInt(1, 1);
        buscaPedido.setTimestamp(2, dataSqlCriacao);
        buscaPedido.setInt(3, pedido.getIdRestaurante());
        buscaPedido.setInt(4, pedido.getIdCliente());
        ResultSet resutaldo = buscaPedido.executeQuery();
        resutaldo.next();
        return resutaldo.getInt("pedidocod");
    }

    public void updatePedido(Pedido pedido) throws SQLException, ClassNotFoundException {
        atualizaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("update pedido set valor = ?, dificuldade = ?, notificado = ? where pedidocod = ?");
        atualizaPedido.clearParameters();
        atualizaPedido.setDouble(1, pedido.getValor());
        atualizaPedido.setInt(2, pedido.getDificuldade());
        atualizaPedido.setBoolean(3, false);
        atualizaPedido.setInt(4, pedido.getNumeroPedido());
        atualizaPedido.execute();
    }

    public void savePedidoProduto(List<ItemDeVenda> itens, Integer pedido) throws SQLException, ClassNotFoundException {
        inserePedido = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into pedido_produto (pedidocod, produtocod, quantidade) values (?, ?, ?)");
        for (ItemDeVenda iten : itens) {
            inserePedido.clearParameters();
            inserePedido.setInt(1, pedido);
            inserePedido.setInt(2, iten.getCodigo());
            inserePedido.setInt(3, iten.getQuantidade());
            inserePedido.execute();
        }
    }

    public void saveComboProduto(ItemDeVenda combo, Integer pedido) throws SQLException, ClassNotFoundException {
        inserePedido = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into pedido_combo (pedidocod, combocod, quantidade) values (?, ?, ?)");
        inserePedido.clearParameters();
        inserePedido.setInt(1, pedido);
        inserePedido.setInt(2, combo.getCodigo());
        inserePedido.setInt(3, combo.getQuantidade());
        inserePedido.execute();
    }

    public List<Pedido> searchPedido(Integer idUsuario) throws SQLException, ClassNotFoundException {
        List<Pedido> pedidos = new ArrayList<>();
        buscaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from pedido where pessoacod = ?");
        buscaPedido.clearParameters();
        buscaPedido.setInt(1, idUsuario);
        ResultSet resultado = buscaPedido.executeQuery();
        while (resultado.next()) {
            Pedido pedido = new Pedido();
            pedido = pedido.setNumeroPedido(resultado.getInt("pedidocod")).setValor(resultado.getDouble("valor")).setDificuldade(resultado.getInt("dificuldade")).setIdRestaurante(resultado.getInt("restaurantecod")).setDataPedido(resultado.getTimestamp("datapedido")).setIdCliente(idUsuario).setNotificado(resultado.getBoolean("notificado"));
            PedidoEstado estado = EstadoFactory.instanciaEstado(resultado.getInt("estado"));
            pedido.setEstado(estado);
            pedidos.add(pedido);
        }
        return pedidos;
    }

    public List<Pedido> searchPedidoRestaurante(Integer idRestaurante) throws SQLException, ClassNotFoundException {
        List<Pedido> pedidos = new ArrayList<>();
        buscaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from pedido where restaurantecod = ?");
        buscaPedido.clearParameters();
        buscaPedido.setInt(1, idRestaurante);
        ResultSet resultado = buscaPedido.executeQuery();
        while (resultado.next()) {
            Pedido pedido = new Pedido();
            pedido = pedido.setNumeroPedido(resultado.getInt("pedidocod")).setValor(resultado.getDouble("valor")).setDificuldade(resultado.getInt("dificuldade")).setIdRestaurante(resultado.getInt("restaurantecod")).setDataPedido(resultado.getTimestamp("datapedido")).setIdCliente(resultado.getInt("pessoacod")).setNotificado(resultado.getBoolean("notificado"));
            PedidoEstado estado = EstadoFactory.instanciaEstado(resultado.getInt("estado"));
            pedido.setEstado(estado);
            Pessoa pessoa = PessoaDAO.getInstance().buscaUsuario(pedido.getIdCliente());
            Cliente cliente = new Cliente(pessoa.getPessoaCod(), pessoa.getTipoPessoa(), pessoa.getNome(), pessoa.getEndereco(), pessoa.getEmail(), null, pessoa.getTelefone(), pedido);
            pedido.setCliente(cliente);
            TipoPedido tipoPedido = TipoPedidoFactory.instanciaTipoPedido(pedido.getDificuldade());
            pedido.setTipoPedido(tipoPedido);
            pedidos.add(pedido);
        }
        return pedidos;
    }

    public Pedido searchPedidoNumPedido(Integer numPedido) throws SQLException, ClassNotFoundException {
        List<ItemDeVenda> itens = new ArrayList<>();
        buscaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from pedido where pedidocod = ?");
        buscaPedido.clearParameters();
        buscaPedido.setInt(1, numPedido);
        Pedido pedido = new Pedido();
        ResultSet resultado = buscaPedido.executeQuery();
        while (resultado.next()) {
            pedido = pedido.setNumeroPedido(resultado.getInt("pedidocod")).setValor(resultado.getDouble("valor")).setDificuldade(resultado.getInt("dificuldade")).setIdRestaurante(resultado.getInt("restaurantecod")).setDataPedido(resultado.getTimestamp("datapedido")).setIdCliente(resultado.getInt("pessoacod")).setNotificado(resultado.getBoolean("notificado"));
            PedidoEstado estado = EstadoFactory.instanciaEstado(resultado.getInt("estado"));
            pedido.setEstado(estado);
            Pessoa pessoa = PessoaDAO.getInstance().buscaUsuario(pedido.getIdCliente());
            Cliente cliente = new Cliente(pessoa.getPessoaCod(), pessoa.getTipoPessoa(), pessoa.getNome(), pessoa.getEndereco(), pessoa.getEmail(), null, pessoa.getTelefone(), pedido);
            pedido.setCliente(cliente);
            TipoPedido tipoPedido = TipoPedidoFactory.instanciaTipoPedido(pedido.getDificuldade());
            pedido.setTipoPedido(tipoPedido);
        }

        buscaPedidoCombo = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from pedido_combo where pedidocod = ?");
        buscaPedidoCombo.clearParameters();
        buscaPedidoCombo.setInt(1, pedido.getNumeroPedido());
        ResultSet resultado2 = buscaPedidoCombo.executeQuery();
        while (resultado2.next()) {
            ItemDeVenda combo = new Combo();
            ComboDAO.getInstance().searchComboEspecifico(resultado2.getInt("combocod"), combo);
            combo.setQuantidade(resultado2.getInt("quantidade"));
            itens.add(combo);
        }

        buscaPedidoProduto = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from pedido_produto where pedidocod = ?");
        buscaPedidoProduto.clearParameters();
        buscaPedidoProduto.setInt(1, pedido.getNumeroPedido());
        ResultSet resultado3 = buscaPedidoProduto.executeQuery();
        while (resultado3.next()) {
            Produto produto = ProdutoDAO.getInstance().listProduto(resultado3.getInt("produtocod"));
            produto.setQuantidade(resultado3.getInt("quantidade"));
            ItemDeVenda itemDeVenda = ItemDeVendaFactory.instanciarItemDeVenda(produto);
            itens.add(itemDeVenda);
        }
        pedido.setItens(itens);
        return pedido;
    }

    public void updatePedidoEstado(Pedido pedido) throws SQLException, ClassNotFoundException {
        atualizaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("update pedido set estado = ? where pedidocod = ?");
        atualizaPedido.clearParameters();
        atualizaPedido.setInt(1, pedido.getEstado().getCodigoEstado());
        atualizaPedido.setInt(2, pedido.getNumeroPedido());
        atualizaPedido.executeUpdate();
    }

}
