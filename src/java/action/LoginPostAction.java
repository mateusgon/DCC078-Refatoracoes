package action;

import PadraoChainOfResponsibility.Login;
import PadraoChainOfResponsibility.LoginCliente;
import PadraoChainOfResponsibility.LoginFuncionario;
import PadraoChainOfResponsibility.LoginSuperUsuario;
import controller.Action;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginPostAction implements Action{

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer identificador = Integer.parseInt(request.getParameter("identificador"));
        Login loginCliente = new LoginCliente(1, null);
        Login loginFuncionario = new LoginFuncionario(2, null);
        Login loginSuperUsuario = new LoginSuperUsuario(3, null);
        loginCliente.setProximo(loginFuncionario);
        loginFuncionario.setProximo(loginSuperUsuario);
        loginSuperUsuario.setProximo(null);
        loginCliente.realizarLogin(identificador, request, response);
    }
    
}
