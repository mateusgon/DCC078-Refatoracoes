package persistence;

import PadraoStateObserverMemento.EstadoFactory;
import PadraoStateObserverMemento.PedidoEstado;
import PadraoStateObserverMemento.PedidoMemento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PedidoMementoDAO {

    private static final PedidoMementoDAO instance = new PedidoMementoDAO();
    private PreparedStatement inserePedido;
    private PreparedStatement buscaPedido;
    private PreparedStatement atualizaPedido;
    private PreparedStatement deletePedido;

    public static PedidoMementoDAO getInstance() {
        return instance;
    }

    private PedidoMementoDAO() {
    }

    public void saveMemento(PedidoMemento pm) throws ClassNotFoundException, SQLException {
        buscaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from pedidomemento where atual = 1 and pedidocod = ?");
        buscaPedido.clearParameters();
        buscaPedido.setInt(1, pm.getNumeroPedido());
        ResultSet resultado = buscaPedido.executeQuery();
        PedidoMemento pm2 = null;
        while (resultado.next()) {
            pm2 = new PedidoMemento();
            pm2.setMementoCod(resultado.getInt("pedidomementocod")).setAtual(resultado.getInt("atual")).setNumeroPedido(pm.getNumeroPedido()).setDataModificacao(resultado.getTimestamp("data"));
            PedidoEstado estado = EstadoFactory.instanciaEstado(resultado.getInt("estado"));
            pm2.setEstado(estado);
            pm.setNomeEstado(null);
        }

        if (pm2 != null) {
            deletePedido = DatabaseLocator.getInstance().getConnection().prepareStatement("delete from pedidomemento where data > ? and pedidocod = ?");
            deletePedido.clearParameters();
            DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            Calendar cal2 = Calendar.getInstance();
            Date data2 = pm2.getDataModificacao();
            cal2.setTime(data2);
            java.sql.Timestamp dataSqlCriacao2;
            dataSqlCriacao2 = new java.sql.Timestamp(data2.getTime());
            deletePedido.setTimestamp(1, dataSqlCriacao2);
            deletePedido.setInt(2, pm2.getNumeroPedido());
            deletePedido.executeUpdate();
        }
        buscaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("update pedidomemento set atual = ? where pedidocod = ?");
        buscaPedido.clearParameters();
        buscaPedido.setInt(1, 0);
        buscaPedido.setInt(2, pm.getNumeroPedido());
        buscaPedido.executeUpdate();

        inserePedido = DatabaseLocator.getInstance().getConnection().prepareStatement("insert into pedidomemento (estado, atual, data, pedidocod) values (?, ?, ?, ?)");
        inserePedido.clearParameters();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        Calendar cal = Calendar.getInstance();
        Date data = new Date();
        cal.setTime(data);
        java.sql.Timestamp dataSqlCriacao;
        dataSqlCriacao = new java.sql.Timestamp(data.getTime());
        inserePedido.setInt(1, pm.getEstado().getCodigoEstado());
        inserePedido.setInt(2, 1);
        inserePedido.setTimestamp(3, dataSqlCriacao);
        inserePedido.setInt(4, pm.getNumeroPedido());
        inserePedido.execute();
    }

    public List<PedidoMemento> searchMemento(Integer pedidocod) throws ClassNotFoundException, SQLException {
        List<PedidoMemento> pedidos = new ArrayList<>();
        buscaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from pedidomemento where pedidocod = ?");
        buscaPedido.clearParameters();
        buscaPedido.setInt(1, pedidocod);
        ResultSet resultado = buscaPedido.executeQuery();
        while (resultado.next()) {
            PedidoMemento pm = new PedidoMemento();
            pm.setMementoCod(resultado.getInt("pedidomementocod")).setAtual(resultado.getInt("atual")).setNumeroPedido(pedidocod).setDataModificacao(resultado.getTimestamp("data"));
            PedidoEstado estado = EstadoFactory.instanciaEstado(resultado.getInt("estado"));
            pm.setEstado(estado);
            pm.setNomeEstado(null);
            pedidos.add(pm);
        }
        return pedidos;
    }

    public PedidoMemento searchMementoPosicao(Integer pedidoMemento) throws ClassNotFoundException, SQLException {
        buscaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("select * from pedidomemento where pedidomementocod = ?");
        buscaPedido.clearParameters();
        buscaPedido.setInt(1, pedidoMemento);
        ResultSet resultado = buscaPedido.executeQuery();
        PedidoMemento pm = new PedidoMemento();
        while (resultado.next()) {
            pm.setMementoCod(resultado.getInt("pedidomementocod")).setAtual(resultado.getInt("atual")).setNumeroPedido(resultado.getInt("pedidocod")).setDataModificacao(resultado.getTimestamp("data"));
            PedidoEstado estado = EstadoFactory.instanciaEstado(resultado.getInt("estado"));
            pm.setEstado(estado);
            pm.setNomeEstado(null);
        }
        return pm;
    }

    public void Update(Integer codigoMemento, Integer codigoPedido) throws ClassNotFoundException, SQLException {
        atualizaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("update pedidomemento set atual = ? where pedidocod = ?");
        atualizaPedido.clearParameters();
        atualizaPedido.setInt(1, 0);
        atualizaPedido.setInt(2, codigoPedido);
        atualizaPedido.executeUpdate();
        atualizaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("update pedidomemento set atual = ? where pedidomementocod = ?");
        atualizaPedido.clearParameters();
        atualizaPedido.setInt(1, 1);
        atualizaPedido.setInt(2, codigoMemento);
        atualizaPedido.executeUpdate();
    }

}
