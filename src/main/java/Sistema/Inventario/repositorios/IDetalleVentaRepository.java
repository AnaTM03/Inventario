package Sistema.Inventario.repositorios;

import Sistema.Inventario.modelos.DetalleVenta;
import Sistema.Inventario.modelos.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {

    // Buscar todos los detalles de una venta específica
    List<DetalleVenta> findByVenta(Venta venta);

    // O también: usando el ID de la venta
    List<DetalleVenta> findByVentaId(Integer ventaId);
}

