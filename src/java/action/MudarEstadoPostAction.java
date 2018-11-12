package action;

import EscalonamentoDePedido.ChefeEasy;
import EscalonamentoDePedido.ChefeHard;
import EscalonamentoDePedido.ChefeMedium;
import EscalonamentoDePedido.Funcionario;
import PadraoStateObserverMemento.Cliente;
import PadraoStateObserverMemento.Pedido;
import controller.Action;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pessoa;
import persistence.PedidoDAO;
import persistence.PessoaDAO;

public class MudarEstadoPostAction implements Action {

    Pedido pedido;
    Pessoa p;
    Cliente cliente;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer idPedido = Integer.parseInt(request.getParameter("idPed"));
        Integer idChefe = Integer.parseInt(request.getParameter("idChefe"));
        String estado = request.getParameter("estado");
        pedido = PedidoDAO.getInstance().searchPedidoNumPedido(idPedido);
        if (pedido.getNomeEstado().equals(estado)) {
            // redirecionar
        }

        mudaEstado(estado);

        p = PessoaDAO.getInstance().buscaUsuario(pedido.getIdCliente());
        cliente = new Cliente(p.getPessoaCod(), p.getTipoPessoa(), p.getNome(), p.getEndereco(), p.getEmail(), null, p.getTelefone(), pedido);

        Pessoa funci = PessoaDAO.getInstance().buscaUsuario(idChefe);

        if (funci.getTipoPessoa() >= 3 && funci.getTipoPessoa() <= 5) {
            Integer idRestaurante = funci.getRestauranteCod();
            Integer idUsuario = funci.getPessoaCod();

            Funcionario funcionari = null;
            List<Funcionario> funcionariosEasy = new ArrayList<>();
            List<Funcionario> funcionariosMedium = new ArrayList<>();
            List<Funcionario> funcionariosHard = new ArrayList<>();
            List<Pessoa> pessoas = PessoaDAO.getInstance().buscaFuncionarioRestaurante(idRestaurante);

            for (Iterator i = pessoas.iterator(); i.hasNext();) {
                Pessoa pessoa = (Pessoa) i.next();
                Funcionario func;
                switch (pessoa.getTipoPessoa()) {
                    case 3: {
                        func = new ChefeEasy(pessoa.getPessoaCod(), pessoa.getRestauranteCod(), pessoa.getNome(), pessoa.getEndereco(), pessoa.getEmail(), pessoa.getTelefone());
                        funcionariosEasy.add(func);
                        break;
                    }
                    case 4: {
                        func = new ChefeMedium(pessoa.getPessoaCod(), pessoa.getRestauranteCod(), pessoa.getNome(), pessoa.getEndereco(), pessoa.getEmail(), pessoa.getTelefone());
                        funcionariosMedium.add(func);
                        break;
                    }
                    case 5: {
                        func = new ChefeHard(pessoa.getPessoaCod(), pessoa.getRestauranteCod(), pessoa.getNome(), pessoa.getEndereco(), pessoa.getEmail(), pessoa.getTelefone());
                        funcionariosHard.add(func);
                        break;
                    }
                    default: {
                        func = new ChefeHard(pessoa.getPessoaCod(), pessoa.getRestauranteCod(), pessoa.getNome(), pessoa.getEndereco(), pessoa.getEmail(), pessoa.getTelefone());
                    }
                }
                if (pessoa.getPessoaCod() == idUsuario) {
                    funcionari = func;
                }
            }

            for (Iterator i = funcionariosEasy.iterator(); i.hasNext();) {
                Funcionario funcionario = (Funcionario) i.next();
                for (Iterator i2 = funcionariosMedium.iterator(); i.hasNext();) {
                    Funcionario funcionario1 = (Funcionario) i2.next();
                    funcionario.getFuncionarioSuperior().add(funcionario1);
                }
                for (Iterator i3 = funcionariosHard.iterator(); i.hasNext();) {
                    Funcionario funcionario2 = (Funcionario) i3.next();
                    funcionario.getFuncionarioSuperior().add(funcionario2);
                }
            }

            for (Iterator i = funcionariosMedium.iterator(); i.hasNext();) {
                Funcionario funcionario = (Funcionario) i.next();
                for (Iterator i3 = funcionariosHard.iterator(); i.hasNext();) {
                    Funcionario funcionario2 = (Funcionario) i3.next();
                    funcionario.getFuncionarioSuperior().add(funcionario2);
                }
            }
            List<Pedido> pedidosPegar = new ArrayList<>();
            List<Pedido> pedidos = PedidoDAO.getInstance().searchPedidoRestaurante(idRestaurante);

            for (Iterator i = pedidos.iterator(); i.hasNext();) {
                Pedido pedido = (Pedido) i.next();
                if ((pedido.getNomeEstado().equals("Aberto") || pedido.getNomeEstado().equals("Preparando")) && funcionari.pegarPedido(pedido)) {
                    pedidosPegar.add(pedido);
                }

            }

            pedido.saveToMemento();

            request.setAttribute("idChefe", funci.getPessoaCod());
            request.setAttribute("pedidos", pedidosPegar);
            RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-chefe.jsp");
            dispatcher.forward(request, response);

        } else {
            Integer idRestaurante = funci.getRestauranteCod();
            List<Pedido> pedidos = PedidoDAO.getInstance().searchPedidoRestaurante(idRestaurante);
            List<Pedido> pedidosLista = new ArrayList<>();
            for (Iterator i = pedidos.iterator(); i.hasNext();) {
                Pedido pedido = (Pedido) i.next();
                if (pedido.getNomeEstado().equals("Enviado") || pedido.getNomeEstado().equals("Pronto")) {
                    pedidosLista.add(pedido);
                }

            }
            pedido.saveToMemento();
            request.setAttribute("motoboyCod", funci.getPessoaCod());
            request.setAttribute("pedidos", pedidosLista);
            RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-motoqueiro.jsp");
            dispatcher.forward(request, response);
        }
    }

    public void mudaEstado(String estado) throws SQLException, ClassNotFoundException {
        switch (estado) {
            case "Preparar":
                pedido.preparar();
                PedidoDAO.getInstance().updatePedido(pedido, 2);
                break;
            case "Pronto":
                pedido.pronto();
                PedidoDAO.getInstance().updatePedido(pedido, 3);
                break;
            case "Enviar":
                pedido.enviar();
                PedidoDAO.getInstance().updatePedido(pedido, 4);
                break;
            case "Receber":
                pedido.receber();
                PedidoDAO.getInstance().updatePedido(pedido, 5);
                break;
            default:
                break;
        }
    }
}
