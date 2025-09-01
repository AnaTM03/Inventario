package Sistema.Inventario.servicios.implementaciones;

import Sistema.Inventario.modelos.AlertaInventario;
import Sistema.Inventario.modelos.Producto;
import Sistema.Inventario.repositorios.IAlertaInventarioRepository;
import Sistema.Inventario.repositorios.IProductoRepository;
import Sistema.Inventario.servicios.interfaces.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductoService implements IProductoService {
    @Autowired
    private IProductoRepository productoRepository;
    @Autowired
    private IAlertaInventarioRepository alertaInventarioRepository;

    @Override
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Page<Producto> buscarTodosPaginados(Pageable pageable) {
        return productoRepository.findByOrderByNombreDesc(pageable);
    }

    @Override
    public Producto buscarPorId(Integer id) {
        return productoRepository.findById(id).get();
    }

    @Override
    public Producto crearOEditar(Producto producto) {
        Producto saved = productoRepository.save(producto);

        if (saved.getStockActual() < saved.getStockMinimo()) {
            // Verificar si ya existe una alerta pendiente
            boolean existeAlertaPendiente = !alertaInventarioRepository
                    .findByProductoAndEstado(saved, "PENDIENTE")
                    .isEmpty();

            if (!existeAlertaPendiente) {
                AlertaInventario alerta = new AlertaInventario();
                alerta.setProducto(saved);
                alerta.setFecha(new Date());
                alerta.setEstado("PENDIENTE");
                alertaInventarioRepository.save(alerta); // 👈 aquí se guarda
            }
        } else {
            // Resolver alertas pendientes si el stock se reabasteció
            List<AlertaInventario> pendientes =
                    alertaInventarioRepository.findByProductoAndEstado(saved, "PENDIENTE");

            pendientes.forEach(a -> {
                a.setEstado("RESUELTA");
                alertaInventarioRepository.save(a);
            });
        }

        return saved;
    }



    @Override
    public void eliminarPorId(Integer id) {
        productoRepository.deleteById(id);
    }
}
