package cl.duocuc.MicroServicioSucursal.service;

import cl.duocuc.MicroServicioSucursal.modelo.ModelInventario;
import cl.duocuc.MicroServicioSucursal.modelo.ModelProducto;
import cl.duocuc.MicroServicioSucursal.repositorio.InventarioRepository;
import cl.duocuc.MicroServicioSucursal.repositorio.ProductoRepository;
import cl.duocuc.MicroServicioSucursal.servicio.ProductoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoServicioTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServicio productoServicio;

    private ModelProducto producto;
    private ModelInventario inventario;

    @BeforeEach
    void setUp() {
        inventario = new ModelInventario();
        inventario.setId(1L);
        inventario.setNombre("Inventario Principal");

        producto = new ModelProducto();
        producto.setId(1L);
        producto.setNombre("Producto Test");
        producto.setPrecio(10000);
        producto.setStock(50);
        producto.setInventario(inventario);
    }

    @Test
    void crearProducto_WhenInventarioExists_ShouldReturnProducto() {
        // Arrange
        when(inventarioRepository.findById(anyLong())).thenReturn(Optional.of(inventario));
        when(productoRepository.save(any(ModelProducto.class))).thenReturn(producto);

        // Act
        ModelProducto result = productoServicio.crearProducto(1L, producto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Producto Test", result.getNombre());
        assertEquals(inventario, result.getInventario());

        verify(inventarioRepository).findById(1L);
        verify(productoRepository).save(producto);
    }

    @Test
    void crearProducto_WhenInventarioNotExists_ShouldThrowException() {
        // Arrange
        when(inventarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoServicio.crearProducto(1L, producto);
        });

        assertEquals("Inventario no existe", exception.getMessage());
        verify(inventarioRepository).findById(1L);
        verify(productoRepository, never()).save(any());
    }

    @Test
    void obtenerTodosProductos_ShouldReturnList() {
        // Arrange
        ModelProducto producto2 = new ModelProducto();
        producto2.setId(2L);
        producto2.setNombre("Producto 2");

        List<ModelProducto> productos = Arrays.asList(producto, producto2);
        when(productoRepository.findAll()).thenReturn(productos);

        // Act
        List<ModelProducto> result = productoServicio.obtenertodosproductos();

        // Assert
        assertEquals(2, result.size());
        verify(productoRepository).findAll();
    }

    @Test
    void obtenerProductoPorId_WhenExists_ShouldReturnProducto() {
        // Arrange
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));

        // Act
        ModelProducto result = productoServicio.obtenerProductoPorId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productoRepository).findById(1L);
    }

    @Test
    void obtenerProductoPorId_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoServicio.obtenerProductoPorId(1L);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
        verify(productoRepository).findById(1L);
    }

    @Test
    void actualizarProducto_WhenExists_ShouldReturnUpdatedProducto() {
        // Arrange
        ModelProducto productoActualizado = new ModelProducto();
        productoActualizado.setNombre("Producto Actualizado");
        productoActualizado.setPrecio(15000);

        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(ModelProducto.class))).thenReturn(producto);

        // Act
        ModelProducto result = productoServicio.actualizarProducto(1L, productoActualizado);

        // Assert
        assertNotNull(result);
        assertEquals("Producto Actualizado", result.getNombre());
        assertEquals(15000, result.getPrecio());

        verify(productoRepository).findById(1L);
        verify(productoRepository).save(producto);
    }

    @Test
    void actualizarProducto_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoServicio.actualizarProducto(1L, producto);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
        verify(productoRepository).findById(1L);
        verify(productoRepository, never()).save(any());
    }

    @Test
    void eliminarProducto_WhenExists_ShouldDeleteProducto() {
        // Arrange
        when(productoRepository.findById(anyLong())).thenReturn(Optional.of(producto));
        doNothing().when(productoRepository).delete(any(ModelProducto.class));

        // Act
        productoServicio.eliminarProducto(1L);

        // Assert
        verify(productoRepository).findById(1L);
        verify(productoRepository).delete(producto);
    }

    @Test
    void eliminarProducto_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(productoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoServicio.eliminarProducto(1L);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
        verify(productoRepository).findById(1L);
        verify(productoRepository, never()).delete(any());
    }
}