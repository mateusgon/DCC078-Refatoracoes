package PadraoChainOfResponsibility;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pessoa;

public class LoginCliente extends Login {

    public LoginCliente() {
    }

    public LoginCliente(Integer identificador, Login proximo) {
        super(identificador, proximo);
        this.identificador = 1;
    }

    @Override
    public void direcionarPagina(HttpServletRequest request, HttpServletResponse response, Pessoa pessoa) throws SQLException, ClassNotFoundException, ServletException, IOException {
        if (pessoa != null) {
            request.setAttribute("pessoa", pessoa);
            RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-cliente.jsp");
            dispatcher.forward(request, response);
        } else {
            RequestDispatcher dispacher = request.getRequestDispatcher("form-login-cliente.jsp");
            dispacher.forward(request, response);
        }
    }
}
