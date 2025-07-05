package cl.duocuc.MicroServicioSucursal.controller;

import cl.duocuc.MicroServicioSucursal.controlador.ProductoControlador;
import cl.duocuc.MicroServicioSucursal.modelo.ModelProducto;
import cl.duocuc.MicroServicioSucursal.servicio.ProductoServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ProductoControladorTest {

    @Mock
    private ProductoServicio productoServicio;

    @InjectMocks
    private ProductoControlador productoControlador;

    private ModelProducto producto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        producto = new ModelProducto();
        producto.setId(1L);
        producto.setNombre("Producto Test");
    }

    @Test
    public void crearProducto_ShouldReturnCreated() {
        when(productoServicio.crearProducto(anyLong(), any(ModelProducto.class))).thenReturn(producto);

        ResponseEntity<ModelProducto> response = productoControlador.crearProducto(1L, producto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Producto Test", response.getBody().getNombre());
    }

    @Test
    public void crearProducto_WhenServiceThrowsException_ShouldReturnInternalServerError() {
        when(productoServicio.crearProducto(anyLong(), any(ModelProducto.class)))
                .thenThrow(new RuntimeException());

        ResponseEntity<ModelProducto> response = productoControlador.crearProducto(1L, producto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void obtenerTodosProductos_ShouldReturnOk() {
        ModelProducto producto2 = new ModelProducto();
        producto2.setId(2L);
        producto2.setNombre("Producto 2");

        List<ModelProducto> productos = Arrays.asList(producto, producto2);

        when(productoServicio.obtenertodosproductos()).thenReturn(productos);

        ResponseEntity<List<ModelProducto>> response = productoControlador.obtenerTodosProductos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Producto Test", response.getBody().get(0).getNombre());
    }

    @Test
    public void obtenerTodosProductos_WhenNoProducts_ShouldReturnNotFound() {
        when(productoServicio.obtenertodosproductos()).thenThrow(new RuntimeException());

        ResponseEntity<List<ModelProducto>> response = productoControlador.obtenerTodosProductos();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }
}