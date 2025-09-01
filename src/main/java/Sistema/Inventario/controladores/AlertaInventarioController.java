package Sistema.Inventario.controladores;

import Sistema.Inventario.modelos.AlertaInventario;
import Sistema.Inventario.modelos.Producto;
import Sistema.Inventario.servicios.interfaces.IAlertaInventarioService;
import Sistema.Inventario.repositorios.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/alertas")
public class AlertaInventarioController {

    @Autowired
    private IAlertaInventarioService alertaInventarioService;

    @Autowired
    private IProductoRepository productoRepository;

    // === LISTADO DE ALERTAS ===
    @GetMapping
    public String index(Model model) {
        List<AlertaInventario> alertas = alertaInventarioService.obtenerTodos();
        model.addAttribute("alertas", alertas);
        return "alerta/index";
    }


    // === FORMULARIO DE CREACIÓN ===
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("alerta", new AlertaInventario());
        model.addAttribute("productos", productoRepository.findAll());
        return "alerta/create";
    }

    // === GUARDAR ALERTA ===
    @PostMapping("/save")
    public String save(@ModelAttribute AlertaInventario alerta,
                       RedirectAttributes attributes) {

        if (alerta.getProducto() != null && alerta.getProducto().getId() != null) {
            Producto producto = productoRepository.findById(alerta.getProducto().getId()).orElse(null);

            if (producto != null) {
                alerta.setProducto(producto);
                alertaInventarioService.crearOEditar(alerta);
                attributes.addFlashAttribute("msg", "Alerta registrada correctamente ✅");
            } else {
                attributes.addFlashAttribute("error", "El producto seleccionado no existe ⚠");
            }
        } else {
            attributes.addFlashAttribute("error", "Debe seleccionar un producto ⚠");
        }

        return "redirect:/alertas";
    }

    // === DETALLES DE UNA ALERTA ===
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        AlertaInventario alerta = alertaInventarioService.buscarPorId(id).orElse(null);

        if (alerta == null) {
            attributes.addFlashAttribute("error", "La alerta no existe ⚠");
            return "redirect:/alertas";
        }

        model.addAttribute("alerta", alerta);
        return "alerta/details";
    }

    // === EDITAR ALERTA ===
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        AlertaInventario alerta = alertaInventarioService.buscarPorId(id).orElse(null);

        if (alerta == null) {
            attributes.addFlashAttribute("error", "La alerta no existe ⚠");
            return "redirect:/alertas";
        }

        model.addAttribute("alerta", alerta);
        model.addAttribute("productos", productoRepository.findAll());
        return "alerta/edit";
    }

    // === ELIMINAR ALERTA (Confirmación) ===
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        AlertaInventario alerta = alertaInventarioService.buscarPorId(id).orElse(null);

        if (alerta == null) {
            attributes.addFlashAttribute("error", "La alerta no existe ⚠");
            return "redirect:/alertas";
        }

        model.addAttribute("alerta", alerta);
        return "alerta/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute AlertaInventario alerta, RedirectAttributes attributes) {
        alertaInventarioService.eliminarPorId(alerta.getId());
        attributes.addFlashAttribute("msg", "Alerta eliminada correctamente ✅");
        return "redirect:/alertas";
    }
}
