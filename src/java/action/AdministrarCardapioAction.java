package action;

import controller.Action;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdministrarCardapioAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer idRestaurante = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("idRestaurante", idRestaurante);
        RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-superusuario-administrar-cardapio.jsp");
        dispatcher.forward(request, response);
    }

}
