package action;

import PadraoChainOfResponsibility.LoginFuncionario;
import PadraoStateObserverMemento.Cliente;
import PadraoStateObserverMemento.Pedido;
import controller.Action;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pessoa;
import persistence.PedidoDAO;
import persistence.PessoaDAO;

public class MudarEstadoPostAction implements Action {

    Integer idPedido;
    Integer idChefe;
    Pedido pedido;
    Pessoa p;
    Cliente cliente;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        idPedido = Integer.parseInt(request.getParameter("idPed"));
        idChefe = Integer.parseInt(request.getParameter("idChefe"));
        String estado = request.getParameter("estado");

        pedido = PedidoDAO.getInstance().searchPedidoNumPedido(idPedido);

        if (pedido.getNomeEstado().equals(estado)) {
            redirecionaErro(request, response);
        }

        if (mudaEstado(estado, request, response)) {
            p = PessoaDAO.getInstance().buscaUsuario(pedido.getIdCliente());
            cliente = new Cliente(p.getPessoaCod(), p.getTipoPessoa(), p.getNome(), p.getEndereco(), p.getEmail(), null, p.getTelefone(), pedido);
            Pessoa funci = PessoaDAO.getInstance().buscaUsuario(idChefe);
            pedido.saveToMemento();
            LoginFuncionario login = new LoginFuncionario();
            login.direcionarPagina(request, response, funci);
        }
    }

    public Boolean mudaEstado(String estado, HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        if (estado.equals("Aberto")) {
            if (pedido.abrir()) {
                PedidoDAO.getInstance().updatePedidoEstado(pedido);
                return true;
            } else {
                redirecionaErro(request, response);
            }
        } else if (estado.equals("Preparar")) {
            if (pedido.preparar()) {
                PedidoDAO.getInstance().updatePedidoEstado(pedido);
                return true;
            } else {
                redirecionaErro(request, response);
            }
        } else if (estado.equals("Enviar")) {
            if (pedido.pronto()) {
                PedidoDAO.getInstance().updatePedidoEstado(pedido);
                return true;
            } else {
                redirecionaErro(request, response);
            }
        } else if (estado.equals("Receber")) {
            if (pedido.receber()) {
                PedidoDAO.getInstance().updatePedidoEstado(pedido);
                return true;
            } else {
                redirecionaErro(request, response);
            }
        } else {
            redirecionaErro(request, response);
        }
        return false;
    }

    public void redirecionaErro(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
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
        dispatcher.forward(request, response);
    }
}
