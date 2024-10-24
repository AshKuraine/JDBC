package entidades;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

public class Pedido {
    private int idPedido;
    private int codigoPedido;
    private Date fechaPedido;
    private Date fechaEsperada;
    private Date fechaEntrega;
    private String estado;
    private String comentarios;
    private int idCliente;

    private List<DetallePedido> detalles;

    public Pedido(int idPedido, int codigoPedido, Date fechaPedido, Date fechaEsperada, Date fechaEntrega,
            String estado, String comentarios, int idCliente) {
        this.idPedido = idPedido;
        this.codigoPedido = codigoPedido;
        this.fechaPedido = fechaPedido;
        this.fechaEsperada = fechaEsperada;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.comentarios = comentarios;
        this.idCliente = idCliente;
        this.detalles = new ArrayList<>();
    }

    public Pedido(int codigoPedido, Date fechaPedido, Date fechaEsperada, Date fechaEntrega, String estado,
            String comentarios, int idCliente) {
        this.codigoPedido = codigoPedido;
        this.fechaPedido = fechaPedido;
        this.fechaEsperada = fechaEsperada;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
        this.comentarios = comentarios;
        this.idCliente = idCliente;
        this.detalles = new ArrayList<>();
    }

    public Pedido() {
        this.detalles = new ArrayList<>();
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(int codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Date getFechaEsperada() {
        return fechaEsperada;
    }

    public void setFechaEsperada(Date fechaEsperada) {
        this.fechaEsperada = fechaEsperada;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public void agregarDetalle(DetallePedido detalle) {
        if (this.detalles == null) {
            this.detalles = new ArrayList<>();
        }
        this.detalles.add(detalle);
    }
}
