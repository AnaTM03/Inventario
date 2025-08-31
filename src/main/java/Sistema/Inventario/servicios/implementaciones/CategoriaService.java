package Sistema.Inventario.servicios.implementaciones;

import Sistema.Inventario.modelos.Categoria;
import Sistema.Inventario.repositorios.ICategoriaRepository;
import Sistema.Inventario.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService implements ICategoriaService {
    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Override
    public Page<Categoria> buscarTodosPaginados(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    @Override
    public List<Categoria> obtenerTodo() {
        return categoriaRepository.findAll();
    }

    @Override
    public Optional<Categoria> buscarPorId(Integer id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Categoria crearOEditar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void eliminarPorId(Integer id) {
        categoriaRepository.deleteById(id);
    }
}
