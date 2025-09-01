package Sistema.Inventario.controladores;

import Sistema.Inventario.modelos.DetalleVenta;
import Sistema.Inventario.modelos.Producto;
import Sistema.Inventario.modelos.Venta;
import Sistema.Inventario.servicios.interfaces.IDetalleVentaService;
import Sistema.Inventario.repositorios.IVentaRepository;
import Sistema.Inventario.repositorios.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/detalles-venta")
public class DetalleVentaController {

    @Autowired
    private IDetalleVentaService detalleVentaService;

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private IProductoRepository productoRepository;

    // === LISTADO DE TODOS LOS DETALLES ===
    @GetMapping
    public String index(Model model) {
        List<DetalleVenta> detalles = detalleVentaService.obtenerTodos();
        model.addAttribute("detalles", detalles);
        return "detalleventa/index";
    }

    // === FORMULARIO DE CREACIÓN ===
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("detalleVenta", new DetalleVenta());
        model.addAttribute("ventas", ventaRepository.findAll());
        model.addAttribute("productos", productoRepository.findAll());
        return "detalleventa/create";
    }

    // === GUARDAR NUEVO DETALLE ===
    @PostMapping("/save")
    public String save(@ModelAttribute DetalleVenta detalleVenta,
                       RedirectAttributes attributes) {

        if (detalleVenta.getVenta() != null && detalleVenta.getProducto() != null) {
            detalleVenta.setSubtotal(detalleVenta.getPrecioUnitario());
            detalleVentaService.crearOEditar(detalleVenta);
            attributes.addFlashAttribute("msg", "Detalle de venta registrado correctamente ✅");
        } else {
            attributes.addFlashAttribute("error", "Debe seleccionar una venta y un producto ⚠");
        }

        return "redirect:/detalles-venta";
    }

    // === EDITAR DETALLE ===
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        DetalleVenta detalle = detalleVentaService.buscarPorId(id).orElse(null);

        if (detalle == null) {
            attributes.addFlashAttribute("error", "El detalle de venta no existe ⚠");
            return "redirect:/detalles-venta";
        }

        model.addAttribute("detalleVenta", detalle);
        model.addAttribute("ventas", ventaRepository.findAll());
        model.addAttribute("productos", productoRepository.findAll());
        return "detalleventa/edit";
    }

    // === ELIMINAR DETALLE (confirmación) ===
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        DetalleVenta detalle = detalleVentaService.buscarPorId(id).orElse(null);

        if (detalle == null) {
            attributes.addFlashAttribute("error", "El detalle de venta no existe ⚠");
            return "redirect:/detalles-venta";
        }

        model.addAttribute("detalleVenta", detalle);
        return "detalleventa/delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute DetalleVenta detalleVenta, RedirectAttributes attributes) {
        detalleVentaService.eliminarPorId(detalleVenta.getId());
        attributes.addFlashAttribute("msg", "Detalle eliminado correctamente ✅");
        return "redirect:/detalles-venta";
    }

    // === DETALLE DE UN REGISTRO ===
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model, RedirectAttributes attributes) {
        DetalleVenta detalle = detalleVentaService.buscarPorId(id).orElse(null);

        if (detalle == null) {
            attributes.addFlashAttribute("error", "El detalle de venta no existe ⚠");
            return "redirect:/detalles-venta";
        }

        model.addAttribute("detalleVenta", detalle);
        return "detalleventa/details";
    }
}
