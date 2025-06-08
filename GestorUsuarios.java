import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de gestionar la lista de usuarios del sistema.
 * Permite agregar, buscar, autenticar usuarios, y guardar/cargar los datos de forma persistente mediante serialización.
 */
public class GestorUsuarios {

    // Lista principal que almacena los usuarios registrados en el sistema.
    private List<Usuario> usuarios;

    // Ruta del archivo donde se serializa y guarda la lista de usuarios.
    private final String archivo = "usuarios.ser";

    /**
     * Método público para acceder a la lista de usuarios.
     * @return Lista actual de usuarios.
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Constructor de la clase. Inicializa la lista de usuarios e intenta cargar los usuarios 
     * previamente guardados desde el archivo.
     */
    public GestorUsuarios() {
        this.usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    /**
     * Autentica a un usuario mediante su correo o nickname y contraseña.
     *
     * @param identificador Puede ser correo electrónico o nickname.
     * @param contrasena Contraseña ingresada por el usuario.
     * @return Usuario autenticado si las credenciales son válidas, de lo contrario null.
     */
    public Usuario autenticar(String identificador, String contrasena) {
        Usuario u = buscarPorCorreoONick(identificador);

        if ( u != null && u.getContrasena().equals(contrasena)) {
            return u;
        }
        return null;
    }

    /**
     * Agrega un nuevo usuario a la lista y guarda la lista actualizada en el archivo.
     * @param usuario Usuario a agregar.
     */
    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        guardarUsuarios();
    }

    /**
     * Busca un usuario por su correo electrónico o nickname.
     *
     * @param correoONick Cadena que representa el correo o nickname a buscar.
     * @return El usuario encontrado, o null si no existe.
     */
    public Usuario buscarPorCorreoONick (String correoONick) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correoONick) || u.getNickname().equalsIgnoreCase(correoONick)) {
                return u;
            }
        }
        return null;
    }
        
    /**
     * Guarda la lista actual de usuarios en un archivo usando serialización.
     * Si ocurre un error, se muestra un mensaje en consola.
     */
    private void guardarUsuarios() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(usuarios);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    /**
     * Carga la lista de usuarios desde un archivo serializado, si existe.
     * Si ocurre un error al leer el archivo, se muestra un mensaje en consola.
     */
    @SuppressWarnings("unchecked")
    private void cargarUsuarios() {
        File f = new File(archivo);
        if (f.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
                usuarios = (List<Usuario>) in.readObject();     // Deserializa la lista
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error al cargar usuarios: " + e.getMessage());
            }
        }
    }

}
