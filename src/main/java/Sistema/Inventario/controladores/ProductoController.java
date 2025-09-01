package Sistema.Inventario.controladores;

import Sistema.Inventario.modelos.Categoria;
import Sistema.Inventario.modelos.Producto;
import Sistema.Inventario.modelos.Proveedor;
import Sistema.Inventario.servicios.interfaces.ICategoriaService;
import Sistema.Inventario.servicios.interfaces.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private IProductoService productoService;

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping()
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Producto> productos = productoService.buscarTodosPaginados(pageable);
        model.addAttribute("productos", productos);

        int totalPages = productos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "producto/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("producto", new Producto());  // Para el formulario
        model.addAttribute("categorias", categoriaService.obtenerTodo()); // Aquí envías las categorías
        return "producto/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Producto producto,
                       RedirectAttributes attributes) {

        if (producto.getCategoria() != null && producto.getCategoria().getId() != null) {
            Categoria categoria = categoriaService.buscarPorId(producto.getCategoria().getId()).orElse(null);

            if (categoria != null) {
                producto.setCategoria(categoria);
                productoService.crearOEditar(producto);
                attributes.addFlashAttribute("msg", "Registro creado correctamente ✅");
            } else {
                attributes.addFlashAttribute("error", "La categoría seleccionada no existe ⚠");
            }

        } else {
            attributes.addFlashAttribute("error", "Debe seleccionar una categoría ⚠");
        }

        return "redirect:/productos";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Producto producto = productoService.buscarPorId(id); // ya devuelve Producto
        model.addAttribute("producto", producto);
        return "producto/details";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Producto producto = productoService.buscarPorId(id); // ya es un Producto, no Optional
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.obtenerTodo()); // enviar categorías también
        return "producto/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoService.buscarPorId(id); // Obtenemos el producto
        model.addAttribute("producto", producto);
        return "producto/delete"; // Vista de confirmación para eliminar
    }

    @PostMapping("/delete")
    public String delete(Producto producto, RedirectAttributes attributes) {
        productoService.eliminarPorId(producto.getId()); // Eliminamos el producto por su ID
        attributes.addFlashAttribute("msg", "Registro eliminado correctamente ✅");
        return "redirect:/productos";
    }




}
