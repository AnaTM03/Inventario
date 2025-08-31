package Sistema.Inventario.servicios.implementaciones;

import Sistema.Inventario.modelos.AlertaInventario;
import Sistema.Inventario.modelos.Producto;
import Sistema.Inventario.repositorios.IAlertaInventarioRepository;
import Sistema.Inventario.repositorios.IProductoRepository;
import Sistema.Inventario.servicios.interfaces.IAlertaInventarioService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertaInventarioService implements IAlertaInventarioService {

    private final IAlertaInventarioRepository alertaRepo;
    private final IProductoRepository productoRepo;

    public AlertaInventarioService(IAlertaInventarioRepository alertaRepo, IProductoRepository productoRepo) {
        this.alertaRepo = alertaRepo;
        this.productoRepo = productoRepo;
    }

    @Override
    public List<AlertaInventario> generarAlertasPorStock() {
        List<Producto> productosBajos = productoRepo.findAll()
                .stream()
                .filter(p -> p.getStockActual() < p.getStockMinimo())
                .toList();

        return productosBajos.stream()
                .map(prod -> {
                    AlertaInventario alerta = new AlertaInventario();
                    alerta.setProducto(prod);
                    alerta.setFechaAlerta(LocalDateTime.now());
                    alerta.setEstado("Pendiente");
                    return alertaRepo.save(alerta);
                }).toList();
    }

    @Override
    public List<AlertaInventario> listarAlertas() {
        return alertaRepo.findAll();
    }

    @Override
    public AlertaInventario resolverAlerta(Integer id) {
        return alertaRepo.findById(id).map(alerta -> {
            alerta.setEstado("Resuelta");
            return alertaRepo.save(alerta);
        }).orElse(null);
    }
}



