import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de gestionar una lista de tareas.
 * Permite agregar, modificar, eliminar y mostrar tareas, así como guardarlas
 * y cargarlas desde un archivo mediante serialización.
 */
public class ListaTareas {

    // Lista principal de tareas activas en el sistema.
    private List<Tarea> tareas;
    // Archivo donde se guarda la lista de tareas de manera persistente.
    private final String archivo = "tareas.ser";

    /**
     * Constructor que inicializa la lista y carga las tareas desde el archivo (si existe).
     */
    public ListaTareas() {
        this.tareas = new ArrayList<>();
        cargarTareas();
    }

    /**
     * Agrega una nueva tarea a la lista y actualiza el archivo.
     *
     * @param tarea Tarea a agregar.
     */
    public void agregarTarea(Tarea tarea) {
        tareas.add(tarea);
        guardarTareas();
    }

    /**
     * Elimina una tarea de la lista según su identificador.
     *
     * @param id Identificador de la tarea a eliminar.
     */
    public void eliminarTarea(int id) {
        tareas.removeIf(t -> t.getId() == id);
        guardarTareas();
    }

    /**
     * Modifica una tarea existente con una nueva versión, si coincide el ID.
     *
     * @param id Identificador de la tarea a modificar.
     * @param nuevaTarea Nueva tarea con los datos actualizados.
     */
    public void modificarTarea(int id, Tarea nuevaTarea) {
        for (int i=0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id ) {
                tareas.set(i, nuevaTarea);
                guardarTareas();
                break;
            }
        }
    }

     /**
     * Permite que un usuario modifique solo sus propias tareas.
     *
     * @param id Identificador de la tarea.
     * @param autor Usuario que realiza la modificación.
     * @param nuevaTarea Nueva versión de la tarea.
     */
    public void modificarMisTareas(int id, Usuario autor, Tarea nuevaTarea) {
        for (int i=0; i < tareas.size(); i++) {
            if (tareas.get(i).getId() == id && tareas.get(i).getAsignacion().equals(autor)) {
                tareas.set(i, nuevaTarea);
                guardarTareas();
                break;
            }
        }
    }

    /**
     * Muestra en consola todas las tareas disponibles.
     */
    public void mostrarTareas() {
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas asignadas.");
            return;
        }

        for (Tarea t : tareas) {
            System.out.println(t);
        }
    }

    /**
     * Muestra en consola solo las tareas que están en un estado específico.
     *
     * @param estado Estado de las tareas a mostrar (ej. "PENDIENTE", "COMPLETADA").
     */
    public void mostrarTareasPorEstado(String estado) {
        ArrayList<Tarea> filtradas = new ArrayList<>();
        for (Tarea t : tareas) {
            if (t.getEstado().toString().equalsIgnoreCase(estado)) {
                filtradas.add(t);
            }
        }
        if (filtradas.isEmpty()) {
            System.out.println("No hay tareas en el estado seleccionado.\n");
        } else {
            for (Tarea t : filtradas) {
                System.out.println(t);
            }
        }
    }

     /**
     * Muestra en consola las tareas asignadas a un usuario específico.
     *
     * @param usuario Usuario al que están asignadas las tareas.
     */
    public void mostrarTareasPorUsuario(Usuario usuario) {
        ArrayList<Tarea> filtradas = new ArrayList<>();
        for (Tarea t : tareas) {
            if (t.getAsignacion().getId() == usuario.getId()) {
                filtradas.add(t);
            }
        }
        if (filtradas.isEmpty()) {
            System.out.println("No hay tareas asignadas a tal usuario.\n");
        } else {
            for (Tarea tarea : filtradas) {
                System.out.println("\n" + tarea);
            }
        }
    }

     /**
     * Muestra en consola las tareas de un usuario filtradas por estado.
     *
     * @param usuario Usuario dueño de las tareas.
     * @param estado Estado que se desea filtrar.
     */
    public void mostrarTareasPorUsuarioYEstado(Usuario usuario, String estado) {
        ArrayList<Tarea> filtradas = new ArrayList<>();
        for (Tarea t : tareas) {
            if (t.getAsignacion().getId() == usuario.getId() && t.getEstado().toString().equalsIgnoreCase(estado)) {
                filtradas.add(t);
            }
        }
        if (filtradas.isEmpty()) {
            System.out.println("No tienes tareas con ese estado.");
        } else {
            for (Tarea tarea : filtradas) {
                System.out.println("\n" + tarea);
            }
        }
    }

    /**
     * Busca una tarea por su identificador único.
     *
     * @param id Identificador de la tarea a buscar.
     * @return Tarea encontrada o null si no existe.
     */
    public Tarea buscarPorID(int id) {
        for (Tarea t : tareas) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    /**
     * Guarda la lista de tareas en un archivo mediante serialización.
     * En caso de error, se imprime un mensaje en consola.
     */
    private void guardarTareas() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(tareas);
        } catch (IOException e) {
            System.out.println("Error al guardar tareas: " + e.getMessage());
        }
    }

    /**
     * Carga la lista de tareas desde el archivo, si este existe.
     * En caso de error durante la lectura o deserialización, se muestra un mensaje.
     */
    @SuppressWarnings("unchecked")
    private void cargarTareas() {
        File f = new File(archivo);
        if (f.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
                tareas = (List<Tarea>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error al cargar tareas: " + e.getMessage());
            }
        }
    }

    /**
     * Retorna la lista completa de tareas.
     *
     * @return Lista de tareas cargadas o en memoria.
     */
    public List<Tarea> getTareas() {
        return tareas;
    }
}
