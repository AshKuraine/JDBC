package servicios;

import java.util.List;

import entidades.DetallePedido;
import entidades.Pedido;
import persistencia.PedidoDAO;

public class PedidoServicio {
    private PedidoDAO pd;

    public PedidoServicio() {
        this.pd = new PedidoDAO();
    }

    public Pedido crearNuevoPedido(int codigoPedido, int idCliente, java.sql.Date fechaPedido, java.sql.Date fechaEsperada, 
                               java.sql.Date fechaEntrega, String estado, String comentarios, 
                               List<DetallePedido> detalles) throws Exception {
        // Validaciones de entrada
        validarPedido(fechaPedido, estado, idCliente, detalles);

        // Crear un objeto Pedido con los datos proporcionados
        Pedido pedido = new Pedido();
        pedido.setCodigoPedido(codigoPedido);
        pedido.setIdCliente(idCliente);
        pedido.setFechaPedido(fechaPedido);
        pedido.setFechaEsperada(fechaEsperada);
        pedido.setFechaEntrega(fechaEntrega);
        pedido.setEstado(estado);
        pedido.setComentarios(comentarios);
        pedido.setDetalles(detalles);

        // Llamar al DAO para guardar el pedido y sus detalles
        return pd.crearPedido(pedido);
    }


    // Validaciones para el pedido
    private void validarPedido(java.sql.Date fechaPedido, String estado, int idCliente, List<DetallePedido> detalles) throws Exception {
        if (fechaPedido == null) {
            throw new Exception("La fecha del pedido no puede ser nula.");
        }
        if (estado == null || estado.trim().isEmpty()) {
            throw new Exception("El estado del pedido no puede estar vacío.");
        }
        if (idCliente <= 0) {
            throw new Exception("El ID del cliente no es válido.");
        }
        if (detalles == null || detalles.isEmpty()) {
            throw new Exception("El pedido debe tener al menos un detalle.");
        }
    }

    public Pedido listarPedidosPorEstado(String estado) throws Exception {
        if (estado == null || estado.isEmpty()) {
            throw new Exception("El estado no puede estar vacío.");
        }
        return pd.listarPedidosPorEstado(estado);
    }

    // Listar pedidos que contienen un producto en particular
    public List<Pedido> listarPedidosPorProducto(int idProducto) throws Exception {
        if (idProducto <= 0) {
            throw new Exception("El ID del producto debe ser mayor que 0.");
        }
        return pd.listarPedidosPorProducto(idProducto);
    }

    // Listar pedidos de un cliente con productos de menor stock
    public List<Pedido> listarProductosConMenorStock(int idCliente) throws Exception {
        if (idCliente <= 0) {
            throw new Exception("El ID del cliente debe ser mayor que 0.");
        }
        return pd.listarProductosConMenorStock(idCliente);
    }
}
