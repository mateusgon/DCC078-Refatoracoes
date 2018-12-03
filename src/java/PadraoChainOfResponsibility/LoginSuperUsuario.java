package PadraoChainOfResponsibility;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pessoa;

public class LoginSuperUsuario extends Login {

    public LoginSuperUsuario() {
    }

    public LoginSuperUsuario(Integer identificador, Login proximo) {
        super(identificador, proximo);
        this.identificador = 3;
    }

    @Override
    public void direcionarPagina(HttpServletRequest request, HttpServletResponse response, Pessoa pessoa) throws SQLException, ClassNotFoundException, ServletException, IOException {
        try {
            if (7 == pessoa.getTipoPessoa()) {
                request.setAttribute("idRest", pessoa.getRestauranteCod());
                RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-superusuario-restaurante.jsp");
                dispatcher.forward(request, response);
            } else if (8 == pessoa.getTipoPessoa()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-superusuario.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("erro.jsp");
            }
        } catch (Exception ex) {
            response.sendRedirect("form-login-superusuario.jsp");
        }
    }
}
