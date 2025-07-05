package cl.duocuc.MicroServicioSucursal.repositorio;

import cl.duocuc.MicroServicioSucursal.modelo.ModelSucursal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucursalRepository extends JpaRepository<ModelSucursal, Long> {

}
