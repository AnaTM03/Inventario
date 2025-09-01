package Sistema.Inventario.controladores;

import Sistema.Inventario.modelos.*;
import Sistema.Inventario.repositorios.IClienteRepository;
import Sistema.Inventario.repositorios.IVentaRepository;
import Sistema.Inventario.repositorios.UsuarioRepository;
import Sistema.Inventario.servicios.implementaciones.ClienteService;
import Sistema.Inventario.servicios.interfaces.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("ventas")
public class VentaController {

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1; // en Spring Data el índice empieza en 0
        int pageSize = size.orElse(5); // tamaño de página por defecto

        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("fecha").descending());

        Page<Venta> ventas = ventaRepository.findAll(pageable);
        model.addAttribute("ventas", ventas);

        int totalPages = ventas.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "venta/index"; // la vista
    }


    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("venta", new Venta());  // objeto vacío para el form

        // Enviar listas para los select del formulario
        model.addAttribute("usuarios", usuarioRepository.findAll());
        model.addAttribute("clientes", clienteRepository.findAll());

        return "venta/create"; // vista del formulario
    }


    @PostMapping("/save")
    public String save(@ModelAttribute Venta venta,
                       RedirectAttributes attributes) {

        if (venta.getUsuario() != null && venta.getUsuario().getId() != null &&
                venta.getCliente() != null && venta.getCliente().getId() != null) {

            Usuario usuario = usuarioRepository.findById(venta.getUsuario().getId()).orElse(null);
            Cliente cliente = clienteRepository.findById(venta.getCliente().getId()).orElse(null);

            if (usuario != null && cliente != null) {
                venta.setUsuario(usuario);
                venta.setCliente(cliente);

                ventaRepository.save(venta);

                attributes.addFlashAttribute("msg", "Venta registrada correctamente ✅");
            } else {
                attributes.addFlashAttribute("error", "El usuario o cliente seleccionado no existe ⚠");
            }

        } else {
            attributes.addFlashAttribute("error", "Debe seleccionar un usuario y un cliente ⚠");
        }

        return "redirect:/ventas";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Venta venta = ventaRepository.findById(id).orElse(null);

        if (venta == null) {
            // Manejar el caso en que no se encuentre la venta
            model.addAttribute("error", "La venta no existe ⚠");
            return "redirect:/ventas";
        }

        model.addAttribute("venta", venta);
        return "venta/details"; // Vista de detalles de la venta
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Venta venta = ventaRepository.findById(id).orElse(null);

        if (venta == null) {
            model.addAttribute("error", "La venta no existe ⚠");
            return "redirect:/ventas";
        }

        model.addAttribute("venta", venta);
        model.addAttribute("usuarios", usuarioRepository.findAll());  // para el select
        model.addAttribute("clientes", clienteRepository.findAll());  // para el select
        return "venta/edit"; // Vista de edición
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Venta venta = ventaRepository.findById(id).orElse(null);

        if (venta == null) {
            model.addAttribute("error", "La venta no existe ⚠");
            return "redirect:/ventas";
        }

        model.addAttribute("venta", venta);
        return "venta/delete"; // Vista de confirmación
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Venta venta, RedirectAttributes attributes) {
        ventaRepository.deleteById(venta.getId());
        attributes.addFlashAttribute("msg", "Venta eliminada correctamente ✅");
        return "redirect:/ventas";
    }


}


