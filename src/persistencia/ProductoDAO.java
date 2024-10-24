package persistencia;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import entidades.Producto;

public class ProductoDAO extends DAO{
    public void eliminarProducto(int codigo) throws Exception {
        try {
            String sql = "DELETE FROM Producto WHERE id_producto = " + codigo + "";
            insertarModificarEliminarDataBase(sql);
            System.out.println("El registro fue eliminado de manera exitosa");
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarDataBase();
        }
    }

    public void modificarProducto (int idProducto, String codigoProducto, String nombre, int idGama, String dimensiones, String proveedor, String descripcion, short cantidadEnStock, double precioVenta, double precioProveedor) throws Exception {
        try {
            String sql = "UPDATE producto SET " +
                        "codigo_producto = ?, " +   
                        "nombre = ?, " +
                        "id_gama = ?, " +
                        "dimensiones = ?, " +
                        "proveedor = ?, " +
                        "descripcion = ?, " +
                        "cantidad_en_stock = ?, " +
                        "precio_venta = ?, " +
                        "precio_proveedor = ? " +
                        "WHERE id_producto = ?";
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.setString(1, codigoProducto);
            preparedStatement.setString(2, nombre);
            preparedStatement.setInt(3, idGama);
            preparedStatement.setString(4, dimensiones);
            preparedStatement.setString(5, proveedor);
            preparedStatement.setString(6, descripcion);
            preparedStatement.setShort(7, cantidadEnStock);
            preparedStatement.setDouble(8, precioVenta);
            preparedStatement.setDouble(9, precioProveedor);
            preparedStatement.setInt(10, idProducto);
            int filasAfectadas = preparedStatement.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Producto modificado con éxito.");
            } else {
                System.out.println("No se encontró el producto con ID: " + idProducto);
            }   
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        } finally {
            desconectarDataBase();
        }
    }

    public Producto listarProductosConMenorStock() throws Exception {
        try {
            String sql = "SELECT * FROM producto WHERE cantidad_en_stock = (SELECT MIN(cantidad_en_stock) FROM producto)";
            consultarDataBase(sql);
            Producto producto = null;
            while (resultSet.next()) {
                producto = new Producto();
                producto.setIdProducto(resultSet.getInt("id_producto"));
                producto.setCodigoProducto(resultSet.getString("codigo_producto"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setIdGama(resultSet.getInt("id_gama"));
                producto.setDimensiones(resultSet.getString("dimensiones"));
                producto.setProveedor(resultSet.getString("proveedor"));
                producto.setDescripcion(resultSet.getString("descripcion"));
                producto.setCantidadEnStock(resultSet.getShort("cantidad_en_stock"));
                producto.setPrecioVenta(resultSet.getDouble("precio_venta"));
                producto.setPrecioProveedor(resultSet.getDouble("precio_proveedor"));
            }
            return producto;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            desconectarDataBase();
        }
    }

    public Producto listarProductosConMenorPrecioVenta() throws Exception {
        try {
            String sql = "SELECT * FROM producto WHERE precio_venta = (SELECT MIN(precio_venta) FROM producto)";
            consultarDataBase(sql);
            Producto producto = null;
            while (resultSet.next()) {
                producto = new Producto();
                producto.setIdProducto(resultSet.getInt("id_producto"));
                producto.setCodigoProducto(resultSet.getString("codigo_producto"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setIdGama(resultSet.getInt("id_gama"));
                producto.setDimensiones(resultSet.getString("dimensiones"));
                producto.setProveedor(resultSet.getString("proveedor"));
                producto.setDescripcion(resultSet.getString("descripcion"));
                producto.setCantidadEnStock(resultSet.getShort("cantidad_en_stock"));
                producto.setPrecioVenta(resultSet.getDouble("precio_venta"));
                producto.setPrecioProveedor(resultSet.getDouble("precio_proveedor"));
            }
            return producto;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            desconectarDataBase();
        }
    }
}
