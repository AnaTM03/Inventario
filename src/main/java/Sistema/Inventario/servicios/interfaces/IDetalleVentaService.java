package Sistema.Inventario.servicios.interfaces;

import Sistema.Inventario.modelos.DetalleVenta;
import Sistema.Inventario.modelos.Venta;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {

    List<DetalleVenta> obtenerTodos();

    Optional<DetalleVenta> buscarPorId(Integer id);

    List<DetalleVenta> buscarPorVenta(Venta venta);

    List<DetalleVenta> buscarPorVentaId(Integer ventaId);

    DetalleVenta crearOEditar(DetalleVenta detalleVenta);

    void eliminarPorId(Integer id);
}

