package Sistema.Inventario.servicios.interfaces;

import Sistema.Inventario.modelos.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVentaService {
    List<Venta> obtenerTodos();

    Page<Venta> buscarTodosPaginados(Pageable pageable);

    Venta buscarPorId(Integer id);

    Venta crearOEditar(Venta venta);

    void eliminarPorId(Integer id);
}
