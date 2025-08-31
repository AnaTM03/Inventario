package Sistema.Inventario.servicios.implementaciones;

import Sistema.Inventario.modelos.Proveedor;
import Sistema.Inventario.repositorios.IProveedorRepository;
import Sistema.Inventario.servicios.interfaces.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService implements IProveedorService {
    @Autowired
    private IProveedorRepository proveedorRepository;

    @Override
    public Page<Proveedor> buscarTodosPaginados(Pageable pageable) {
        return proveedorRepository.findAll(pageable);
    }

    @Override
    public List<Proveedor> obtenerTodo() {
        return proveedorRepository.findAll();
    }

    @Override
    public Optional<Proveedor> buscarPorId(Integer id) {
        return proveedorRepository.findById(id);
    }

    @Override
    public Proveedor crearOEditar(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public void eliminarPorId(Integer id) {
        proveedorRepository.deleteById(id);
    }
}
