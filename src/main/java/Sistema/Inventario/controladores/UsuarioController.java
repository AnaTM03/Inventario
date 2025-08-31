package Sistema.Inventario.controladores;

import Sistema.Inventario.modelos.Usuario;
import Sistema.Inventario.modelos.Rol;  // 👈 IMPORTANTE
import Sistema.Inventario.repositorios.UsuarioRepository;
import Sistema.Inventario.repositorios.IRolRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final IRolRepository rolRepository;

    // 👇 Constructor con inyección de repositorios
    public UsuarioController(UsuarioRepository usuarioRepository, IRolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    // 👉 Listado de usuarios
    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuario/listar"; // templates/usuario/listar.html
    }

    // 👉 Formulario para nuevo usuario
    @GetMapping("/usuarios/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolRepository.findAll()); // 🔹 enviamos la lista de roles
        return "usuario/form"; // templates/usuario/form.html
    }

    // 👉 Guardar usuario (crear o actualizar)
    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario) {
        // Buscar el rol desde la BD por su id
        Rol rolSeleccionado = rolRepository.findById(usuario.getRol().getId())
                .orElseThrow(() -> new IllegalArgumentException("Rol inválido"));

        usuario.setRol(rolSeleccionado);
        usuarioRepository.save(usuario);
        return "redirect:/usuarios";
    }

    // 👉 Editar usuario
    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolRepository.findAll());
        return "usuario/form"; // Reutilizamos el mismo formulario
    }

    // 👉 Ver detalles del usuario
    @GetMapping("/usuarios/detalles/{id}")
    public String detallesUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));
        model.addAttribute("usuario", usuario);
        return "usuario/detalles"; // templates/usuario/detalles.html
    }

    // 👉 Eliminar usuario
    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios";
    }
}


