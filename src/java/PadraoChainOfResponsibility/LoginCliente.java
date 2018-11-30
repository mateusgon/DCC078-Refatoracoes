package PadraoChainOfResponsibility;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pessoa;
import persistence.PessoaDAO;

public class LoginCliente extends Login {

    public LoginCliente() {
    }

    public LoginCliente(Integer identificador, Login proximo) {
        super(identificador, proximo);
        this.identificador = 1;
    }

    @Override
    public void direcionarPagina(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        try {
            String email = request.getParameter("email");
            String senha = request.getParameter("password");
            boolean flagAutenticado = false;
            if (email.equals("") || senha.equals("")) {
                response.sendRedirect("erro.jsp"); 
            } else {
                Pessoa pessoa = new Pessoa();
                pessoa = pessoa.setEmail(email).setSenha(senha);
                PessoaDAO.getInstance().Autentica(pessoa);
                request.setAttribute("pessoa", pessoa);
                RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-cliente.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception ex) {
            try {
                RequestDispatcher dispacher = request.getRequestDispatcher("form-login-cliente.jsp");
                dispacher.forward(request, response);
            } catch (ServletException ex1) {
                Logger.getLogger(LoginCliente.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(LoginCliente.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

}
