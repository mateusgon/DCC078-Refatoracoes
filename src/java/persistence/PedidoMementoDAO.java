package persistence;

import PadraoStateObserverMemento.PedidoEstado;
import PadraoStateObserverMemento.PedidoEstadoAberto;
import PadraoStateObserverMemento.PedidoEstadoEnviar;
import PadraoStateObserverMemento.PedidoEstadoPreparar;
import PadraoStateObserverMemento.PedidoEstadoPronto;
import PadraoStateObserverMemento.PedidoEstadoReceber;
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
//        deletePedido = DatabaseLocator.getInstance().getConnection().prepareStatement("delete from pedidomemento where data > ?");
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
        switch (pm.getEstado().getNomeEstado()) {
            case "Aberto":
                inserePedido.setInt(1, 1);
                break;
            case "Preparando":
                inserePedido.setInt(1, 2);
                break;
            case "Pronto":
                inserePedido.setInt(1, 3);
                break;
            case "Enviado":
                inserePedido.setInt(1, 4);
                break;
            case "Recebido":
                inserePedido.setInt(1, 5);
                break;
            default:
                break;
        }
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
            iniciaEstado(pm, resultado.getInt("estado"));
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
            iniciaEstado(pm, resultado.getInt("estado"));
            pm.setNomeEstado(null);
        }
        return pm;
    }

    public void Update(Integer codigoMemento, Integer codigoPedido) throws ClassNotFoundException, SQLException {
        atualizaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("update pedidomemento set atual = ? where pedidocod = ?");
        atualizaPedido.clearParameters();
        atualizaPedido.setInt(1, 0);
        atualizaPedido.setInt(2, codigoPedido);
        atualizaPedido = DatabaseLocator.getInstance().getConnection().prepareStatement("update pedidomemento set atual = ? where pedidomementocod = ?");
        atualizaPedido.clearParameters();
        atualizaPedido.setInt(1, 1);
        atualizaPedido.setInt(2, codigoMemento);
        atualizaPedido.executeUpdate();
    }

    public void iniciaEstado(PedidoMemento pm, Integer codigo) {
        switch (codigo) {
            case 1: {
                PedidoEstado estado = new PedidoEstadoAberto();
                pm.setEstado(estado);
                break;
            }
            case 2: {
                PedidoEstado estado = new PedidoEstadoPreparar();
                pm.setEstado(estado);
                break;
            }
            case 3: {
                PedidoEstado estado = new PedidoEstadoPronto();
                pm.setEstado(estado);
                break;
            }
            case 4: {
                PedidoEstado estado = new PedidoEstadoEnviar();
                pm.setEstado(estado);
                break;
            }
            case 5: {
                PedidoEstado estado = new PedidoEstadoReceber();
                pm.setEstado(estado);;
                break;
            }
        }
    }
}
