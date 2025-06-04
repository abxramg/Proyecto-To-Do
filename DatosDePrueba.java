import java.time.LocalDate;

public class DatosDePrueba {

    public static void cargar(GestorUsuarios gestorUsuarios, ListaTareas listaTareas) {

        if (gestorUsuarios.getUsuarios().isEmpty()) {
            Administrador admin = new Administrador("Abigail Ramírez Guzmán", "Abigail", "abigail@gmail.com", "1234");
            Desarrollador dev = new Desarrollador("Emmanuel Márquez Hernández", "Emma", "emma@gmail.com", "2345");
            Invitado invitado = new Invitado("Jonathan Chávez", "Jonathan", "jonathan@gmail.com", "3456");

            gestorUsuarios.agregarUsuario(admin);
            gestorUsuarios.agregarUsuario(dev);
            gestorUsuarios.agregarUsuario(invitado);

            System.out.println("Usuarios de prueba creados.");
        }

        if (listaTareas.getTareas().isEmpty()) {
            Usuario dev = gestorUsuarios.buscarPorCorreoONick("Emma");

            LocalDate hoy = LocalDate.now();
            LocalDate inicio = hoy.plusDays(1);
            LocalDate fin = hoy.plusDays(5);

            Tarea ejemplo = new Tarea("Realizar informe del proyecto", dev, inicio, fin);
            listaTareas.agregarTarea(ejemplo);

            System.out.println("Tarea de prueba creada.");
        }
    }
}
