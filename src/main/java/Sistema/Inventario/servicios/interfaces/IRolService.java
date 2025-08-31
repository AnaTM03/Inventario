package Sistema.Inventario.servicios.interfaces;

import Sistema.Inventario.modelos.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRolService {
    Page<Rol> buscarTodosPaginados(Pageable pageable);

    List<Rol> obtenerTodo();

    Optional<Rol> buscarPorId(Integer id);

    Rol crearOEditar(Rol rol);

    void eliminarPorId(Integer id);
}
