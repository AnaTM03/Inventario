package Sistema.Inventario.servicios.interfaces;

import Sistema.Inventario.modelos.AlertaInventario;
import Sistema.Inventario.modelos.Producto;

import java.util.List;
import java.util.Optional;

public interface IAlertaInventarioService {

    List<AlertaInventario> obtenerTodos();

    Optional<AlertaInventario> buscarPorId(Integer id);

    List<AlertaInventario> buscarPorProducto(Producto producto);

    List<AlertaInventario> buscarPorEstado(String estado);

    List<AlertaInventario> buscarPorProductoYEstado(Producto producto, String estado);

    AlertaInventario crearOEditar(AlertaInventario alerta);

    void eliminarPorId(Integer id);
}


