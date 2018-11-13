package action;

import PadraoStateObserverMemento.Pedido;
import controller.Action;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.PedidoDAO;

public class MudarEstadoAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer idPedido = Integer.parseInt(request.getParameter("id"));
        Integer idChefe = Integer.parseInt(request.getParameter("id2"));
        Pedido pedido = PedidoDAO.getInstance().searchPedidoNumPedido(idPedido);
        List<String> nomeEstados = new ArrayList<>();
        nomeEstados.add("Aberto");
        nomeEstados.add("Preparar");
        nomeEstados.add("Pronto");
        nomeEstados.add("Enviar");
        nomeEstados.add("Receber");
        request.setAttribute("estadoAtual", pedido.getNomeEstado());
        request.setAttribute("estados", nomeEstados);
        request.setAttribute("idPed", idPedido);
        request.setAttribute("idChefe", idChefe);
        RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-mudar-estado.jsp");
    }

}
