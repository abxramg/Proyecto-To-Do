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

    /**
     * Obtiene el identificador único de la tarea.
     * @return ID de la tarea
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la descripción de la tarea.
     * @return Descripción de la tarea
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene el estado actual de la tarea.
     * @return Estado de la tarea
     */
    public Estado getEstado() {
        return estado;
    }

     /**
     * Obtiene el usuario al que está asignada la tarea.
     * @return Usuario asignado
     */
    public Usuario getAsignacion() {
        return asignacion;
    }

    /**
     * Obtiene la fecha estimada de inicio de la tarea.
     * @return Fecha estimada de inicio
     */
    public LocalDate getFechaEstimadaInicio() {
        return fechaEstimadaInicio;
    }

    /**
     * Obtiene la fecha real de inicio de la tarea.
     * @return Fecha real de inicio
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Obtiene la fecha estimada de finalización de la tarea.
     * @return Fecha estimada de finalización
     */
    public LocalDate getFechaEstimadaFinal() {
        return fechaEstimadaFinal;
    }

    /**
     * Obtiene la fecha real de finalización de la tarea.
     * @return Fecha real de finalización
     */
    public LocalDate getFechaFinal() {
        return fechaFinal;
    }


    // Setters con validación

    /** 
     * Permite cambiar la descripción solo si la tarea está en estado PENDIENTE. 
     */

    public void setDescripcion(String descripcion) {
        if (estado != Estado.PENDIENTE) {
            System.out.println("No se puede cambiar la descripción una vez iniciada.");
            return;
        }

        this.descripcion = descripcion;
        System.out.println("Descripción actualizada.");
    }

    /**
     * Establece una nueva fecha estimada de inicio, si la tarea está pendiente.
     * @param fecha Nueva fecha estimada de inicio
     * @throws IllegalArgumentException Si la fecha es inválida
     */
    public void setFechaEstimadaInicio(LocalDate fecha) {
        if (estado != Estado.PENDIENTE) {
            System.out.println("No se puede modificar la fecha estimada de inicio una vez iniciada.");
            return;
        }

        if (fecha.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha estimada de inicio no puede estar en el pasado.");
        }

        if (fecha.isAfter(fechaEstimadaFinal)) {
            throw new IllegalArgumentException("La fecha estimada de inicio no puede ser posterior a la fecha estimada de finalización.");
        }

        this.fechaEstimadaInicio = fecha;
    }

    /**
     * Establece una nueva fecha estimada de finalización, si la tarea está pendiente.
     * @param fecha Nueva fecha estimada de finalización
     * @throws IllegalArgumentException Si la fecha es inválida
     */
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

    /**
     * Reasigna la tarea a un nuevo usuario, si la tarea está pendiente.
     * @param nuevoUsuario Nuevo usuario asignado
     */
    public void setAsignacion(Usuario nuevoUsuario) {
        if (estado != Estado.PENDIENTE) {
            System.out.println("No se puede reasignar una tarea ya en curso/completada.");
            return;
        }
        this.asignacion = nuevoUsuario;
    }

    /**
     * Devuelve una representación en texto de la tarea, incluyendo fechas, estado y asignación.
     * @return Cadena con los datos de la tarea
     */
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

    /**
     * Actualiza el contador estático de ID para asegurar que no se repitan los identificadores al cargar tareas desde almacenamiento.
     * @param tareas Lista de tareas cargadas desde almacenamiento
     */
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

