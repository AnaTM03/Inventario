package Sistema.Inventario.servicios.implementaciones;

import Sistema.Inventario.modelos.Venta;
import Sistema.Inventario.repositorios.IVentaRepository;
import Sistema.Inventario.servicios.interfaces.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService implements IVentaService {
    @Autowired
    private IVentaRepository ventaRepository;

    @Override
    public List<Venta> obtenerTodos() {
        return ventaRepository.findAll();
    }

    @Override
    public Page<Venta> buscarTodosPaginados(Pageable pageable) {
        return ventaRepository.findByOrderByFechaDesc(pageable);
    }

    @Override
    public Venta buscarPorId(Integer id) {
        return ventaRepository.findById(id).get();
    }

    @Override
    public Venta crearOEditar(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Override
    public void eliminarPorId(Integer id) {
        ventaRepository.deleteById(id);
    }
}
