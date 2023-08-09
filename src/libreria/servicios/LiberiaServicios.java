
package libreria.servicios;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LiberiaServicios {
    private Scanner leer = new Scanner(System.in);
    AutorServicios aServ = new AutorServicios();
    EditorialServicios eServ = new EditorialServicios();
    LibroServicios lServ = new LibroServicios();
    ClienteServicios cServ = new ClienteServicios();
    PrestamoServicios pServ = new PrestamoServicios();
   
    
    
    public void menu() throws Exception {
        System.out.println();
        System.out.println(" Seleccione de los siguientes la opcion deseada");
        System.out.println();
        System.out.println("1) Menu de Autor.");
        System.out.println("2) Menu de Editorial.");
        System.out.println("3) Menu de Libros.");
        System.out.println("4) Menu de Clientes.");
        System.out.println("5) Menu de Prestamos.");
        System.out.println("6) Salir.");

        int op;
        while (true) {
            try {
                // Verifica si el siguiente valor a leer es un entero
                if (leer.hasNextInt()) {
                    op = leer.nextInt();
                    leer.nextLine();
                    break; // Salir del bucle si se leyó un entero
                } else {
                    leer.nextLine(); // Se limpia el buffer
                    System.out.println("Error: Ingrese una opción válida (número entero).");
                }
            } catch (Exception e) {
                leer.nextLine(); 
                System.out.println("Error: Ingrese una opción válida (número entero).");
            }
        }

        opciones(op);
    }

    public void opciones(int opcion) throws Exception {
        switch (opcion) {
            case 1:
                aServ.menu();
                menu();
                break;
            case 2:
                eServ.menu();
                menu();
                break;
            case 3:
                lServ.menu();
                menu();
                break;
            case 4:
                cServ.menu();
                menu();
                break;
            case 5:
                pServ.menu();
                menu();
                break;
            case 6:
                System.out.println("El programa termina");
                break;
            default:
                System.out.println("Ingrese una opcion valida");
                menu();

        }
    }
}
