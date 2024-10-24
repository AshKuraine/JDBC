import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import entidades.Cliente;
import entidades.DetallePedido;
import entidades.Pedido;
import servicios.ClienteServicio;
import servicios.PedidoServicio;

public class Menu {

    private ClienteServicio clienteServicio;
    private PedidoServicio pedidoServicio;
    private Scanner scanner;

    public Menu() {
        this.clienteServicio = new ClienteServicio();
        this.pedidoServicio = new PedidoServicio();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("========== MENÚ PRINCIPAL ==========");
            System.out.println("1. Crear nuevo cliente");
            System.out.println("2. Listar todos los clientes");
            System.out.println("3. Buscar cliente por código");
            System.out.println("4. Crear nuevo pedido");
            System.out.println("5. Listar pedidos por estado");
            System.out.println("6. Listar pedidos por producto");
            System.out.println("7. Listar productos con menor stock por cliente");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
            ejecutarOpcion(opcion);
        } while (opcion != 0);
    }

    private void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1:
                    crearNuevoCliente();
                    break;
                case 2:
                    listarTodosLosClientes();
                    break;
                case 3:
                    buscarClientePorCodigo();
                    break;
                case 4:
                    crearNuevoPedido();
                    break;
                case 5:
                    listarPedidosPorEstado();
                    break;
                case 6:
                    listarPedidosPorProducto();
                    break;
                case 7:
                    listarProductosConMenorStockPorCliente();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearNuevoCliente() throws Exception {
        System.out.print("Ingrese el código del cliente: ");
        int codigoC = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nombre del contacto: ");
        String nombreContacto = scanner.nextLine();
        System.out.print("Apellido del contacto: ");
        String apellidoContacto = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Fax: ");
        String fax = scanner.nextLine();
        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine();
        System.out.print("Región: ");
        String region = scanner.nextLine();
        System.out.print("País: ");
        String pais = scanner.nextLine();
        System.out.print("Código postal: ");
        String codigoPostal = scanner.nextLine();
        System.out.print("ID del empleado: ");
        int idEmpleado = scanner.nextInt();
        System.out.print("Límite de crédito: ");
        double limiteCredito = scanner.nextDouble();

        clienteServicio.crearNuevoCliente(codigoC, nombre, nombreContacto, apellidoContacto, telefono, fax, ciudad, region, pais, codigoPostal, idEmpleado, limiteCredito);
        System.out.println("Cliente creado exitosamente.");
    }

    private void listarTodosLosClientes() throws Exception {
        List<Cliente> clientes = clienteServicio.listarTodosLosClientes();
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    private void buscarClientePorCodigo() throws Exception {
        System.out.print("Ingrese el código del cliente: ");
        int codigoC = scanner.nextInt();
        Cliente cliente = clienteServicio.buscarClientePorCodigo(codigoC);
        if (cliente != null) {
            System.out.println(cliente);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void crearNuevoPedido() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el código del pedido: ");
        int codigoPedido = scanner.nextInt();
        System.out.print("Ingrese el ID del cliente: ");
        int idCliente = scanner.nextInt();
        System.out.print("Ingrese la fecha del pedido (YYYY-MM-DD): ");
        String fechaPedidoStr = scanner.next(); // Puedes usar Date.valueOf para convertir a Date
        System.out.print("Ingrese la fecha esperada (YYYY-MM-DD): ");
        String fechaEsperadaStr = scanner.next();
        System.out.print("Ingrese la fecha de entrega (YYYY-MM-DD): ");
        String fechaEntregaStr = scanner.next(); 
        System.out.print("Ingrese el estado del pedido: ");
        String estado = scanner.next();
        System.out.print("Ingrese comentarios: ");
        String comentarios = scanner.next();
        List<DetallePedido> detalles = new ArrayList<>();
        boolean continuarAgregandoDetalles = true;
        while (continuarAgregandoDetalles) {
            System.out.print("Ingrese el ID del producto: ");
            int idProducto = scanner.nextInt();
            
            System.out.print("Ingrese la cantidad: ");
            int cantidad = scanner.nextInt();
            
            System.out.print("Ingrese el precio por unidad: ");
            double precioUnidad = scanner.nextDouble();
            
            System.out.print("Ingrese el número de línea: ");
            short numeroLinea = scanner.nextShort();

            DetallePedido detalle = new DetallePedido(idProducto, cantidad, cantidad, precioUnidad, numeroLinea);
            detalles.add(detalle);

            System.out.print("¿Desea agregar otro detalle? (true/false): ");
            continuarAgregandoDetalles = scanner.nextBoolean();
        }

        try {
            Pedido nuevoPedido = pedidoServicio.crearNuevoPedido(
                codigoPedido,
                idCliente,
                Date.valueOf(fechaPedidoStr),
                Date.valueOf(fechaEsperadaStr),
                Date.valueOf(fechaEntregaStr),
                estado,
                comentarios,
                detalles
            );
            System.out.println("Pedido creado exitosamente con ID: " + nuevoPedido.getIdPedido());
        } catch (Exception e) {
            System.err.println("Error al crear el pedido: " + e.getMessage());
        }
    }

    private void listarPedidosPorEstado() throws Exception {
        System.out.print("Ingrese el estado del pedido: ");
        String estado = scanner.nextLine();
        Pedido pedido = pedidoServicio.listarPedidosPorEstado(estado);
        System.out.println(pedido);
    }

    private void listarPedidosPorProducto() throws Exception {
        System.out.print("Ingrese el ID del producto: ");
        int idProducto = scanner.nextInt();
        List<Pedido> pedidos = pedidoServicio.listarPedidosPorProducto(idProducto);
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
    }

    private void listarProductosConMenorStockPorCliente() throws Exception {
        System.out.print("Ingrese el ID del cliente: ");
        int idCliente = scanner.nextInt();
        List<Pedido> pedidos = pedidoServicio.listarProductosConMenorStock(idCliente);
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.mostrarMenu();
    }
}