package cl.duocuc.MicroServicioSucursal.controller;

import cl.duocuc.MicroServicioSucursal.controlador.InventarioControlador;
import cl.duocuc.MicroServicioSucursal.modelo.ModelInventario;
import cl.duocuc.MicroServicioSucursal.modelo.ModelSucursal;
import cl.duocuc.MicroServicioSucursal.servicio.InventarioServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventarioControladorTest {

    @Mock
    private InventarioServicio inventarioServicio;

    @InjectMocks
    private InventarioControlador inventarioControlador;

    private ModelInventario inventario;
    private ModelSucursal sucursal;

    @BeforeEach
    void setUp() {
        // Configuración inicial para cada test
        sucursal = new ModelSucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal Principal");

        inventario = new ModelInventario();
        inventario.setId(1L);
        inventario.setSucursal(sucursal);
        inventario.setNombre("Inventario 1");
        inventario.setDescripcion("Descripción del inventario");

        // Configuración para el contexto de la solicitud (necesario para algunos tests)
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void createInventario_ShouldReturnCreatedStatus() {
        // Arrange
        when(inventarioServicio.createInventario(any(ModelInventario.class))).thenReturn(inventario);

        // Act
        ResponseEntity<ModelInventario> response = inventarioControlador.createInventario(inventario);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(inventario.getId(), response.getBody().getId());
        verify(inventarioServicio, times(1)).createInventario(any(ModelInventario.class));
    }

    @Test
    void createInventario_WhenServiceThrowsException_ShouldReturnInternalServerError() {
        // Arrange
        when(inventarioServicio.createInventario(any(ModelInventario.class))).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<ModelInventario> response = inventarioControlador.createInventario(inventario);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void obtenerTodosLosInventarios_ShouldReturnOkStatus() {
        // Arrange
        List<ModelInventario> inventarios = Arrays.asList(inventario);
        when(inventarioServicio.obtenerTodosLosInventarios()).thenReturn(inventarios);

        // Act
        ResponseEntity<List<ModelInventario>> response = inventarioControlador.obtenerTodosLosInventarios();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        verify(inventarioServicio, times(1)).obtenerTodosLosInventarios();
    }

    @Test
    void obtenerTodosLosInventarios_WhenServiceThrowsException_ShouldReturnInternalServerError() {
        // Arrange
        when(inventarioServicio.obtenerTodosLosInventarios()).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<List<ModelInventario>> response = inventarioControlador.obtenerTodosLosInventarios();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void obtenerInventarioPorId_WhenExists_ShouldReturnOkStatus() {
        // Arrange
        when(inventarioServicio.obtenerInventarioPorId(anyLong())).thenReturn(Optional.of(inventario));

        // Act
        ResponseEntity<ModelInventario> response = inventarioControlador.obtenerInventarioPorId(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(inventario.getId(), response.getBody().getId());
        verify(inventarioServicio, times(1)).obtenerInventarioPorId(1L);
    }

    @Test
    void obtenerInventarioPorId_WhenNotExists_ShouldReturnNotFoundStatus() {
        // Arrange
        when(inventarioServicio.obtenerInventarioPorId(anyLong())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ModelInventario> response = inventarioControlador.obtenerInventarioPorId(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void obtenerInventarioPorId_WhenServiceThrowsException_ShouldReturnInternalServerError() {
        // Arrange
        when(inventarioServicio.obtenerInventarioPorId(anyLong())).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<ModelInventario> response = inventarioControlador.obtenerInventarioPorId(1L);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void eliminarInventario_WhenExists_ShouldReturnNoContentStatus() {
        // Arrange
        when(inventarioServicio.obtenerInventarioPorId(anyLong())).thenReturn(Optional.of(inventario));
        doNothing().when(inventarioServicio).eliminarInventario(anyLong());

        // Act
        ResponseEntity<Void> response = inventarioControlador.eliminarInventario(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(inventarioServicio, times(1)).obtenerInventarioPorId(1L);
        verify(inventarioServicio, times(1)).eliminarInventario(1L);
    }

    @Test
    void eliminarInventario_WhenNotExists_ShouldReturnNotFoundStatus() {
        // Arrange
        when(inventarioServicio.obtenerInventarioPorId(anyLong())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Void> response = inventarioControlador.eliminarInventario(1L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(inventarioServicio, times(1)).obtenerInventarioPorId(1L);
        verify(inventarioServicio, never()).eliminarInventario(anyLong());
    }

    @Test
    void eliminarInventario_WhenServiceThrowsException_ShouldReturnInternalServerError() {
        // Arrange
        when(inventarioServicio.obtenerInventarioPorId(anyLong())).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<Void> response = inventarioControlador.eliminarInventario(1L);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void actualizarInventario_WhenExists_ShouldReturnOkStatus() {
        // Arrange
        when(inventarioServicio.actualizarInventario(anyLong(), any(ModelInventario.class))).thenReturn(inventario);

        // Act
        ResponseEntity<ModelInventario> response = inventarioControlador.actualizarInventario(1L, inventario);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(inventario.getId(), response.getBody().getId());
        verify(inventarioServicio, times(1)).actualizarInventario(1L, inventario);
    }

    @Test
    void actualizarInventario_WhenServiceThrowsException_ShouldReturnInternalServerError() {
        // Arrange
        when(inventarioServicio.actualizarInventario(anyLong(), any(ModelInventario.class))).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<ModelInventario> response = inventarioControlador.actualizarInventario(1L, inventario);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}