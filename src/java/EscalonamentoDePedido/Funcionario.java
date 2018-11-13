package EscalonamentoDePedido;

import PadraoStateObserverMemento.Pedido;
import java.util.ArrayList;
import java.util.List;

public abstract class Funcionario {

    private Integer pessoaCod, restauranteCod;
    private String nome, endereco, email, telefone;
    protected List listaPedidos = new ArrayList();
    private List<Funcionario> funcionarioSuperior = new ArrayList();

    public Funcionario(Integer pessoaCod, Integer restauranteCod, String nome, String endereco, String email, String telefone) {
        this.pessoaCod = pessoaCod;
        this.restauranteCod = restauranteCod;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
    }

    public List getListaPedidos() {
        return listaPedidos;
    }

    public Funcionario setListaPedidos(List listaPedidos) {
        this.listaPedidos = listaPedidos;
        return this;
    }

    public List<Funcionario> getFuncionarioSuperior() {
        return funcionarioSuperior;
    }

    public Funcionario setFuncionarioSuperior(List<Funcionario> funcionarioSuperior) {
        this.funcionarioSuperior = funcionarioSuperior;
        return this;
    }

    public Integer getPessoaCod() {
        return pessoaCod;
    }

    public Funcionario setPessoaCod(Integer pessoaCod) {
        this.pessoaCod = pessoaCod;
        return this;
    }

    public Integer getRestauranteCod() {
        return restauranteCod;
    }

    public Funcionario setRestauranteCod(Integer restauranteCod) {
        this.restauranteCod = restauranteCod;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Funcionario setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEndereco() {
        return endereco;
    }

    public Funcionario setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Funcionario setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTelefone() {
        return telefone;
    }

    public Funcionario setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public Boolean pegarPedido(Pedido pedido) {
        Boolean contem = false;
        for (Object listaPedido : listaPedidos) {
            TipoPedido tp = (TipoPedido) listaPedido;
            if (pedido.getTipoPedido().getTipoPedido().equals(tp.getTipoPedido())) {
                contem = true;
                pedido.setNotificado(true);
                if (pedido.getNotificado().equals(false)) {
                    System.out.println("Existe um pedido para o funcionário " + getNome() + ". Número: " + pedido.getNumeroPedido());
                }
            }
        }
        if (contem) {
            return true;
        } else {
            if (funcionarioSuperior.size() > 0) {
                List<Funcionario> funcionarios = getFuncionarioSuperior();
                for (Funcionario funcionario : funcionarios) {
                    funcionario.pegarPedido(pedido);
                }
                return false;
            } else {
                return false;
            }
        }
    }
}
