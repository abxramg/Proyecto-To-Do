import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;



public class GestorUsuarios {

    // Lista principal de usuarios.
    private List<Usuario> usuarios;

    // Archivo donde se guardan los datos.
    private final String archivo = "usuarios.ser";
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    // Inicializa la lista e intenta cargar los usuarios desde el archivo.
    public GestorUsuarios() {
        this.usuarios = new ArrayList<>();
        cargarUsuarios();
    }

    public Usuario autenticar(String identificador, String contrasena) {
        Usuario u = buscarPorCorreoONick(identificador);

        if ( u != null && u.getContrasena().equals(contrasena)) {
            return u;
        }
        return null;
    }

    public void agregarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        guardarUsuarios();
    }

    public Usuario buscarPorCorreoONick (String correoONick) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correoONick) || u.getNickname().equalsIgnoreCase(correoONick)) {
                return u;
            }
        }
            return null;
    }
        
    private void guardarUsuarios() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
            out.writeObject(usuarios);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarUsuarios() {
        File f = new File(archivo);
        if (f.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
                usuarios = (List<Usuario>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error al cargar usuarios: " + e.getMessage());
            }
        }
    }

}
