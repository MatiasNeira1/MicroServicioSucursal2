package cl.duocuc.MicroServicioSucursal.servicio;

import cl.duocuc.MicroServicioSucursal.modelo.ModelInventario;
import cl.duocuc.MicroServicioSucursal.modelo.ModelSucursal;
import cl.duocuc.MicroServicioSucursal.repositorio.InventarioRepository;
import cl.duocuc.MicroServicioSucursal.repositorio.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioServicio {

    @Autowired
    InventarioRepository inventariorepository;
    @Autowired
    private SucursalRepository sucursalRepository;

    public ModelInventario createInventario(ModelInventario inventario) {
        Long idSucursal = inventario.getSucursal().getId();

        ModelSucursal sucursal = sucursalRepository.findById(idSucursal)
                .orElseThrow(() -> new RuntimeException("La sucursal no existe"));

        inventario.setSucursal(sucursal); //

        return inventariorepository.save(inventario);
    }


    //public ResponseEntity<ModelInventario> obtenerInventario(@PathVariable Long id) {
       // ModelInventario inventario = inventariorepository.findById(id)
         //       .orElseThrow(() -> new RuntimeException("No encontrado"));

        //return ResponseEntity.ok(inventario);
    //}

    public List<ModelInventario>obtenerTodosLosInventarios() {
        return inventariorepository.findAll();
    }


    public Optional <ModelInventario> obtenerInventarioPorId(Long id) {
        return inventariorepository.findById(id);

    }

    public List<ModelInventario> eliminarInventario(Long id) {
        if (inventariorepository.existsById(id)) {
            inventariorepository.deleteById(id);
        }
        return inventariorepository.findAll();
    }

    public ModelInventario actualizarInventario(Long id, ModelInventario inventarioActualizado) {
        return inventariorepository.findById(id).map(inventario -> {
            inventario.setSucursal(inventarioActualizado.getSucursal());
            inventario.setNombre(inventarioActualizado.getNombre());
            inventario.setDescripcion(inventarioActualizado.getDescripcion());
            return inventariorepository.save(inventario);
        }).orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    }






}
