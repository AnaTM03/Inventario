package Sistema.Inventario.repositorios;

import Sistema.Inventario.modelos.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProveedorRepository extends JpaRepository<Proveedor, Integer> {
}
