package cl.duocuc.MicroServicioSucursal.controlador;


import cl.duocuc.MicroServicioSucursal.modelo.ModelInventario;
import cl.duocuc.MicroServicioSucursal.modelo.ModelSucursal;
import cl.duocuc.MicroServicioSucursal.servicio.InventarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/inventarios")
public class InventarioControlador {

    @Autowired
    private InventarioServicio inventarioservice;


    @PostMapping("/crear")
    public ResponseEntity<ModelInventario> createInventario(@RequestBody ModelInventario inventario) {
        try{
           ModelInventario inventario_creado = inventarioservice.createInventario(inventario);
            return ResponseEntity.status(HttpStatus.CREATED).body(inventario_creado);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ModelInventario>>obtenerTodosLosInventarios() {
        try {
           List<ModelInventario> inventario = inventarioservice.obtenerTodosLosInventarios();
            return ResponseEntity.status(HttpStatus.OK).body(inventario);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/inventario/{id}")
    public ResponseEntity<ModelInventario> obtenerInventarioPorId(@PathVariable Long id) {
        try {
           Optional <ModelInventario> inventario = inventarioservice.obtenerInventarioPorId(id);{
                if (inventario.isPresent()) {
                    return ResponseEntity.status(HttpStatus.OK).body(inventario.get());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void>  eliminarInventario(@PathVariable Long id) {
        try {

            Optional<ModelInventario> inventarioEncontradd = inventarioservice.obtenerInventarioPorId(id);
            if (inventarioEncontradd.isPresent()) {
                inventarioservice.eliminarInventario(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ModelInventario> actualizarInventario(@PathVariable Long id, @RequestBody ModelInventario inventarioActualizado) {
        try {
            ModelInventario inventario = inventarioservice.actualizarInventario(id, inventarioActualizado);
            return ResponseEntity.status(HttpStatus.OK).body(inventario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}