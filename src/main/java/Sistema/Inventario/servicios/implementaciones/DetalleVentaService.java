package Sistema.Inventario.servicios.implementaciones;

import Sistema.Inventario.modelos.DetalleVenta;
import Sistema.Inventario.modelos.Venta;
import Sistema.Inventario.repositorios.IDetalleVentaRepository;
import Sistema.Inventario.servicios.interfaces.IDetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaService implements IDetalleVentaService {

    @Autowired
    private IDetalleVentaRepository detalleVentaRepository;

    @Override
    public List<DetalleVenta> obtenerTodos() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public Optional<DetalleVenta> buscarPorId(Integer id) {
        return detalleVentaRepository.findById(id);
    }

    @Override
    public List<DetalleVenta> buscarPorVenta(Venta venta) {
        return detalleVentaRepository.findByVenta(venta);
    }

    @Override
    public List<DetalleVenta> buscarPorVentaId(Integer ventaId) {
        return detalleVentaRepository.findByVentaId(ventaId);
    }

    @Override
    public DetalleVenta crearOEditar(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public void eliminarPorId(Integer id) {
        detalleVentaRepository.deleteById(id);
    }
}
