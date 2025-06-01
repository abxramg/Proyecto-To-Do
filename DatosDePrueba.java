import java.time.LocalDate;

public class DatosDePrueba {

    public static void cargar(GestorUsuarios gestorUsuarios, ListaTareas listaTareas) {

        if (gestorUsuarios.getUsuarios().isEmpty()) {
            Administrador admin = new Administrador("Emma Admin", "admin", "emma@gmail.com", "1234");
            Desarrollador dev = new Desarrollador("Abigail", "dev", "abigail@gmail.com", "2345");
            Invitado invitado = new Invitado("Jonathan", "invitado", "jonathan@gmail.com", "3456");

            gestorUsuarios.agregarUsuario(admin);
            gestorUsuarios.agregarUsuario(dev);
            gestorUsuarios.agregarUsuario(invitado);

            System.out.println("Usuarios de prueba creados.");
        }

        if (listaTareas.getTareas().isEmpty()) {
            Usuario dev = gestorUsuarios.buscarPorCorreoONick("dev");

            LocalDate hoy = LocalDate.now();
            LocalDate inicio = hoy.plusDays(1);
            LocalDate fin = hoy.plusDays(5);

            Tarea ejemplo = new Tarea("Realizar informe del proyecto", dev, inicio, fin);
            listaTareas.agregarTarea(ejemplo);

            System.out.println("Tarea de prueba creada.");
        }
    }
}
