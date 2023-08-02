package libreria.servicios;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
import libreria.entidades.Editorial;
import libreria.persistencia.EditorialDAO;

public class EditorialServicios {

    private Scanner leer = new Scanner(System.in);
    EditorialDAO eDAO = new EditorialDAO();

    public void menu() throws Exception {
        try{
        System.out.println();
        System.out.println(" Bienvenidos al menu de Editorial");
        System.out.println();
        System.out.println("Selecciones una opcion");
        System.out.println("1) Agregar una Editorial");
        System.out.println("2) Modificar una Editorial existente");
        System.out.println("3) Eliminar una Editorial");
        System.out.println("4) Consultar una Editorial");
        System.out.println("5) Consultar todas las Editoriales");
        System.out.println("6) Volver al menu principal");
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
                agregarEditorial();
                menu();
                break;
            case 2:
                modificarEditorial();
                menu();
                break;
            case 3:
                eliminarEditorial();
                menu();
                break;
            case 4:
                consultarEditorial();
                menu();
                break;
            case 5:
                consultarEditoriales();
                menu();
                break;
            case 6:
                break;
            default:
                System.out.println("Ingrese una opcion valida");
                menu();
        }
    }

    public void agregarEditorial() throws Exception {
        Editorial editorial = new Editorial();
        System.out.println("Para ingresar un nuevo Editor por favor indique lo siguiente");
        System.out.println();

        try {
            System.out.println("Ingrese el nombre de la Editorial");
            String nombreEditorial = leer.nextLine();

            if (nombreEditorial.isEmpty()) {
                throw new IllegalArgumentException("El nombre de la editorial no puede estar vacío.");
            }

            editorial.setNombre(nombreEditorial);
            editorial.setAlta(true);
            eDAO.guardar(editorial);
            System.out.println("¡Editorial agregado correctamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        } 
    }

    public void modificarEditorial() throws Exception {
        System.out.println("Para modificar una Editorial, por favor ingrese lo siguiente:");
        System.out.println();
        System.out.println("Ingrese el ID de la Editorial a modificar:");
        int autorId = leer.nextInt();
        leer.nextLine(); // Limpiar el buffer después de leer un entero para evitar problemas con el siguiente nextLine().

        try {
            Editorial editorial = eDAO.buscarPorID(autorId);

            if (editorial == null) {
                throw new Exception("No se encontró una editorial con el ID proporcionado.");
            }

            System.out.println("Ingrese el nuevo nombre de la editorial");
            String nuevoNombreEditorial = leer.nextLine();

            if (nuevoNombreEditorial.isEmpty()) {
                throw new IllegalArgumentException("El nombre de la editorial no puede estar vacío.");
            }

            editorial.setNombre(nuevoNombreEditorial);
            eDAO.guardar(editorial);
            System.out.println("¡Editorial modificado correctamente!");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public void eliminarEditorial() throws Exception {
        System.out.println("Para eliminar una Editorial, por favor ingrese lo siguiente");
        System.out.println();
        System.out.println("Ingrese el ID de la editorial a eliminar");
        int editorialId = leer.nextInt();
        leer.nextLine(); // Limpiar el buffer después de leer un entero para evitar problemas con el siguiente nextLine().

        try {
            Editorial editorial = eDAO.buscarPorID(editorialId);

            if (editorial == null) {
                throw new Exception("No se encontró una editorial con el ID proporcionado.");
            }

            eDAO.eliminar(editorialId);
            System.out.println("¡Editorial eliminado correctamente!");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public void consultarEditorial() throws Exception {
        System.out.println("Para consultar una Editorial, por favor ingrese lo siguiente");
        System.out.println();
        System.out.println("Ingrese el ID de la editorial");
        int editorialId = leer.nextInt();
        leer.nextLine(); // Limpiar el buffer después de leer un entero para evitar problemas con el siguiente nextLine().

        try {
            Editorial editorial = eDAO.buscarPorID(editorialId);

            if (editorial == null) {
                throw new Exception("No se encontró una editorial con el ID proporcionado.");
            }

            System.out.println(editorial.toString());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public void consultarEditoriales() {
        try {
            System.out.println("Se consultará todos las editoriales");
            System.out.println();
            Collection<Editorial> editoriales = eDAO.listarTodos();

            for (Editorial editorial : editoriales) {
                System.out.println(editorial.toString());
            }
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al consultar las editoriales: " + e.getMessage());
        }
    }

}
