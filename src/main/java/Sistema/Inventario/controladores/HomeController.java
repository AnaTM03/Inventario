package Sistema.Inventario.controladores;

import Sistema.Inventario.modelos.AlertaInventario;
import Sistema.Inventario.repositorios.IAlertaInventarioRepository;
import Sistema.Inventario.repositorios.IClienteRepository;
import Sistema.Inventario.repositorios.IVentaRepository;
import Sistema.Inventario.repositorios.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IAlertaInventarioRepository alertaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @RequestMapping
    public String index(Model model) {
        // Total de ventas
        long totalVentas = ventaRepository.count();

        // Productos con bajo stock
        long productosBajoStock = productoRepository.findAll().stream()
                .filter(p -> p.getStockActual() < p.getStockMinimo())
                .count();

        // Alertas pendientes
        long alertasPendientes = alertaRepository.findByEstado("PENDIENTE").size();

        // Total clientes
        long totalClientes = clienteRepository.count();

        // Últimas 5 alertas
        List<AlertaInventario> ultimasAlertas = alertaRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getFecha().compareTo(a.getFecha()))
                .limit(5)
                .toList();

        // Pasar datos al modelo
        model.addAttribute("totalVentas", totalVentas);
        model.addAttribute("productosBajoStock", productosBajoStock);
        model.addAttribute("alertasPendientes", alertasPendientes);
        model.addAttribute("totalClientes", totalClientes);
        model.addAttribute("ultimasAlertas", ultimasAlertas);

        return "home/index";
    }
}