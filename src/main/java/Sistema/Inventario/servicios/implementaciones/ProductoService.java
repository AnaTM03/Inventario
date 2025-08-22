package Sistema.Inventario.servicios.implementaciones;

import Sistema.Inventario.modelos.Producto;
import Sistema.Inventario.repositorios.IProductoRepository;
import Sistema.Inventario.servicios.interfaces.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService implements IProductoService {
    @Autowired
    private IProductoRepository productoRepository;
    @Override
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Page<Producto> buscarTodosPaginados(Pageable pageable) {
        return productoRepository.findByOrderByNombreDesc(pageable);
    }

    @Override
    public Producto buscarPorId(Integer id) {
        return productoRepository.findById(id).get();
    }

    @Override
    public Producto crearOEditar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void eliminarPorId(Integer id) {
        productoRepository.deleteById(id);
    }
}
