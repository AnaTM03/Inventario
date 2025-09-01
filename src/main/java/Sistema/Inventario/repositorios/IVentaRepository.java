package Sistema.Inventario.repositorios;

import Sistema.Inventario.modelos.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVentaRepository extends JpaRepository<Venta, Integer> {
    Page<Venta> findByOrderByFechaDesc(Pageable pageable);
}
