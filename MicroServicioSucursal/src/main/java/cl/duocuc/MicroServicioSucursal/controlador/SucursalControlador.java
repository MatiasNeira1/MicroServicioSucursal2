package cl.duocuc.MicroServicioSucursal.controlador;

import cl.duocuc.MicroServicioSucursal.modelo.ModelSucursal;
import cl.duocuc.MicroServicioSucursal.repositorio.SucursalRepository;
import cl.duocuc.MicroServicioSucursal.servicio.SucursalServicio;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sucursales")

public class SucursalControlador {

    @Autowired
    private SucursalServicio sucursalservicio;

   @PostMapping("/crear")
    public ResponseEntity<ModelSucursal> createSucursal(@RequestBody ModelSucursal sucursal){

       try{
           ModelSucursal sucursal_creada=sucursalservicio.createSucursal(sucursal);
           return ResponseEntity.status(HttpStatus.CREATED).body(sucursal_creada);

       }catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

       }

   }
    @GetMapping("/todas")
    public ResponseEntity<List<ModelSucursal>>todasSucursales(){
       try{
          List<ModelSucursal> sucursales_encontradas=sucursalservicio.getAll();
          return ResponseEntity.status(HttpStatus.FOUND).body(sucursales_encontradas);

       }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }

    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelSucursal> SucursalById(@PathVariable Long id) {
        try {
            Optional<ModelSucursal> sucursalEncontrada = sucursalservicio.findbyId(id);
            if (sucursalEncontrada.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(sucursalEncontrada.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteSucursalById(@PathVariable Long id) {
        try {
            Optional<ModelSucursal> sucursalEncontrada = sucursalservicio.findbyId(id);
            if (sucursalEncontrada.isPresent()) {
                sucursalservicio.deleteSucursal(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ModelSucursal> updateSucursal(@PathVariable Long id, @RequestBody ModelSucursal sucursal){

       try{
           ModelSucursal sucursal_updated=sucursalservicio.updateSucursal(id, sucursal);
           return ResponseEntity.status(HttpStatus.OK).body(sucursal_updated);

       }catch(Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

       }

    }

}



