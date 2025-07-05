package cl.duocuc.MicroServicioSucursal.servicio;


import cl.duocuc.MicroServicioSucursal.modelo.ModelInventario;
import cl.duocuc.MicroServicioSucursal.modelo.ModelProducto;
import cl.duocuc.MicroServicioSucursal.repositorio.InventarioRepository;
import cl.duocuc.MicroServicioSucursal.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServicio {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public ModelProducto crearProducto(Long id_inventario, ModelProducto producto) {
        ModelInventario inventario = inventarioRepository.findById(id_inventario)
                .orElseThrow(() -> new RuntimeException("Inventario no existe"));

        producto.setInventario(inventario); // 💡

        return productoRepository.save(producto);
    }

    public List<ModelProducto> obtenertodosproductos(){
        return productoRepository.findAll();
    }

    public ModelProducto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public ModelProducto actualizarProducto(Long id, ModelProducto producto) {
        ModelProducto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));


        productoExistente.setNombre(producto.getNombre());
        productoExistente.setPrecio(producto.getPrecio());


        return productoRepository.save(productoExistente);
    }

    public void eliminarProducto(Long id) {
        ModelProducto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoRepository.delete(producto);
    }
}
