package Sistema.Inventario.controladores;

import Sistema.Inventario.modelos.AlertaInventario;
import Sistema.Inventario.servicios.interfaces.IAlertaInventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas")
public class AlertaInventarioController {

    private final IAlertaInventarioService alertaService;

    public AlertaInventarioController(IAlertaInventarioService alertaService) {
        this.alertaService = alertaService;
    }

    // Generar alertas de productos con bajo stock
    @PostMapping("/generar")
    public List<AlertaInventario> generarAlertas() {
        return alertaService.generarAlertasPorStock();
    }

    // Listar todas las alertas
    @GetMapping
    public List<AlertaInventario> listarAlertas() {
        return alertaService.listarAlertas();
    }

    // Resolver alerta por id
    @PutMapping("/{id}/resolver")
    public AlertaInventario resolverAlerta(@PathVariable Integer id) {
        return alertaService.resolverAlerta(id);
    }
}


