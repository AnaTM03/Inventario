package Sistema.Inventario.servicios.interfaces;

import Sistema.Inventario.modelos.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;


public interface IProductoService {
    List<Producto> obtenerTodos();

    Page<Producto> buscarTodosPaginados(Pageable pageable);

    Producto buscarPorId(Integer id);

    Producto crearOEditar(Producto producto);

    void eliminarPorId(Integer id);
}
