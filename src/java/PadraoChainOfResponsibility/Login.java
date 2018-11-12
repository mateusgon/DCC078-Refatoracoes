package PadraoChainOfResponsibility;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    public abstract void direcionarPagina(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException;

    public void realizarLogin(Integer identificador, HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        if (this.identificador.equals(identificador)) {
            direcionarPagina(request, response);
        } else if (proximo != null) {
            proximo.realizarLogin(identificador, request, response);
        }
    }
}
