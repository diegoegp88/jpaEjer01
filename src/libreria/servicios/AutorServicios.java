package libreria.servicios;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
import libreria.entidades.Autor;
import libreria.persistencia.AutorDAO;

public class AutorServicios {

    private Scanner leer = new Scanner(System.in);

    AutorDAO aDAO = new AutorDAO();

    public void menu() throws Exception {
        try{
        System.out.println();
        System.out.println(" Bienvenidos al menu de Autor");
        System.out.println();
        System.out.println("Selecciones una opcion");
        System.out.println("1) Agregar un Autor");
        System.out.println("2) Modificar un autor existente");
        System.out.println("3) Eliminar un Autor");
        System.out.println("4) Consultar un Autor");
        System.out.println("5) Consultar todos los Autores");
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
                agregarAutor();
                menu();
                break;
            case 2:
                modificarAutor();
                menu();
                break;
            case 3:
                eliminarAutor();
                menu();
                break;
            case 4:
                consultarAutor();
                menu();
                break;
            case 5:consultarAutores();
                menu();
                break;
            case 6:
                break;
            default:
                System.out.println("Ingrese una opcion valida");
                menu();
        }
    }
    
    
    

    public void agregarAutor() throws Exception {
        Autor autor = new Autor();
        System.out.println("Para ingresar un nuevo Autor por favor indique lo siguiente");
        System.out.println();

        try {
            System.out.println("Ingrese el nombre del Autor");
            String nombreAutor = leer.nextLine();

            if (nombreAutor.isEmpty()) {
                throw new IllegalArgumentException("El nombre del autor no puede estar vacío.");
            }

            autor.setNombre(nombreAutor);
            autor.setAlta(true);
            aDAO.guardar(autor);
            System.out.println("¡Autor agregado correctamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public void modificarAutor() throws Exception {
        System.out.println("Para modificar un autor, por favor ingrese lo siguiente:");
        System.out.println();
        System.out.println("Ingrese el ID del autor a modificar:");
        int autorId = leer.nextInt();
        leer.nextLine(); // Limpiar el buffer después de leer un entero para evitar problemas con el siguiente nextLine().

        try {
            Autor autor = aDAO.buscarPorID(autorId);

            if (autor == null) {
                throw new Exception("No se encontró un autor con el ID proporcionado.");
            }

            System.out.println("Ingrese el nuevo nombre del autor:");
            String nuevoNombreAutor = leer.nextLine();

            if (nuevoNombreAutor.isEmpty()) {
                throw new IllegalArgumentException("El nombre del autor no puede estar vacío.");
            }

            autor.setNombre(nuevoNombreAutor);
            aDAO.editar(autor);
            System.out.println("¡Autor modificado correctamente!");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public void eliminarAutor() throws Exception {
        System.out.println("Para eliminar un autor, por favor ingrese lo siguiente:");
        System.out.println();
        System.out.println("Ingrese el ID del autor a eliminar");
        int autorId = leer.nextInt();
        leer.nextLine(); // Limpiar el buffer después de leer un entero para evitar problemas con el siguiente nextLine().

        try {
            Autor autor = aDAO.buscarPorID(autorId);

            if (autor == null) {
                throw new Exception("No se encontró un autor con el ID proporcionado.");
            }

            aDAO.eliminar(autorId);
            System.out.println("¡Autor eliminado correctamente!");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public void consultarAutor() throws Exception {
        System.out.println("Para consultar un autor, por favor ingrese lo siguiente:");
        System.out.println();
        System.out.println("Ingrese el ID del autor");
        int autorId = leer.nextInt();
        leer.nextLine(); // Limpiar el buffer después de leer un entero para evitar problemas con el siguiente nextLine().

        try {
            Autor autor = aDAO.buscarPorID(autorId);

            if (autor == null) {
                throw new Exception("No se encontró un autor con el ID proporcionado.");
            }

            System.out.println(autor.toString());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public void consultarAutores() {
    try {
        System.out.println("Se consultará todos los autores");
        System.out.println();
        Collection<Autor> autores = aDAO.listarTodos();

        for (Autor autor : autores) {
            System.out.println(autor.toString());
        }
    } catch (Exception e) {
        System.out.println("Ha ocurrido un error al consultar los autores: " + e.getMessage());
    }
}
    
    
}
