import java.time.LocalDate;

/**
 * Clase que proporciona datos de prueba para cargar usuarios y tareas
 * en el sistema. Se utiliza para pruebas iniciales y demostraciones.
 */
public class DatosDePrueba {

    /**
     * Carga usuarios y tareas de prueba en las estructuras proporcionadas.
     *
     * @param gestorUsuarios Objeto encargado de la gestión de usuarios.
     * @param listaTareas Objeto encargado de la gestión de tareas.
     */
    public static void cargar(GestorUsuarios gestorUsuarios, ListaTareas listaTareas) {

        // Verifica si aún no se han cargado usuarios al sistema
        if (gestorUsuarios.getUsuarios().isEmpty()) {
            // Usuario Administrador de prueba
            Administrador admin = new Administrador("Abigail Ramírez Guzmán", "Abigail", "abigail@gmail.com", "1234");
            // Usuario Desarrollador de prueba
            Desarrollador dev = new Desarrollador("Emmanuel Márquez Hernández", "Emma", "emma@gmail.com", "2345");
             // Usuario Invitado de prueba
            Invitado invitado = new Invitado("Jonathan Chávez Gutiérrez", "Jonathan", "jonathan@gmail.com", "3456");

            // Agrega los usuarios creados al gestor
            gestorUsuarios.agregarUsuario(admin);
            gestorUsuarios.agregarUsuario(dev);
            gestorUsuarios.agregarUsuario(invitado);

            // Mensaje en consola indicando que los usuarios de prueba fueron creados
            System.out.println("Usuarios de prueba creados.");
        }

        if (listaTareas.getTareas().isEmpty()) {

            // Busca al usuario con nickname o correo "Emma"
            Usuario dev = gestorUsuarios.buscarPorCorreoONick("Emma");

            // Define las fechas de inicio y fin de la tarea
            LocalDate hoy = LocalDate.now();    // Fecha actual
            LocalDate inicio = hoy.plusDays(1);    // Inicio: mañana
            LocalDate fin = hoy.plusDays(5);    // Fin: en 5 días

            // Crea una tarea de prueba asignada al usuario "Emma"
            Tarea ejemplo = new Tarea("Realizar informe del proyecto", dev, inicio, fin);

                // Agrega la tarea a la lista
            listaTareas.agregarTarea(ejemplo);

            // Mensaje en consola indicando que la tarea de prueba fue creada.
            System.out.println("Tarea de prueba creada.");
        }
    }
}
