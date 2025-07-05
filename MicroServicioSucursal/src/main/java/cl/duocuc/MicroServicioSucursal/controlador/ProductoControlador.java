package cl.duocuc.MicroServicioSucursal.controlador;


import cl.duocuc.MicroServicioSucursal.modelo.ModelProducto;
import cl.duocuc.MicroServicioSucursal.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @PostMapping("/crear/{id_inv}")
    public ResponseEntity<ModelProducto> crearProducto(@PathVariable Long id_inv, @RequestBody ModelProducto producto) {
        try {
            ModelProducto productoCreado = productoServicio.crearProducto(id_inv, producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ModelProducto>> obtenerTodosProductos() {
        try {
            List<ModelProducto> productos = productoServicio.obtenertodosproductos();
            return ResponseEntity.status(HttpStatus.OK).body(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelProducto> obtenerProductoPorId(@PathVariable Long id) {
        try {
            ModelProducto producto = productoServicio.obtenerProductoPorId(id);
            if (producto != null) {
                return ResponseEntity.status(HttpStatus.OK).body(producto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ModelProducto> actualizarProducto(@PathVariable Long id, @RequestBody ModelProducto producto) {
        try {
            ModelProducto productoActualizado = productoServicio.actualizarProducto(id, producto);
            return ResponseEntity.status(HttpStatus.OK).body(productoActualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        try {
            productoServicio.eliminarProducto(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
}


}
