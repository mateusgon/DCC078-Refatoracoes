package action;

import PadraoStateObserverMemento.Pedido;
import PadraoStateObserverMemento.PedidoMemento;
import controller.Action;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.PedidoDAO;
import persistence.PedidoMementoDAO;

public class ReverterPedidoAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer idCodigoMemento = Integer.parseInt(request.getParameter("id"));
        PedidoMemento pm = PedidoMementoDAO.getInstance().searchMementoPosicao(idCodigoMemento);
        Pedido pedido = PedidoDAO.getInstance().searchPedidoNumPedido(pm.getNumeroPedido());
        pedido.restoreFromMemento(pm);
        PedidoDAO.getInstance().updatePedidoEstado(pedido);
        PedidoMementoDAO.getInstance().Update(idCodigoMemento, pedido.getNumeroPedido());
        List<PedidoMemento> pms = PedidoMementoDAO.getInstance().searchMemento(pedido.getNumeroPedido());
        request.setAttribute("memento", pms);
        RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-superusuario-reverter-estado.jsp");
        dispatcher.forward(request, response);
    }


}
