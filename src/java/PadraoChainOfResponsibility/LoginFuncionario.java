package PadraoChainOfResponsibility;

import EscalonamentoDePedido.Funcionario;
import EscalonamentoDePedido.FuncionarioFactory;
import PadraoStateObserverMemento.Pedido;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pessoa;
import persistence.PedidoDAO;

public class LoginFuncionario extends Login {

    public LoginFuncionario() {
    }

    public LoginFuncionario(Integer identificador, Login proximo) {
        super(identificador, proximo);
        this.identificador = 2;
    }

    @Override
    public void direcionarPagina(HttpServletRequest request, HttpServletResponse response, Pessoa p) throws SQLException, ClassNotFoundException, ServletException, IOException {
        try {
            if (p.getTipoPessoa() == 2) {
                acessoAtendente(request, response, p);
            } else if (p.getTipoPessoa() >= 3 && p.getTipoPessoa() <= 5) {
                acessoFuncionario(request, response, p);
            } else if (p.getTipoPessoa() == 6) {
                acessoMotoboy(request, response, p);
            } else {
                RequestDispatcher dispacher = request.getRequestDispatcher("form-login-funcionarios.jsp");
                dispacher.forward(request, response);
            }
        } catch (Exception ex) {
            RequestDispatcher dispacher = request.getRequestDispatcher("form-login-funcionarios.jsp");
            dispacher.forward(request, response);
        }
    }

    private void acessoAtendente(HttpServletRequest request, HttpServletResponse response, Pessoa p) throws SQLException, ClassNotFoundException, ServletException, IOException {
        Integer idRestaurante = p.getRestauranteCod();
        request.setAttribute("pessoa", p);
        request.setAttribute("pedidos", PedidoDAO.getInstance().searchPedidoRestaurante(idRestaurante));
        RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-atendente.jsp");
        dispatcher.forward(request, response);
    }

    private void acessoFuncionario(HttpServletRequest request, HttpServletResponse response, Pessoa p) throws SQLException, ClassNotFoundException, ServletException, IOException {
        Integer idRestaurante = p.getRestauranteCod();
        Integer idUsuario = p.getPessoaCod();
        Funcionario funcionari = FuncionarioFactory.instanciaFuncionario(p.getTipoPessoa());
        //funcionari.setPessoaCod(idUsuario).setRestauranteCod(idRestaurante).setNome(p.getNome()).setEndereco(p.getEndereco()).setEmail(p.getEmail()).setTelefone(p.getTelefone());
        List<Pedido> pedidosPegar = new ArrayList<>();
        List<Pedido> pedidos = PedidoDAO.getInstance().searchPedidoRestaurante(idRestaurante);
        for (Iterator i = pedidos.iterator(); i.hasNext();) {
            Pedido pedido = (Pedido) i.next();
            if ((pedido.getNomeEstado().equals("Aberto") || pedido.getNomeEstado().equals("Preparando")) && p.getTipoPessoa() >= pedido.getTipoPedido().getIdentificador() + 2) {
                pedidosPegar.add(pedido);
            }
        }
        request.setAttribute("pessoa", p);
        request.setAttribute("idChefe", p.getPessoaCod());
        request.setAttribute("pedidos", pedidosPegar);
        RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-chefe.jsp");
        dispatcher.forward(request, response);
    }

    private void acessoMotoboy(HttpServletRequest request, HttpServletResponse response, Pessoa p) throws SQLException, ClassNotFoundException, ServletException, IOException {
        Integer idRestaurante = p.getRestauranteCod();
        List<Pedido> pedidos = PedidoDAO.getInstance().searchPedidoRestaurante(idRestaurante);
        List<Pedido> pedidosLista = new ArrayList<>();
        for (Iterator i = pedidos.iterator(); i.hasNext();) {
            Pedido pedido = (Pedido) i.next();
            if (pedido.getNomeEstado().equals("Enviado") || pedido.getNomeEstado().equals("Pronto")) {
                pedidosLista.add(pedido);
            }
        }
        request.setAttribute("pessoa", p);
        request.setAttribute("motoboyCod", p.getPessoaCod());
        request.setAttribute("pedidos", pedidosLista);
        RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-motoqueiro.jsp");
        dispatcher.forward(request, response);
    }
}
