package persistencia;

import java.util.ArrayList;
import java.util.List;

import entidades.Cliente;

public class ClienteDAO extends DAO{
    public void guardarCliente(Cliente cliente) throws Exception {
        try {
            if (cliente == null) {
                throw new Exception("El cliente no puede ser nulo.");
            }
            String sql = "INSERT INTO Cliente (codigo_cliente, nombre_cliente, nombre_contacto, " +
                        "apellido_contacto, telefono, fax, ciudad, region, pais, codigo_postal, " +
                        "id_empleado, limite_credito) VALUES (" +
                        cliente.getCodigoCliente() + ", '" +
                        cliente.getNombreCliente() + "', '" +
                        cliente.getNombreContacto() + "', '" +
                        cliente.getApellidoContacto() + "', '" +
                        cliente.getTelefono() + "', '" +
                        cliente.getFax() + "', '" +
                        cliente.getCiudad() + "', '" +
                        cliente.getRegion() + "', '" +
                        cliente.getPais() + "', '" +
                        cliente.getCodigoPostal() + "', " +
                        cliente.getIdEmpleado() + ", " +
                        cliente.getLimiteCredito() + ");";
            insertarModificarEliminarDataBase(sql);
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarDataBase();
        }
    }

    public List<Cliente> listarTodosLosClientes() throws Exception {
        try {
            List<Cliente> clientes = new ArrayList<>();
            String sql = "SELECT id_cliente,nombre_contacto, apellido_contacto FROM cliente ";
            consultarDataBase(sql);
            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(resultSet.getInt("id_cliente"));
                cliente.setNombreContacto(resultSet.getString("nombre_contacto"));
                cliente.setApellidoContacto(resultSet.getString("apellido_contacto"));
                clientes.add(cliente);
            }
            for (Cliente clienteUnitario : clientes) {
                System.out.println(clienteUnitario.imprimirNyA());
            }
            return clientes;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            desconectarDataBase();
        }
    }

    public Cliente buscarClientePorCodigo(int codigo) throws Exception {
        try {
            String sql = "SELECT * FROM cliente WHERE codigo_cliente = " + codigo;
            consultarDataBase(sql);
            Cliente cliente = null;
            while (resultSet.next()) {
                cliente = new Cliente();
                cliente.setIdCliente(resultSet.getInt("id_cliente"));
                cliente.setCodigoCliente(resultSet.getInt("codigo_cliente"));
                cliente.setNombreCliente(resultSet.getString("nombre_cliente"));
                cliente.setNombreContacto(resultSet.getString("nombre_contacto"));
                cliente.setApellidoContacto(resultSet.getString("apellido_contacto"));
                cliente.setTelefono(resultSet.getString("telefono"));
                cliente.setFax(resultSet.getString("fax"));
                cliente.setCiudad(resultSet.getString("ciudad"));
                cliente.setPais(resultSet.getString("pais"));
                cliente.setCodigoPostal(resultSet.getString("pais"));
                cliente.setRegion(resultSet.getString("region"));
                cliente.setIdEmpleado(resultSet.getInt("id_empleado"));
                cliente.setLimiteCredito(resultSet.getDouble("limite_credito"));
            }
            return cliente;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarDataBase();
        }
    }

    public void eliminarClientePorCodigo(int codigo) throws Exception {
        try {
            String sql = "DELETE FROM cliente WHERE id_cliente = " + codigo;
            insertarModificarEliminarDataBase(sql);
            System.out.println("El cliente fue eliminado de manera exitosa");
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarDataBase();
        }
    }

    public Cliente listarClientesPorEmpleado(int codigo) throws Exception {
        try {
            String sql = "SELECT * FROM cliente WHERE id_empleado = " + codigo;
            consultarDataBase(sql);
            Cliente cliente = null;
            while (resultSet.next()) {
                cliente = new Cliente();
                cliente.setIdCliente(resultSet.getInt("id_cliente"));
                cliente.setCodigoCliente(resultSet.getInt("codigo_cliente"));
                cliente.setNombreCliente(resultSet.getString("nombre_cliente"));
                cliente.setNombreContacto(resultSet.getString("nombre_contacto"));
                cliente.setApellidoContacto(resultSet.getString("apellido_contacto"));
                cliente.setTelefono(resultSet.getString("telefono"));
                cliente.setFax(resultSet.getString("fax"));
                cliente.setCiudad(resultSet.getString("ciudad"));
                cliente.setPais(resultSet.getString("pais"));
                cliente.setCodigoPostal(resultSet.getString("pais"));
                cliente.setRegion(resultSet.getString("region"));
                cliente.setIdEmpleado(resultSet.getInt("id_empleado"));
                cliente.setLimiteCredito(resultSet.getDouble("limite_credito"));
            }
            return cliente;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarDataBase();
        }
    }

    public void incrementarLimiteCredito() throws Exception {
        try {
            String sql = "UPDATE cliente SET limite_credito = limite_credito * 1.15";
            insertarModificarEliminarDataBase(sql);
            System.out.println("El límite de crédito de todos los clientes ha sido incrementado en un 15%.");
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarDataBase();
        }
    }
}
