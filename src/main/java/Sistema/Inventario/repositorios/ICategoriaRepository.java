package Sistema.Inventario.repositorios;

import Sistema.Inventario.modelos.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<Categoria, Integer> {
}
