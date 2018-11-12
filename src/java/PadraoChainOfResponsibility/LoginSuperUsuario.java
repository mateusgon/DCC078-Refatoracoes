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

public class LoginSuperUsuario extends Login {

    public LoginSuperUsuario() {
    }

    public LoginSuperUsuario(Integer identificador, Login proximo) {
        super(identificador, proximo);
        this.identificador = 3;
    }

    @Override
    public void direcionarPagina(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        String nome = request.getParameter("nome");
        String senha = request.getParameter("password");
        boolean flagAutenticado = false;
        if (nome.equals("") || senha.equals("")) {
            try {
                response.sendRedirect("erro.jsp");
            } catch (IOException ex) {
                Logger.getLogger(LoginSuperUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Pessoa pessoa = new Pessoa();
            pessoa.setEmail(nome).setSenha(senha);
            PessoaDAO.getInstance().Autentica(pessoa);
            if (7 == pessoa.getTipoPessoa()) {
                try {
                    request.setAttribute("idRest", pessoa.getRestauranteCod());
                    RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-superusuario-restaurante.jsp");
                    dispatcher.forward(request, response);
                } catch (ServletException ex) {
                    Logger.getLogger(LoginSuperUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LoginSuperUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (8 == pessoa.getTipoPessoa()) {
                try {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-superusuario.jsp");
                    dispatcher.forward(request, response);
                } catch (ServletException ex) {
                    Logger.getLogger(LoginSuperUsuario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(LoginSuperUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    response.sendRedirect("erro.jsp");
                } catch (IOException ex) {
                    Logger.getLogger(LoginSuperUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}


