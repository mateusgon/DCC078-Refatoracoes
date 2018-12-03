package action;

import controller.Action;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Pessoa;
import persistence.PessoaDAO;

public class CadastrarSuperUsuarioPostAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String nome = request.getParameter("nome");
        String endereco = request.getParameter("endereco");
        Integer restauranteCod = Integer.parseInt(request.getParameter("idRestaurante"));
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (nome.equals("") || email.equals("") || restauranteCod.equals("") || telefone.equals("") || email.equals("") || senha.equals("")) {
            response.sendRedirect("erro.jsp");
        } else {
            Pessoa pessoa = new Pessoa();
            pessoa.setRestauranteCod(restauranteCod).setNome(nome).setEndereco(endereco).setEmail(email).setTelefone(telefone).setTipoPessoa(7).setSenha(senha);
            try {
                PessoaDAO.getInstance().saveSuperUsuarioeFuncionario(pessoa);
                RequestDispatcher dispatcher = request.getRequestDispatcher("acesso-restrito-superusuario.jsp");
                dispatcher.forward(request, response);
            } catch (SQLException ex) {
                response.sendRedirect("erro.jsp");
            }
        }
    }

}
