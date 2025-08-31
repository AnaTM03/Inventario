package Sistema.Inventario.servicios.interfaces;

import Sistema.Inventario.modelos.AlertaInventario;
import java.util.List;

public interface IAlertaInventarioService {
    List<AlertaInventario> generarAlertasPorStock();
    List<AlertaInventario> listarAlertas();
    AlertaInventario resolverAlerta(Integer id);
}


