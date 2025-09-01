package Sistema.Inventario.servicios.implementaciones;

import Sistema.Inventario.modelos.AlertaInventario;
import Sistema.Inventario.modelos.Producto;
import Sistema.Inventario.repositorios.IAlertaInventarioRepository;
import Sistema.Inventario.servicios.interfaces.IAlertaInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertaInventarioService implements IAlertaInventarioService {

    @Autowired
    private IAlertaInventarioRepository alertaInventarioRepository;

    @Override
    public List<AlertaInventario> obtenerTodos() {
        return alertaInventarioRepository.findAll();
    }

    @Override
    public Optional<AlertaInventario> buscarPorId(Integer id) {
        return alertaInventarioRepository.findById(id);
    }

    @Override
    public List<AlertaInventario> buscarPorProducto(Producto producto) {
        return alertaInventarioRepository.findByProducto(producto);
    }

    @Override
    public List<AlertaInventario> buscarPorEstado(String estado) {
        return alertaInventarioRepository.findByEstado(estado);
    }

    @Override
    public List<AlertaInventario> buscarPorProductoYEstado(Producto producto, String estado) {
        return alertaInventarioRepository.findByProductoAndEstado(producto, estado);
    }

    @Override
    public AlertaInventario crearOEditar(AlertaInventario alerta) {
        return alertaInventarioRepository.save(alerta);
    }

    @Override
    public void eliminarPorId(Integer id) {
        alertaInventarioRepository.deleteById(id);
    }
}



