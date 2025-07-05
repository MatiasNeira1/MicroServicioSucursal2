package cl.duocuc.MicroServicioSucursal.service;

import cl.duocuc.MicroServicioSucursal.modelo.ModelInventario;
import cl.duocuc.MicroServicioSucursal.modelo.ModelSucursal;
import cl.duocuc.MicroServicioSucursal.repositorio.InventarioRepository;
import cl.duocuc.MicroServicioSucursal.repositorio.SucursalRepository;
import cl.duocuc.MicroServicioSucursal.servicio.InventarioServicio;
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
public class InventarioServicioTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private InventarioServicio inventarioServicio;

    private ModelInventario inventario;
    private ModelSucursal sucursal;

    @BeforeEach
    void setUp() {
        sucursal = new ModelSucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal Central");

        inventario = new ModelInventario();
        inventario.setId(1L);
        inventario.setSucursal(sucursal);
        inventario.setNombre("Inventario Principal");
        inventario.setDescripcion("Inventario de productos varios");
    }

    @Test
    void testCreateInventario_Success() {

        when(sucursalRepository.existsById(anyLong())).thenReturn(true);
        when(inventarioRepository.save(any(ModelInventario.class))).thenReturn(inventario);


        ModelInventario resultado = inventarioServicio.createInventario(inventario);


        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(sucursalRepository, times(1)).existsById(1L);
        verify(inventarioRepository, times(1)).save(inventario);
    }

    @Test
    void testCreateInventario_WhenSucursalNotExists_ThrowsException() {

        when(sucursalRepository.existsById(anyLong())).thenReturn(false);


        assertThrows(RuntimeException.class, () -> {
            inventarioServicio.createInventario(inventario);
        });

        verify(sucursalRepository, times(1)).existsById(1L);
        verify(inventarioRepository, never()).save(any());
    }

    @Test
    void testObtenerTodosLosInventarios() {

        List<ModelInventario> inventarios = Arrays.asList(inventario);
        when(inventarioRepository.findAll()).thenReturn(inventarios);


        List<ModelInventario> resultado = inventarioServicio.obtenerTodosLosInventarios();


        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    void testObtenerInventarioPorId_Found() {
        // Arrange
        when(inventarioRepository.findById(anyLong())).thenReturn(Optional.of(inventario));

        // Act
        Optional<ModelInventario> resultado = inventarioServicio.obtenerInventarioPorId(1L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Inventario Principal", resultado.get().getNombre());
        verify(inventarioRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerInventarioPorId_NotFound() {
        // Arrange
        when(inventarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<ModelInventario> resultado = inventarioServicio.obtenerInventarioPorId(1L);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(inventarioRepository, times(1)).findById(1L);
    }

    @Test
    void testEliminarInventario_WhenExists() {
        // Arrange
        List<ModelInventario> inventarios = Arrays.asList(inventario);
        when(inventarioRepository.existsById(anyLong())).thenReturn(true);
        when(inventarioRepository.findAll()).thenReturn(inventarios);

        // Act
        List<ModelInventario> resultado = inventarioServicio.eliminarInventario(1L);

        // Assert
        assertFalse(resultado.isEmpty());
        verify(inventarioRepository, times(1)).existsById(1L);
        verify(inventarioRepository, times(1)).deleteById(1L);
        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    void testEliminarInventario_WhenNotExists() {
        // Arrange
        List<ModelInventario> inventarios = Arrays.asList(inventario);
        when(inventarioRepository.existsById(anyLong())).thenReturn(false);
        when(inventarioRepository.findAll()).thenReturn(inventarios);

        // Act
        List<ModelInventario> resultado = inventarioServicio.eliminarInventario(1L);

        // Assert
        assertFalse(resultado.isEmpty());
        verify(inventarioRepository, times(1)).existsById(1L);
        verify(inventarioRepository, never()).deleteById(1L);
        verify(inventarioRepository, times(1)).findAll();
    }

    @Test
    void testActualizarInventario_Success() {
        // Arrange
        ModelInventario inventarioActualizado = new ModelInventario();
        inventarioActualizado.setSucursal(sucursal);
        inventarioActualizado.setNombre("Nuevo Nombre");
        inventarioActualizado.setDescripcion("Nueva Descripción");

        when(inventarioRepository.findById(anyLong())).thenReturn(Optional.of(inventario));
        when(inventarioRepository.save(any(ModelInventario.class))).thenReturn(inventarioActualizado);

        // Act
        ModelInventario resultado = inventarioServicio.actualizarInventario(1L, inventarioActualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals("Nuevo Nombre", resultado.getNombre());
        verify(inventarioRepository, times(1)).findById(1L);
        verify(inventarioRepository, times(1)).save(inventario);
    }

    @Test
    void testActualizarInventario_WhenNotFound_ThrowsException() {
        // Arrange
        ModelInventario inventarioActualizado = new ModelInventario();
        when(inventarioRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            inventarioServicio.actualizarInventario(1L, inventarioActualizado);
        });

        verify(inventarioRepository, times(1)).findById(1L);
        verify(inventarioRepository, never()).save(any());
    }
}