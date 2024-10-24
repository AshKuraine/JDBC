package persistencia;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import entidades.DetallePedido;
import entidades.Pedido;

public class PedidoDAO extends DAO{

    public Pedido crearPedido(Pedido pedido) throws Exception {
        try {
            if (pedido == null) {
                throw new Exception("El pedido no puede ser nulo.");
            }
            connectarDataBase();
            conexion.setAutoCommit(false);
            String sql = "INSERT INTO pedidos (codigo_pedido, fecha_pedido, fecha_esperada, fecha_entrega, estado, comentarios, id_cliente) "
                             + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psPedido = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            psPedido.setInt(1, pedido.getCodigoPedido());
            psPedido.setDate(2, pedido.getFechaPedido());
            psPedido.setDate(3, pedido.getFechaEsperada());
            psPedido.setDate(4, pedido.getFechaEntrega());
            psPedido.setString(5, pedido.getEstado());
            psPedido.setString(6, pedido.getComentarios());
            psPedido.setInt(7, pedido.getIdCliente());
            psPedido.executeUpdate();
            var rs = psPedido.getGeneratedKeys();
            int idPedidoGenerado = 0;
            if (rs.next()) {
                idPedidoGenerado = rs.getInt(1);
            }
            pedido.setIdPedido(idPedidoGenerado);
            String sqlDetalle = "INSERT INTO detalles_pedido (id_pedido, id_producto, cantidad, precio_unidad, numero_linea) "
                              + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psDetalle = conexion.prepareStatement(sqlDetalle);
            for (DetallePedido detalle : pedido.getDetalles()) {
                psDetalle.setInt(1, idPedidoGenerado);  // Usamos el id del pedido generado
                psDetalle.setInt(2, detalle.getIdProducto());
                psDetalle.setInt(3, detalle.getCantidad());
                psDetalle.setDouble(4, detalle.getPrecioUnidad());
                psDetalle.setShort(5, detalle.getNumero_linea());

                // Ejecutamos la inserción del detalle
                psDetalle.executeUpdate();
            }
            conexion.commit();
            return pedido;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarDataBase();
        }
    }
    
    public List<Pedido> listarProductosConMenorStock(int idCliente) throws Exception {
        try {
            String sql = "SELECT p.id_pedido, p.fecha_pedido, p.estado, p.fecha_esperada, p.id_cliente, " +
                        "dp.id_producto, dp.cantidad, dp.precio_unidad " +
                        "FROM pedido p " +
                        "JOIN detalle_pedido dp ON p.id_pedido = dp.id_pedido " +
                        "WHERE p.id_cliente = " + idCliente;
            consultarDataBase(sql);
            List<Pedido> listaPedidos = new ArrayList<>();
            while (resultSet.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(resultSet.getInt("id_pedido"));
                pedido.setFechaPedido(resultSet.getDate("fecha_pedido"));
                pedido.setEstado(resultSet.getString("estado"));
                pedido.setFechaEsperada(resultSet.getDate("fecha_esperada"));

                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setIdProducto(resultSet.getInt("id_producto"));
                detallePedido.setCantidad(resultSet.getInt("cantidad"));
                detallePedido.setPrecioUnidad(resultSet.getDouble("precio_unidad"));

                pedido.agregarDetalle(detallePedido);

                listaPedidos.add(pedido);
            }
            return listaPedidos;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            desconectarDataBase();
        }
    }

    public Pedido listarPedidosPorEstado(String estado) throws Exception {
        try {
            String sql = "SELECT * FROM pedido WHERE estado = " + estado;
            consultarDataBase(sql);
            Pedido pedido = null;
            while (resultSet.next()) {
                pedido = new Pedido();
                pedido.setIdPedido(resultSet.getInt("id_pedido"));
                pedido.setCodigoPedido(resultSet.getInt("codigo_pedido"));
                pedido.setFechaPedido(resultSet.getDate("fecha_pedido"));
                pedido.setFechaEsperada(resultSet.getDate("fecha_esperada"));
                pedido.setFechaEntrega(resultSet.getDate("fecha_entrega"));
                pedido.setEstado(resultSet.getString("estado"));
                pedido.setComentarios(resultSet.getString("comentarios"));
                pedido.setIdCliente(resultSet.getInt("id_cliente"));
            }
            return pedido;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            desconectarDataBase();
        }
    }

    public List<Pedido> listarPedidosPorProducto(int idProducto) throws Exception {
        try {
            String sql = "SELECT p.id_pedido, p.fecha_pedido, p.estado, p.fecha_esperada, p.id_cliente, " +
                         "dp.id_producto, dp.cantidad, dp.precio_unidad " +
                         "FROM pedido p " +
                         "JOIN detalle_pedido dp ON p.id_pedido = dp.id_pedido " +
                         "WHERE dp.id_producto = " + idProducto;
    
            consultarDataBase(sql);
            List<Pedido> listaPedidos = new ArrayList<>();
            
            while (resultSet.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(resultSet.getInt("id_pedido"));
                pedido.setFechaPedido(resultSet.getDate("fecha_pedido"));
                pedido.setEstado(resultSet.getString("estado"));
                pedido.setFechaEsperada(resultSet.getDate("fecha_esperada"));
                pedido.setIdCliente(resultSet.getInt("id_cliente")); // Asegúrate de tener el setter
    
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setIdProducto(resultSet.getInt("id_producto"));
                detallePedido.setCantidad(resultSet.getInt("cantidad"));
                detallePedido.setPrecioUnidad(resultSet.getDouble("precio_unidad"));
    
                pedido.agregarDetalle(detallePedido);
    
                listaPedidos.add(pedido);
            }
            
            return listaPedidos;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            desconectarDataBase();
        }
    }    
}
