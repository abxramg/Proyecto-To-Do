import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Clase que representa una tarea en el sistema.

// Implementa Serializable para su almacenamiento persistente.
public class Tarea implements Serializable {

    // Contador del ID de las tareas existentes
    private static int contadorID = 1;

    // Atributos principales de una tarea
    private int id;
    private String descripcion;
    private Estado estado;
    private Usuario asignacion;

    // Atributos de una tarea relacionados a fechas
    private LocalDate fechaEstimadaInicio;
    private LocalDate fechaInicio;
    private LocalDate fechaEstimadaFinal;
    private LocalDate fechaFinal;

    /** 
     * Enumeración que representa el estado que puede tomar una tarea.
     * Solo puede estar en uno de estos tres estados: PENDIENTE, EN CURSO Y COMPLETADA.
     */
    public enum Estado {
        PENDIENTE, EN_CURSO, COMPLETADA;

        
    }

    /** 
     * Constructor para crear una nueva tarea.
     * La tarea se crea automáticamente con estado PENDIENTE.
     * @param descripcion Texto breve que describe la tarea
     * @param asignacion Usuario al que se le asigna la tarea
     * @param fechaEstimadaInicio Fecha planificada de inicio
     * @param fechaEstimadaFinal Fecha planificada de finalización
     * @throws IllegalArgumentException Si las tareas son inválidas
    */
    public Tarea(String descripcion, Usuario asignacion, LocalDate fechaEstimadaInicio, LocalDate fechaEstimadaFinal) {
        this.id = contadorID++;
        this.descripcion = descripcion;
        this.asignacion = asignacion;
        this.estado = Estado.PENDIENTE;

        // Validaciones básicas relacionadas a fechas
        if (fechaEstimadaInicio.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Fecha inválida.");
        }

        if (fechaEstimadaFinal.isBefore(fechaEstimadaInicio)) {
            throw new IllegalArgumentException("Fecha inválida.");
        }

        this.fechaEstimadaInicio = fechaEstimadaInicio;
        this.fechaEstimadaFinal = fechaEstimadaFinal;
        this.fechaInicio = null;
        this.fechaFinal = null;
    }
    
    /** 
     * Avanza el estado de la tarea:
     *      De PENDIENTE a EN_CURSO, se guarda la fecha real de inicio.
     *      De EN_CURSO a COMPLETADA, se guarda la fecha real de finalización.   
     */
    public void avanzarEstado() {
        switch (estado) {
            case PENDIENTE:
                    this.estado = Estado.EN_CURSO;
                    this.fechaInicio = LocalDate.now();
                break;
            case EN_CURSO: 
                    this.estado = Estado.COMPLETADA;
                    this.fechaFinal = LocalDate.now();
                break;
            case COMPLETADA: 
                    System.out.println("¡Tarea completada!");
                break;
        }
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public Usuario getAsignacion() {
        return asignacion;
    }

    public LocalDate getFechaEstimadaInicio() {
        return fechaEstimadaInicio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaEstimadaFinal() {
        return fechaEstimadaFinal;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }


    // Setters con validación

    /** Permite cambiar la descripción solo si la tarea está en estado PENDIENTE. */

    public void setDescripcion(String descripcion) {
        if (estado != Estado.PENDIENTE) {
            System.out.println("No se puede cambiar la descripción una vez iniciada.");
            return;
        }

        this.descripcion = descripcion;
    }

    public void setFechaEstimadaInicio(LocalDate fecha) {
        if (estado != Estado.PENDIENTE) {
            System.out.println("No se puede modificar la fecha estimada de inicio una vez iniciada.");
            return;
        }

        if (fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha estimada de inicio no puede estar en el pasado.");
        }

        this.fechaEstimadaInicio = fecha;
    }

    public void setFechaEstimadaFin(LocalDate fecha) {
        if (estado != Estado.PENDIENTE) {
            System.out.println("La fecha estimada de finalización no puede modificarse una vez iniciada.");
            return;
        }

        if (fecha.isBefore(fechaEstimadaInicio)) {
            throw new IllegalArgumentException("La fecha estimada de finalización no puede ser anterior a la fecha estimada de inicio.");
        }
        this.fechaEstimadaFinal = fecha;
    }

    public void setAsignacion(Usuario nuevoUsuario) {
        if (estado != Estado.PENDIENTE) {
            System.out.println("No se puede reasignar una tarea ya en curso/completada.");
            return;
        }
        this.asignacion = nuevoUsuario;
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "ID: " + id + 
                "\nDescripción:" + descripcion + 
                "\nAsignación: " + asignacion.getNickname() + 
                "\nEstado: " + estado + 
                "\nFecha estimada de inicio: " + formato.format(fechaEstimadaInicio) +
                "\nFecha estimada de finalización: " + formato.format(fechaEstimadaFinal) + (fechaInicio !=null ? "\nFecha de inicio: " + formato.format(fechaInicio) : "") +
               (fechaFinal !=null ? "\nFecha de finalización: " + formato.format(fechaFinal) : "") + "\n";              
    }

    public static void actualizarContadorID(List<Tarea> tareas) {
        int nuevoID = 1;
        for (Tarea tarea : tareas) {
            if (tarea.getId() > nuevoID) {
                nuevoID = tarea.getId();
            }
        }
        contadorID = nuevoID + 1;
    }
}

