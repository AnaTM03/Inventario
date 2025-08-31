package Sistema.Inventario.servicios.interfaces;

import Sistema.Inventario.modelos.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProveedorService {
    Page<Proveedor> buscarTodosPaginados(Pageable pageable);

    List<Proveedor> obtenerTodo();

    Optional<Proveedor> buscarPorId(Integer id);

    Proveedor crearOEditar(Proveedor proveedor);

    void eliminarPorId(Integer id);
}
