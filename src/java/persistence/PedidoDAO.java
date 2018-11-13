package persistence;

import EscalonamentoDePedido.TipoPedido;
import EscalonamentoDePedido.TipoPedidoEasy;
import EscalonamentoDePedido.TipoPedidoHard;
import EscalonamentoDePedido.TipoPedidoMedium;
import PadraoComposite.Bebida;
import PadraoComposite.ItemDeVenda;
import PadraoComposite.PratoDeEntrada;
import PadraoComposite.PratoPrincipal;
import PadraoComposite.Sobremesa;
import PadraoStateObserverMemento.Cliente;
import PadraoStateObserverMemento.Pedido;
import PadraoStateObserverMemento.PedidoEstado;
import PadraoStateObserverMemento.PedidoEstadoAberto;
import PadraoStateObserverMemento.PedidoEstadoEnviar;
import PadraoStateObserverMemento.PedidoEstadoPreparar;
import PadraoStateObserverMemento.PedidoEstadoPronto;
import PadraoStateObserverMemento.PedidoEstadoReceber;
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
        atualizaPedido.setInt(3, pedido.getNumeroPedido());
        atualizaPedido.execute();
    }

    public void savePedidoProduto(List<ItemDeVenda> itens, Integer pedido) throws SQLException, ClassNotFoundException {
        inserePedido = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into pedido_produto (pedidocod, produtocod) values (?, ?)");
        for (ItemDeVenda iten : itens) {
            inserePedido.clearParameters();
            inserePedido.setInt(1, pedido);
            inserePedido.setInt(2, iten.getCodigo());
            inserePedido.execute();
        }
    }

    public void saveComboProduto(ItemDeVenda combo, Integer pedido) throws SQLException, ClassNotFoundException {
        inserePedido = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into pedido_combo (pedidocod, combocod) values (?, ?)");
        inserePedido.clearParameters();
        inserePedido.setInt(1, pedido);
        inserePedido.setInt(2, combo.getCodigo());
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
            iniciaEstado(resultado.getInt("estado"), pedido);
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
            iniciaEstado(resultado.getInt("estado"), pedido);
            Pessoa pessoa = PessoaDAO.getInstance().buscaUsuario(pedido.getIdCliente());
            Cliente cliente = new Cliente(pessoa.getPessoaCod(), pessoa.getTipoPessoa(), pessoa.getNome(), pessoa.getEndereco(), pessoa.getEmail(), null, pessoa.getTelefone(), pedido);
            pedido.setCliente(cliente);
            iniciaTipoDoPedido(pedido.getDificuldade(), pedido);
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
            iniciaEstado(resultado.getInt("estado"), pedido);
            Pessoa pessoa = PessoaDAO.getInstance().buscaUsuario(pedido.getIdCliente());
            Cliente cliente = new Cliente(pessoa.getPessoaCod(), pessoa.getTipoPessoa(), pessoa.getNome(), pessoa.getEndereco(), pessoa.getEmail(), null, pessoa.getTelefone(), pedido);
            pedido.setCliente(cliente);
            iniciaTipoDoPedido(pedido.getDificuldade(), pedido);
        }

        buscaPedidoCombo = DatabaseLocator.getInstance().getConnection().prepareStatement("select combocod from pedido_combo where pedidocod = ?");
        buscaPedidoCombo.clearParameters();
        buscaPedidoCombo.setInt(1, pedido.getNumeroPedido());
        ResultSet resultado2 = buscaPedidoCombo.executeQuery();
        while (resultado2.next()) {
            itens.add(ComboDAO.getInstance().searchComboEspecifico(resultado2.getInt("combocod")));
        }

        buscaPedidoProduto = DatabaseLocator.getInstance().getConnection().prepareStatement("select produtocod from pedido_produto where pedidocod = ?");
        buscaPedidoProduto.clearParameters();
        buscaPedidoProduto.setInt(1, pedido.getNumeroPedido());
        ResultSet resultado3 = buscaPedidoProduto.executeQuery();
        while (resultado3.next()) {
            Produto produto = ProdutoDAO.getInstance().listProduto(resultado3.getInt("produtocod"));
            itens.add(instanciaObjeto(produto));
        }

        pedido.setItens(itens);
        return pedido;
    }

    public void updatePedido(Pedido pedido, Integer idEstado) throws SQLException, ClassNotFoundException {
        atualizaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("update pedido set estado = ? where pedidocod = ?");
        atualizaPedido.clearParameters();
        atualizaPedido.setInt(1, idEstado);
        atualizaPedido.setInt(2, pedido.getNumeroPedido());
        atualizaPedido.executeUpdate();
    }

    public void iniciaEstado(Integer codigoEstado, Pedido pedido) {
        switch (codigoEstado) {
            case 1: {
                PedidoEstado estado = new PedidoEstadoAberto(pedido);
                pedido.setEstado(estado);
                break;
            }
            case 2: {
                PedidoEstado estado = new PedidoEstadoPreparar();
                pedido.setEstado(estado);
                break;
            }
            case 3: {
                PedidoEstado estado = new PedidoEstadoPronto();
                pedido.setEstado(estado);
                break;
            }
            case 4: {
                PedidoEstado estado = new PedidoEstadoEnviar();
                pedido.setEstado(estado);
                break;
            }
            case 5: {
                PedidoEstado estado = new PedidoEstadoReceber();
                pedido.setEstado(estado);;
                break;
            }
        }
    }

    public void iniciaTipoDoPedido(Integer dificuldade, Pedido pedido) {
        switch (dificuldade) {
            case 1: {
                TipoPedido tp = new TipoPedidoEasy();
                pedido.setTipoPedido(tp);
                break;
            }
            case 2: {
                TipoPedido tp = new TipoPedidoMedium();
                pedido.setTipoPedido(tp);
                break;
            }
            case 3: {
                TipoPedido tp = new TipoPedidoHard();
                pedido.setTipoPedido(tp);
                break;
            }
        }
    }

    public ItemDeVenda instanciaObjeto(Produto produto) {
        ItemDeVenda item;
        switch (produto.getTipoItem()) {
            case 1:
                item = new PratoDeEntrada(produto.getProdutocod(), produto.getNome(), produto.getValor(), produto.getDificuldade(), produto.getRestaurantecod(), produto.getAtivado());
                break;
            case 2:
                item = new PratoPrincipal(produto.getProdutocod(), produto.getNome(), produto.getValor(), produto.getDificuldade(), produto.getRestaurantecod(), produto.getAtivado());

                break;
            case 3:
                item = new Bebida(produto.getProdutocod(), produto.getNome(), produto.getValor(), produto.getDificuldade(), produto.getRestaurantecod(), produto.getAtivado());

                break;
            case 4:
                item = new Sobremesa(produto.getProdutocod(), produto.getNome(), produto.getValor(), produto.getDificuldade(), produto.getRestaurantecod(), produto.getAtivado());
                break;
            default:
                item = new Sobremesa(produto.getProdutocod(), produto.getNome(), produto.getValor(), produto.getDificuldade(), produto.getRestaurantecod(), produto.getAtivado());
                break;
        }
        return item;
    }
}
