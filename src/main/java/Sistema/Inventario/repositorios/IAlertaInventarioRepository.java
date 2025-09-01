package Sistema.Inventario.repositorios;

import Sistema.Inventario.modelos.AlertaInventario;
import Sistema.Inventario.modelos.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAlertaInventarioRepository extends JpaRepository<AlertaInventario, Integer> {

    // Buscar todas las alertas de un producto específico
    List<AlertaInventario> findByProducto(Producto producto);

    // Buscar alertas por estado (ejemplo: "PENDIENTE", "RESUELTA")
    List<AlertaInventario> findByEstado(String estado);

    // Buscar alertas de un producto filtradas por estado
    List<AlertaInventario> findByProductoAndEstado(Producto producto, String estado);
}




