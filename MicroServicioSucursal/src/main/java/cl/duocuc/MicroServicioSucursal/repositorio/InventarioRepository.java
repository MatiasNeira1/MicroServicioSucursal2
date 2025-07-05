package cl.duocuc.MicroServicioSucursal.repositorio;

import cl.duocuc.MicroServicioSucursal.modelo.ModelInventario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventarioRepository extends JpaRepository<ModelInventario, Long> {

    @EntityGraph(attributePaths = {"sucursal"})
    Optional<ModelInventario> findById(Long id);
}
