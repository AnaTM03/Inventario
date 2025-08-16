package Sistema.Inventario.servicios.interfaces;

import Sistema.Inventario.modelos.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICategoriaService {
    Page<Categoria> buscarTodosPaginados(Pageable pageable);

    List<Categoria> obtenerTodo();

    Optional<Categoria> buscarPorId(Integer id);

    Categoria crearOEditar(Categoria categoria);

    void eliminarPorId(Integer id);
}
