package PadraoChainOfResponsibility;

import EscalonamentoDePedido.ChefeEasy;
import EscalonamentoDePedido.ChefeHard;
import EscalonamentoDePedido.ChefeMedium;
import EscalonamentoDePedido.Funcionario;
import PadraoStateObserverMemento.Pedido;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pessoa;
import persistence.PedidoDAO;
import persistence.PessoaDAO;

public class LoginFuncionario extends Login {

    public LoginFuncionario() {
    }

    public LoginFuncionario(Integer identificador, Login proximo) {
        super(identificador, proximo);
        this.identificador = 2;
    }

    @Override
    public void direcionarPagina(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
        try {
            String email = request.getParameter("email");
            String senha = request.getParameter("password");
            boolean flagAutenticado = false;
            if (email.equals("") || senha.equals("")) {
                response.sendRedirect("erro.jsp"); //tratar erro1
            } else {
                Pessoa p = new Pessoa();
                p = p.setEmail(email).setSenha(senha);
                PessoaDAO.getInstance().Autentica(p);
                request.setAttribute("pessoa", p);
//                HttpSession session = request.getSession();
//                session.setAttribute("authUser", pessoa.getNome());
//                session.setAttribute("idUser", pessoa.getPessoaCod());
                if (p.getTipoPessoa() == 2) {
                    Integer idRestaurante = p.getRestauranteCod();
                    request.setAttribute("pedidos", PedidoDAO.getInstance().searchPedidoRestaurante(idRestaurante));
                    RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-atendente.jsp");
                    dispatcher.forward(request, response);
                } else if (p.getTipoPessoa() >= 3 && p.getTipoPessoa() <= 5) {
                    Integer idRestaurante = p.getRestauranteCod();
                    Integer idUsuario = p.getPessoaCod();

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

                    request.setAttribute("idChefe", funcionari.getPessoaCod());
                    request.setAttribute("pedidos", pedidosPegar);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-chefe.jsp");
                    dispatcher.forward(request, response);

                } else if (p.getTipoPessoa() == 6) {
                    Integer idRestaurante = p.getRestauranteCod();
                    List<Pedido> pedidos = PedidoDAO.getInstance().searchPedidoRestaurante(idRestaurante);
                    List<Pedido> pedidosLista = new ArrayList<>();
                    for (Iterator i = pedidos.iterator(); i.hasNext();) {
                        Pedido pedido = (Pedido) i.next();
                        if (pedido.getNomeEstado().equals("Enviado") || pedido.getNomeEstado().equals("Pronto")) {
                            pedidosLista.add(pedido);
                        }

                    }

                    request.setAttribute("motoboyCod", p.getPessoaCod());
                    request.setAttribute("pedidos", pedidosLista);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-motoqueiro.jsp");
                    dispatcher.forward(request, response);
                } else {
                    RequestDispatcher dispacher = request.getRequestDispatcher("form-login-funcionarios.jsp");
                    dispacher.forward(request, response);
                }
            }
        } catch (Exception ex) {
            try {
                RequestDispatcher dispacher = request.getRequestDispatcher("form-login-funcionarios.jsp");
                dispacher.forward(request, response);
            } catch (ServletException ex1) {
                Logger.getLogger(LoginFuncionario.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(LoginFuncionario.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
}


