package libreria.servicios;

import java.sql.Date;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import libreria.entidades.Cliente;
import libreria.entidades.Libro;
import libreria.entidades.Prestamo;
import libreria.persistencia.ClienteDAO;
import libreria.persistencia.LibroDAO;
import libreria.persistencia.PrestamoDAO;

public class PrestamoServicios {
    private Scanner leer = new Scanner(System.in);
    PrestamoDAO pDAO = new PrestamoDAO();
    ClienteDAO cDAO = new ClienteDAO();
    LibroDAO lDAO = new LibroDAO();
    
    public void menu() throws Exception {
        try{
        System.out.println();
        System.out.println(" Bienvenidos al menu de Prestamos");
        System.out.println();
        System.out.println("Selecciones una opcion");
        System.out.println("1) Prestar un libro");
        System.out.println("2) Devolver un libro");
        System.out.println("3) Modificar un prestamo activo");
        System.out.println("4) Consultar un Prestamo");
        System.out.println("5) Consultar todos los prestamos");
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
                prestarLibro();
                menu();
                break;
            case 2:
                devolverLibro();
                menu();
                break;
            case 3:
                modificarPrestamo();
                menu();
                break;
            case 4:
                consultarPrestamo();
                menu();
                break;
            case 5:
                consultarPrestamos();
                menu();
                break;
            case 6:
                break;
            default:
                System.out.println("Ingrese una opcion valida");
                menu();
        }
    }
    
    public void prestarLibro() throws Exception {
        Prestamo prestamo = new Prestamo();
        System.out.println("Para ingresar un nuevo prestamo por favor indique lo siguiente");
        System.out.println();

        try {
            System.out.println("Ingrese el ID del cliente");
            if(!(leer.hasNextInt())){
                throw new IllegalArgumentException("El ID debe ser solo numero");
            }
            Cliente cliente = cDAO.buscarPorID(leer.nextInt());
            leer.nextLine();
            if(cliente==null){
                throw new IllegalArgumentException("No se encontro un cliente con el ID ingresado");
            }
            prestamo.setCliente(cliente);
            
            System.out.println("Ingrese el ISBN del libro");
            if(!(leer.hasNextLong())){
                throw new IllegalArgumentException("El ISBN debe ser solo numero");
            }
            Libro libro = lDAO.buscarPorISBN(leer.nextLong());
            leer.nextLine();
            if(libro==null){
                throw new IllegalArgumentException("No se encontro un libro con el ISBN ingresado");
            }
            
            if(libro.getEjemplaresRestantes()<1){
                throw new IllegalArgumentException("No hay ejemplares disponibles para prestar del libro solicitado");
            }else{
                libro.setEjemplaresPrestados(libro.getEjemplaresPrestados()+1);
                libro.setEjemplaresRestantes(libro.getEjemplaresRestantes()-1);
            }
            lDAO.editar(libro);
            prestamo.setLibro(libro);
            prestamo.setFechaPrestamo(Date.valueOf(LocalDate.now()));
            
            pDAO.guardar(prestamo);
            System.out.println("Prestamo de "+libro.getTitulo()+" al cliente "+cliente.getApellido()+", "+cliente.getNombre());
            System.out.println("Se agrego correctamente");
            
           
           
        } catch (IllegalArgumentException e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
    
    public void devolverLibro(){
        try {
            System.out.println("Ingrese el ID del cliente");
            if(!(leer.hasNextInt())){
                throw new IllegalArgumentException("El ID debe ser solo numero");
            }
            Cliente cliente = cDAO.buscarPorID(leer.nextInt());
            leer.nextLine();
            if(cliente==null){
                throw new IllegalArgumentException("No se encontro un cliente con el ID ingresado");
            }
            Prestamo prestamo = pDAO.buscarPorIDCliente(cliente.getId());
            System.out.println("El cliente "+cliente.getNombre()+", "+cliente.getApellido()+" tiene el libro "+"\n"+prestamo.getLibro().toString());
            
            prestamo.setFechaDevolucion(Date.valueOf(LocalDate.now()));
            
            Libro libro = lDAO.buscarPorISBN(prestamo.getLibro().getIsbn());
            
            if(libro.getEjemplaresPrestados()<1){
                throw new IllegalArgumentException("No hay ejemplares para devolver del libro solicitado");
            }else{
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados()-1);
            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes()+1);
            }
            
            lDAO.editar(libro);
            pDAO.editar(prestamo);
            
        }catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
    
    public void modificarPrestamo() throws Exception{
        Prestamo prestamo = new Prestamo();
        System.out.println("Para modificar un prestamo por favor indique lo siguiente");
        System.out.println();

        try {
            System.out.println("Ingrese el ID del Prestam");
            if(!(leer.hasNextInt())){
                throw new IllegalArgumentException("El ID debe ser solo numero");
            }
            prestamo = pDAO.buscarPorID(leer.nextInt());
            leer.nextLine();
            if(prestamo==null){
                throw new IllegalArgumentException("No se encontro un prestamo con el ID ingresado");
            }
            
            System.out.println("Ingrese el ID del cliente");
            if(!(leer.hasNextInt())){
                throw new IllegalArgumentException("El ID debe ser solo numero");
            }
            Cliente cliente = cDAO.buscarPorID(leer.nextInt());
            leer.nextLine();
            if(cliente==null){
                throw new IllegalArgumentException("No se encontro un cliente con el ID ingresado");
            }
            prestamo.setCliente(cliente);
            
            System.out.println("Ingrese el ISBN del libro");
            if(!(leer.hasNextLong())){
                throw new IllegalArgumentException("El ISBN debe ser solo numero");
            }
            Libro libroNuevo = lDAO.buscarPorISBN(leer.nextLong());
            leer.nextLine();
            if(libroNuevo==null){
                throw new IllegalArgumentException("No se encontro un libro con el ISBN ingresado");
            }
            //Traigo el libro actual para comprar y modificar en caso de ser necesario.
            Libro libroActual = lDAO.buscarPorISBN(prestamo.getLibro().getIsbn());
            
            if (!(libroNuevo == libroActual)) {
                if (libroNuevo.getEjemplaresRestantes() < 1) {
                    throw new IllegalArgumentException("No hay ejemplares disponibles para prestar del libro solicitado");
                } else {
                    //Modifico los ejemplares del nuevo libro
                    libroNuevo.setEjemplaresPrestados(libroNuevo.getEjemplaresPrestados() + 1);
                    libroNuevo.setEjemplaresRestantes(libroNuevo.getEjemplaresRestantes() - 1);
                    //Modifico los ejemplares del libro actual.
                    libroActual.setEjemplaresPrestados(libroActual.getEjemplaresPrestados() - 1);
                    libroActual.setEjemplaresRestantes(libroActual.getEjemplaresRestantes() + 1);
                }
            }
            
            lDAO.editar(libroNuevo);
            lDAO.editar(libroActual);
            prestamo.setLibro(libroNuevo);
            prestamo.setFechaPrestamo(Date.valueOf(LocalDate.now()));
            
            pDAO.editar(prestamo);
            System.out.println("Prestamo de "+prestamo.getLibro().getTitulo()+" al cliente "+cliente.getApellido()+", "+cliente.getNombre());
            System.out.println("Se edito correctamente");
            
           
           
        } catch (IllegalArgumentException e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
    
    public void consultarPrestamo() {
    try {
        System.out.println("Ingrese el ID del préstamo a consultar:");
        int id = leer.nextInt();
        leer.nextLine();
        Prestamo prestamo = pDAO.buscarPorID(id);

        if (prestamo == null) {
            throw new Exception("No se encontró un préstamo con el ID proporcionado.");
        }

        System.out.println(prestamo.toString());
    } catch (Exception e) {
        System.out.println("Ha ocurrido un error: " + e.getMessage());
    }
}

    
    public void consultarPrestamos() {
        try {
            System.out.println("Se consultaran todos los prestamos:");

            List<Prestamo> prestamos = pDAO.listarTodos();
            
            //Metodo for corto.
            prestamos.forEach((prestamo) -> {
                System.out.println(prestamo.toString());
            });

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
