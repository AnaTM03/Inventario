package Sistema.Inventario.repositorios;

import Sistema.Inventario.modelos.AlertaInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IAlertaInventarioRepository extends JpaRepository<AlertaInventario, Integer> {
    List<AlertaInventario> findByEstado(String estado);
}



