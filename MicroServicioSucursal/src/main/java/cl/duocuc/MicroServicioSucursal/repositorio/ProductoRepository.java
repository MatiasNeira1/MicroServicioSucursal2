package cl.duocuc.MicroServicioSucursal.repositorio;

import cl.duocuc.MicroServicioSucursal.modelo.ModelProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ModelProducto, Long> {


}
