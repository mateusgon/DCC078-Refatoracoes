package PadraoChainOfResponsibility;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pessoa;
import persistence.PessoaDAO;

public abstract class Login {

    protected Integer identificador;
    private Login proximo;

    public Login(Integer identificador, Login proximo) {
        this.identificador = identificador;
        this.proximo = proximo;
    }

    public Login() {
    }

    public Integer getLogin() {
        return identificador;
    }

    public void setLogin(Integer identificador) {
        this.identificador = identificador;
    }

    public Login getProximo() {
        return proximo;
    }

    public void setProximo(Login proximo) {
        this.proximo = proximo;
    }

    public void realizarLogin(Integer identificador, HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException, ServletException {
        if (this.identificador.equals(identificador)) {
            direcionarPagina(request, response, auxRealizarLogin(request, response));
        } else if (proximo != null) {
            proximo.realizarLogin(identificador, request, response);
        }
    }

    private Pessoa auxRealizarLogin(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, IOException{
        try {
            String email = request.getParameter("email");
            String senha = request.getParameter("password");
            if (email.equals("") || senha.equals("")) {
                return null;
            } else {
                Pessoa p = new Pessoa();
                p = p.setEmail(email).setSenha(senha);
                PessoaDAO.getInstance().Autentica(p);
                return p;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public abstract void direcionarPagina(HttpServletRequest request, HttpServletResponse response, Pessoa pessoa) throws SQLException, ClassNotFoundException, ServletException, IOException;

}
