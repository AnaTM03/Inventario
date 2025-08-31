package Sistema.Inventario.repositorios;

import Sistema.Inventario.modelos.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {
    Page<Producto> findByOrderByNombreDesc(Pageable pageable);
}
