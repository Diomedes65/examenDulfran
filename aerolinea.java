import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class aerolinea {
    private List<Pasajero> pasajerosRegistrados;
    private Map<String, Integer> ventas;
    private Scanner scanner;

    public aerolinea() {
        pasajerosRegistrados = new ArrayList<>();
        ventas = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        aerolinea aerolinea = new aerolinea();
        aerolinea.iniciarOperaciones();
    }

    public void iniciarOperaciones() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Registrar pasajero");
            System.out.println("2. Vender tickets");
            System.out.println("3. Consultar ventas");
            System.out.println("4. Consultar clientes registrados");
            System.out.println("5. Salir");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarPasajero();
                    break;
                case 2:
                    venderTickets();
                    break;
                case 3:
                    consultarVentas();
                    break;
                case 4:
                    consultarClientesRegistrados();
                    break;
                case 5:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione nuevamente.");
            }
        }
        scanner.close();
    }

    public void registrarPasajero() {
        System.out.println("Ingrese el nombre del pasajero:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el contacto del pasajero:");
        String contacto = scanner.nextLine();

        System.out.println("Ingrese el correo del pasajero:");
        String correo = scanner.nextLine();

        System.out.println("Ingrese el sexo del pasajero (M/F):");
        char sexo = scanner.nextLine().charAt(0);

        System.out.println("Seleccione el nivel de membresía del pasajero:");
        System.out.println("1. Bronce");
        System.out.println("2. Plata");
        System.out.println("3. Oro");
        System.out.println("4. Platino");
        int opcionMembresia = scanner.nextInt();
        scanner.nextLine();

        Membresia membresia = Membresia.values()[opcionMembresia - 1];

        Pasajero pasajero = new Pasajero(nombre, contacto, correo, sexo, membresia);
        pasajerosRegistrados.add(pasajero);

        System.out.println("Pasajero registrado con éxito.");
    }

    public void venderTickets() {
        System.out.println("Ingrese el nombre del pasajero:");
        String nombrePasajero = scanner.nextLine();

        Pasajero pasajero = buscarPasajero(nombrePasajero);
        if (pasajero == null) {
            System.out.println("Error: Pasajero no encontrado.");
            return;
        }

        System.out.println("Ingrese la cantidad de tickets a vender:");
        int cantidad = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ingrese la clase de los tickets (Económica/Primera Clase):");
        String clase = scanner.nextLine();

        System.out.println("Ingrese el porcentaje de descuento (0-100):");
        double descuento = scanner.nextDouble();
        scanner.nextLine(); // Limpiar el buffer de entrada

        comprarTicket(pasajero, cantidad, clase, descuento);
    }

    public void comprarTicket(Pasajero pasajero, int cantidad, String clase, double descuento) {
        double precioUnitario = obtenerPrecioClase(clase);
        double total = precioUnitario * cantidad * (1 - descuento / 100);

        // Registrar venta
        ventas.put(pasajero.getNombre(), ventas.getOrDefault(pasajero.getNombre(), 0) + cantidad);

        System.out.println("Venta realizada a " + pasajero.getNombre() + ": " + cantidad + " tiquetes de clase " + clase + ". Total a pagar: $" + total);
    }

    public void consultarVentas() {
        System.out.println("Ventas realizadas:");
        for (Map.Entry<String, Integer> entry : ventas.entrySet()) {
            System.out.println(entry.getValue() + " tiquetes vendidos a " + entry.getKey());
        }
    }

    public void consultarClientesRegistrados() {
        System.out.println("Clientes registrados:");
        for (Pasajero pasajero : pasajerosRegistrados) {
            System.out.println(pasajero);
        }
    }

    private Pasajero buscarPasajero(String nombre) {
        for (Pasajero pasajero : pasajerosRegistrados) {
            if (pasajero.getNombre().equalsIgnoreCase(nombre)) {
                return pasajero;
            }
        }
        return null;
    }

    private double obtenerPrecioClase(String clase) {
        // Simplemente un ejemplo, aquí iría la lógica para obtener el precio de la clase
        if (clase.equalsIgnoreCase("Económica")) {
            return 100.0;
        } else if (clase.equalsIgnoreCase("Primera Clase")) {
            return 300.0;
        } else {
            return 0.0; // En caso de clase no reconocida
        }
    }
}

class Pasajero {
    private String nombre;
    private String contacto;
    private String correo;
    private char sexo;
    private Membresia membresia;

    public Pasajero(String nombre, String contacto, String correo, char sexo, Membresia membresia) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.correo = correo;
        this.sexo = sexo;
        this.membresia = membresia;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    // Otros getters y setters

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Contacto: " + contacto + ", Correo: " + correo + ", Sexo: " + sexo + ", Membresía: " + membresia;
    }
}

enum Membresia {
    BRONCE, PLATA, ORO, PLATINO
}
