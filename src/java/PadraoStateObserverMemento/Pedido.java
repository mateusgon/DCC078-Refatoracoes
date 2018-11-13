package PadraoStateObserverMemento;

import EscalonamentoDePedido.TipoPedido;
import PadraoComposite.ItemDeVenda;
import PadraoStrategy.MetodoPagamento;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import persistence.PedidoMementoDAO;

public class Pedido extends Observable {

    private Integer numeroPedido;
    private List<ItemDeVenda> itens;
    private PedidoEstado estado;
    private Double valor;
    private Date dataPedido;
    private Integer dificuldade;
    private Integer idRestaurante;
    private Integer idCliente;
    private String nomeEstado;
    private Cliente cliente;
    private TipoPedido tipoPedido;
    private Boolean notificado;

    public Pedido() {
        this.itens = new ArrayList<>();
        this.estado = null;
    }

    public Boolean abrir() {
        return estado.aberto(this);
    }

    public Boolean preparar() {
        return estado.preparando(this);
    }

    public Boolean pronto() {
        return estado.pronto(this);
    }

    public Boolean enviar() {
        return estado.enviado(this);
    }

    public Boolean receber() {
        return estado.recebido(this);
    }

    public void notificar() {
        this.setChanged();
        notifyObservers();
    }

    public PedidoEstado getEstado() {
        return estado;
    }

    public Pedido setEstado(PedidoEstado estado) {
        if (this.estado == null) {
            this.estado = estado;
        } else {
            this.estado = estado;
            setChanged();
            notifyObservers();
        }
        return this;
    }

    public Integer getNumeroPedido() {
        return numeroPedido;
    }

    public Pedido setNumeroPedido(Integer numeroPedido) {
        this.numeroPedido = numeroPedido;
        return this;
    }

    public List<ItemDeVenda> getItens() {
        return itens;
    }

    public Pedido setItens(List<ItemDeVenda> itens) {
        this.itens = itens;
        return this;
    }

    public Double getValor() {
        return valor;
    }

    public Pedido setValor(Double valor) {
        this.valor = valor;
        return this;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public Pedido setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
        return this;
    }

    public Integer getDificuldade() {
        return dificuldade;
    }

    public Pedido setDificuldade(Integer dificuldade) {
        this.dificuldade = dificuldade;
        return this;
    }

    public Integer getIdRestaurante() {
        return idRestaurante;
    }

    public Pedido setIdRestaurante(Integer idRestaurante) {
        this.idRestaurante = idRestaurante;
        return this;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public Pedido setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
        return this;
    }

    public String getNomeEstado() {
        return this.estado.getNomeEstado();
    }

    public Pedido setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
        return this;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pedido setCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public Pedido setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
        return this;
    }

    public void saveToMemento() throws ClassNotFoundException, SQLException {
        PedidoMemento pm = new PedidoMemento();
        pm = pm.setNumeroPedido(this.numeroPedido).setEstado(this.getEstado());
        PedidoMementoDAO.getInstance().saveMemento(pm);
    }

    public void restoreFromMemento(PedidoMemento pedido) {
        this.estado = pedido.getEstado();
    }

    public Boolean getNotificado() {
        return notificado;
    }

    public Pedido setNotificado(Boolean notificado) {
        this.notificado = notificado;
        return this;
    }

}
