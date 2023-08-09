package libreria.servicios;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import libreria.entidades.Cliente;
import libreria.persistencia.ClienteDAO;

public class ClienteServicios {
    private Scanner leer = new Scanner(System.in);
    ClienteDAO cDAO = new ClienteDAO();
    
    public void menu() throws Exception {
        try{
        System.out.println();
        System.out.println(" Bienvenidos al menu de Cliente");
        System.out.println();
        System.out.println("Selecciones una opcion");
        System.out.println("1) Agregar un Cliente");
        System.out.println("2) Modificar un Cliente existente");
        System.out.println("3) Eliminar un Cliente");
        System.out.println("4) Consultar un Cliente");
        System.out.println("5) Consultar todos los Clientes");
        System.out.println("6) volver al menu principal");
        System.out.println();
        int op = leer.nextInt();
        leer.nextLine();
        opciones(op);
         } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese una opción válida (número entero).");
            
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public void opciones(int opcion) throws Exception {
        switch (opcion) {
            case 1:
                agregarCliente();
                menu();
                break;
            case 2:
                modificarCliente();
                menu();
                break;
            case 3:
                eliminarCliente();
                menu();
                break;
            case 4:
                consultarCliente();
                menu();
                break;
            case 5:consultarClientes();
                menu();
                break;
            case 6:
                break;
            default:
                System.out.println("Ingrese una opcion valida");
                menu();
        }
    }
    
    public void agregarCliente() throws Exception {
        Cliente cliente = new Cliente();
        System.out.println("Para ingresar un nuevo Cliente por favor indique lo siguiente");
        System.out.println();

        try {
            System.out.println("Ingrese el docuemento del cliente");
            if(!(leer.hasNextLong())){
                throw new IllegalArgumentException("El documento debe ser solo numero");
            }
            long documento = leer.nextLong();
            leer.nextLine();
            
            System.out.println("Ingrese el nombre del cliente");
            String nombre = leer.nextLine();
            if(nombre.isEmpty()){
                throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
            }
            
            System.out.println("Ingrese el apellido del cliente");
            String apellido = leer.nextLine();
            if(apellido.isEmpty()){
                throw new IllegalArgumentException("El apellido del cliente no puede estar vacío.");
            }
            
            System.out.println("Ingrese el telefono del cliente");
            String telefono = leer.nextLine();
            if(telefono.isEmpty()){
                throw new IllegalArgumentException("El telefono del cliente no puede estar vacío.");
            }
            
            cliente.setDocumento(documento);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);
            
            //Verifica si el documento ya existe en la base de datos
            List<Cliente> clientes = cDAO.listarTodos();
            boolean noRepetido= true;
            for (Cliente cliente1 : clientes) {
                if(cliente.compararDocumento(cliente1)){
                    noRepetido = false;
                }
            }
            
            if(noRepetido){
                cDAO.guardar(cliente);
                System.out.println("¡Cliente agregado correctamente!");
            }else{
                throw new IllegalArgumentException("El cliente ya existe en la base de datos");
            }
           
        } catch (IllegalArgumentException e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
    
    public void modificarCliente(){
        System.out.println("Ingrese el ID del cliente que quiere modificar");
        Integer clienteId = leer.nextInt();
        leer.nextLine();
        try {
            Cliente cliente = cDAO.buscarPorID(clienteId);

            if (cliente == null) {
                throw new Exception("No se encontró un cliente con el ID proporcionado.");
            }

            System.out.println("Ingrese el nuevo docuemento del cliente");
            if(!(leer.hasNextLong())){
                throw new IllegalArgumentException("El documento debe ser solo numero");
            }
            long documento = leer.nextLong();
            leer.nextLine();
            
            System.out.println("Ingrese el nuevo nombre del cliente");
            String nombre = leer.nextLine();
            if(nombre.isEmpty()){
                throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
            }
            
            System.out.println("Ingrese el nuevo apellido del cliente");
            String apellido = leer.nextLine();
            if(apellido.isEmpty()){
                throw new IllegalArgumentException("El apellido del cliente no puede estar vacío.");
            }
            
            System.out.println("Ingrese el nuevo telefono del cliente");
            String telefono = leer.nextLine();
            if(telefono.isEmpty()){
                throw new IllegalArgumentException("El telefono del cliente no puede estar vacío.");
            }
            
            cliente.setDocumento(documento);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);
            
            //Verifica si el documento ya existe en la base de datos
            List<Cliente> clientes = cDAO.listarTodos();
            boolean noRepetido= true;
            for (Cliente cliente1 : clientes) {
                if(cliente.compararDocumento(cliente1)){
                    noRepetido = false;
                }
            }
            
            if(noRepetido){
                cDAO.editar(cliente);
                System.out.println("¡Cliente modificado correctamente!");
            }else{
                throw new IllegalArgumentException("El cliente ya existe en la base de datos");
            }
           
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
    
    public void eliminarCliente(){
        System.out.println("Ingrese el ID de cliente que quiere borrar");
        Integer clienteId = leer.nextInt();
        leer.nextLine();
        try{
            Cliente cliente = cDAO.buscarPorID(clienteId);
            
            if (cliente == null) {
                throw new Exception("No se encontró un cliente con el ID proporcionado.");
            }
            
            cDAO.eliminar(clienteId);
            System.out.println("El cliente ah sido eleimino correctamente!");
            
        }catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
    
    public void consultarCliente(){
        System.out.println("Ingrese el ID del cliente ah consultar");
        int autorId = leer.nextInt();
        leer.nextLine(); // Limpiar el buffer después de leer un entero para evitar problemas con el siguiente nextLine().

        try {
            Cliente cliente = cDAO.buscarPorID(autorId);

            if (cliente == null) {
                throw new Exception("No se encontró un autor con el ID proporcionado.");
            }

            System.out.println(cliente.toString());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
    
    public void consultarClientes(){
        System.out.println("Se consultaran todos los clientes");
        try{
            List<Cliente> clientes = cDAO.listarTodos();
            for (Cliente cliente : clientes) {
                System.out.println(cliente.toString());
            }
        }catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
