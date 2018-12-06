package action;

import PadraoChainOfResponsibility.LoginFuncionario;
import PadraoStateObserverMemento.Cliente;
import PadraoStateObserverMemento.Pedido;
import controller.Action;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.AttributeList;
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

    public Boolean mudaEstado(String estado, HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, String> map = new HashMap<>();
        map.put("Aberto", "abrir");
        map.put("Preparar", "preparar");
        map.put("Pronto", "pronto");
        map.put("Enviar", "enviar");
        map.put("Receber", "receber");

        Class classe = Class.forName("PadraoStateObserverMemento.Pedido");
        Object objeto = pedido;
        Method metodo = classe.getMethod(map.get(estado));
        
        if ((Boolean) metodo.invoke(objeto)) {
            PedidoDAO.getInstance().updatePedidoEstado(pedido);
            return true;
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
