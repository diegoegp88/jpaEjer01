package libreria.servicios;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;
import libreria.persistencia.AutorDAO;
import libreria.persistencia.EditorialDAO;
import libreria.persistencia.LibroDAO;

public class LibroServicios {

    private Scanner leer = new Scanner(System.in);
    LibroDAO lDAO = new LibroDAO();
    AutorServicios aServ = new AutorServicios();
    AutorDAO aDAO = new AutorDAO();
    EditorialDAO eDAO = new EditorialDAO();

    public void menu() {
        try {
            System.out.println();
            System.out.println(" Bienvenidos al menu de Libro");
            System.out.println();
            System.out.println("Selecciones una opcion");
            System.out.println("1) Agregar un Libro");
            System.out.println("2) Modificar un Libro existente");
            System.out.println("3) Eliminar un Libro");
            System.out.println("4) Consultar un Libro");
            System.out.println("5) Consultar todos los Libros");
            System.out.println("6) volver al menu anterior");
            int op = leer.nextInt();
            leer.nextLine(); // Limpiar el buffer después de leer un entero para evitar problemas con el siguiente nextLine().
            opciones(op);
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese una opción válida (número entero).");
            leer.nextLine();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }


    public void opciones(int opcion) throws Exception {
        switch (opcion) {
            case 1:
                agregarLibro();
                menu();
                break;
            case 2:
                modificarLibro();
                menu();
                break;
            case 3:
                eliminarLibro();
                menu();
                break;
            case 4:
                consultarLibro();
                menu();
                break;
            case 5:
                consultarLibros();
                menu();
                break;
            case 6:
                break;
            default:
                System.out.println("Ingrese una opcion valida");
                menu();
        }
    }

    public void agregarLibro() {
        Libro libro = new Libro();
        System.out.println("Para ingresar un nuevo Libro por favor indique lo siguiente");
        System.out.println();

        try {
            System.out.println("Ingrese el ISBN del libro");
            long isbn = leer.nextLong();
            leer.nextLine(); // Limpiar el buffer después de leer un entero.
            libro.setIsbn(isbn);

            System.out.println("Ingrese el año del libro");
            int anio = leer.nextInt();
            leer.nextLine(); // Limpiar el buffer después de leer un entero.
            libro.setAnio(anio);

            System.out.println("Ingrese el título del libro");
            String titulo = leer.nextLine();
            if (titulo.isEmpty()) {
                throw new IllegalArgumentException("El título del libro no puede estar vacío.");
            }
            libro.setTitulo(titulo);

            System.out.println("Ingrese el ID del autor");
            Autor autor = aDAO.buscarPorID(leer.nextInt());
            if (autor == null) {
                throw new Exception("No se encontró un autor con el ID proporcionado.");
            }
            libro.setAutor(autor);

            System.out.println("Ingrese el ID de la editorial");
            Editorial editorial = eDAO.buscarPorID(leer.nextInt());
            if (editorial == null) {
                throw new Exception("No se encontró una editorial con el ID proporcionado.");
            }
            libro.setEditorial(editorial);
            
            System.out.println("Ingrese la cantidad de ejemplares");
            if(!(leer.hasNextInt())){
                throw new Exception("La cantidad debe ser numerica y entera y positiva");
            }
            int cantidadEjem = leer.nextInt();
            libro.setEjemplares(cantidadEjem);
            libro.setEjemplaresPrestados(0);
            libro.setEjemplaresRestantes(cantidadEjem);

            List<Libro> libros = lDAO.listarTodos();

            // Comprueba que el ISBN y el título no existan ya en la BD
            for (Libro libroExistente : libros) {
                if (libroExistente.getIsbn() == isbn) {
                    throw new Exception("El ISBN ya existe.");
                }
                if (libroExistente.getTitulo().equals(titulo)) {
                    throw new Exception("El título ya existe.");
                }
            }
            libro.setAlta(true);
            lDAO.guardar(libro);

            System.out.println("¡Libro agregado correctamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al agregar el libro: " + e.getMessage());
        }
    }

    public void modificarLibro() throws Exception {
    System.out.println("Para modificar un libro, por favor ingrese lo siguiente:");
    System.out.println();
    System.out.println("Ingrese el ISBN del libro a modificar");
    long isbn = leer.nextLong();
    leer.nextLine(); // Limpiar el buffer 

    try {
        Libro libro = lDAO.buscarPorISBN(isbn);

        if (libro == null) {
            throw new Exception("No se encontró un libro con el ISBN proporcionado.");
        }

        System.out.println("Ingrese el año del libro (si no se modifica, ingrese 0)");
        int anio = leer.nextInt();
        leer.nextLine(); // Limpiar el buffer después de leer un entero.
        if (anio != 0) {
            libro.setAnio(anio);
        }

        System.out.println("Ingrese el título del libro");
        String titulo = leer.nextLine();
        if (!titulo.isEmpty()) {
            libro.setTitulo(titulo);
        }

        System.out.println("Ingrese el ID del autor (si no se modifica, ingrese 0)");
        int idAutor = leer.nextInt();
        if (idAutor != 0) {
            Autor autor = aDAO.buscarPorID(idAutor);
            if (autor == null) {
                throw new Exception("No se encontró un autor con el ID proporcionado.");
            }
            libro.setAutor(autor);
        }

        System.out.println("Ingrese el ID de la editorial (si no se modifica, ingrese 0)");
        int idEditorial = leer.nextInt();
        if (idEditorial != 0) {
            Editorial editorial = eDAO.buscarPorID(idEditorial);
            if (editorial == null) {
                throw new Exception("No se encontró una editorial con el ID proporcionado.");
            }
            libro.setEditorial(editorial);
        }

        System.out.println("Ingrese la cantidad de ejemplares a sumar (si no se suman, ingrese 0)");
        int cantidadEjem = leer.nextInt();
        if (cantidadEjem != 0) {
            if (libro.getEjemplares() == null) {
                libro.setEjemplares(cantidadEjem);
            } else {
                libro.setEjemplares(libro.getEjemplares() + cantidadEjem);
            }
            if (libro.getEjemplaresRestantes() == null) {
                libro.setEjemplaresRestantes(cantidadEjem);
            } else {
                libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() + cantidadEjem);
            }
            if(libro.getEjemplaresPrestados()==null){
                libro.setEjemplaresPrestados(0);
            }
        }

        lDAO.editar(libro);
        System.out.println("¡Libro modificado correctamente!");
    } catch (Exception e) {
        System.out.println("Ha ocurrido un error: " + e.getMessage());
    }
}


    public void eliminarLibro() throws Exception {
        System.out.println("Para eliminar un libro, por favor ingrese lo siguiente:");
        System.out.println();
        System.out.println("Ingrese el ISBN del libro a eliminar");
        long isbn = leer.nextLong();
        leer.nextLine(); // Limpiar el buffer después de leer un entero para evitar problemas con el siguiente nextLine().

        try {
            Libro libro = lDAO.buscarPorISBN(isbn);

            if (libro == null) {
                throw new Exception("No se encontró un libro con el ISBN proporcionado.");
            }

            lDAO.eliminar(isbn);
            System.out.println("¡Libro eliminado correctamente!");
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public void consultarLibro() {
        System.out.println("Para consultar un libro, por favor indique lo siguiente");
        System.out.println();
        System.out.println("La consulta será por:");
        System.out.println("1) Titulo");
        System.out.println("2) ISBN");
        System.out.println("3) Autor");
        System.out.println("4) Editorial");
        System.out.println("5) Volver al menú anterior");

        int opcion;
        do {
            System.out.println("Ingrese una opción válida:");
            opcion = leer.nextInt();
            leer.nextLine(); // Limpiar el buffer después de leer un entero.
        } while (opcion < 1 || opcion > 5);

        try {
            switch (opcion) {
                case 1:
                    consultarPorTitulo();
                    break;
                case 2:
                    consultarPorISBN();
                    break;
                case 3:
                    consultarPorAutor();
                    break;
                case 4:
                    consultarPorEditorial();
                    break;
                case 5:
                    break;
            }

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error: " + e.getMessage());
        }
    }

    private void consultarPorTitulo() throws Exception {
        System.out.println("Ingrese el título");
        String titulo = leer.nextLine();
        Libro libro = lDAO.buscarPorTitulo(titulo);

        if (libro == null) {
            throw new Exception("No se encontró un libro con el título proporcionado.");
        }

        System.out.println(libro.toString());
    }

    private void consultarPorISBN() throws Exception {
        System.out.println("Ingrese el ISBN");
        long isbn = leer.nextLong();
        leer.nextLine();
        Libro libro = lDAO.buscarPorISBN(isbn);

        if (libro == null) {
            throw new Exception("No se encontró un libro con el ISBN proporcionado.");
        }

        System.out.println(libro.toString());
    }

    private void consultarPorAutor() throws Exception {
        System.out.println("Ingrese el Id del autor");
        int autorId = leer.nextInt();
        leer.nextLine();
        List<Libro> libros = lDAO.buscarPorAutor(autorId);

        if (libros.isEmpty()) {
            throw new Exception("No se encontró ningún libro con el autor proporcionado.");
        }

        for (Libro libro : libros) {
            System.out.println(libro.toString());
        }
    }

    private void consultarPorEditorial() throws Exception {
        System.out.println("Ingrese el Id de la editorial");
        int editorialId = leer.nextInt();
        leer.nextLine();
        List<Libro> libros = lDAO.buscarPorEditorial(editorialId);

        if (libros.isEmpty()) {
            throw new Exception("No se encontró ningún libro con la editorial proporcionada.");
        }

        for (Libro libro : libros) {
            System.out.println(libro.toString());
        }
    }

    public void consultarLibros() {
        try {
            System.out.println("Se consultará todos los libros");
            System.out.println();
            Collection<Libro> libros = lDAO.listarTodos();

            for (Libro libro : libros) {
                System.out.println(libro.toString());
            }
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al consultar los libros: " + e.getMessage());
        }
    }

}