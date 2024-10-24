package servicios;

import java.util.List;

import entidades.Cliente;
import persistencia.ClienteDAO;

public class ClienteServicio {
    private ClienteDAO cd;

    public  ClienteServicio() {
        this.cd = new ClienteDAO();
    }

    public Cliente crearNuevoCliente(int codigoC, String nombre, String nombreContacto, String apellidoContacto, String telefono,
                                     String fax, String ciudad, String region, String pais, String codigoPostal, int idEmpleado,
                                     double limiteCredito) throws Exception {
        validacionesNyA(nombreContacto, apellidoContacto);
        Cliente cliente = new Cliente(codigoC, nombre, nombreContacto, apellidoContacto, telefono, fax, ciudad, region, pais, codigoPostal, idEmpleado, limiteCredito);
        cd.guardarCliente(cliente);
        return cliente;
    }

    public void validacionesNyA(String nombreContacto, String apellidoContacto) throws Exception {
        if (nombreContacto == null) {
            throw new Exception("El nombre del contacto no puede ser nulo.");
        }
        if (apellidoContacto == null) {
            throw new Exception("El apellido del contacto no puede ser nulo.");
        }
    }

    public List<Cliente> listarTodosLosClientes() throws Exception {
        try {
            return cd.listarTodosLosClientes();
        } catch (Exception e) {
            throw new Exception("Error al listar los clientes: " + e.getMessage(), e);
        }
    }

    public Cliente buscarClientePorCodigo(int codigoC) throws Exception {
        try {
            return cd.buscarClientePorCodigo(codigoC);
        } catch (Exception e) {
            throw new Exception("Error al obtener cliente: " + e.getMessage(), e);
        }
    }
}
