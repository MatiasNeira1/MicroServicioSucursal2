package cl.duocuc.MicroServicioSucursal.controller;

import cl.duocuc.MicroServicioSucursal.controlador.SucursalControlador;
import cl.duocuc.MicroServicioSucursal.modelo.ModelSucursal;
import cl.duocuc.MicroServicioSucursal.servicio.SucursalServicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SucursalControladorTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private SucursalServicio sucursalServicio;

    @InjectMocks
    private SucursalControlador sucursalControlador;

    private ModelSucursal sucursal;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(sucursalControlador).build();

        sucursal = new ModelSucursal();
        sucursal.setId(1L);
        sucursal.setNombre("Sucursal Principal");
        sucursal.setDireccion("Calle Principal 123");
    }

    @Test
    public void createSucursal_ShouldReturnCreated() throws Exception {
        when(sucursalServicio.createSucursal(any(ModelSucursal.class))).thenReturn(sucursal);

        mockMvc.perform(post("/sucursales/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Sucursal Principal"));
    }

    @Test
    public void createSucursal_WhenException_ShouldReturnInternalServerError() throws Exception {
        when(sucursalServicio.createSucursal(any(ModelSucursal.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(post("/sucursales/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void todasSucursales_ShouldReturnFound() throws Exception {
        ModelSucursal sucursal2 = new ModelSucursal();
        sucursal2.setId(2L);
        sucursal2.setNombre("Sucursal Secundaria");

        List<ModelSucursal> sucursales = Arrays.asList(sucursal, sucursal2);

        when(sucursalServicio.getAll()).thenReturn(sucursales);

        mockMvc.perform(get("/sucursales/todassucursales"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Sucursal Principal"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nombre").value("Sucursal Secundaria"));
    }

    @Test
    public void todasSucursales_WhenException_ShouldReturnNotFound() throws Exception {
        when(sucursalServicio.getAll()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/sucursales/todassucursales"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void sucursalById_WhenExists_ShouldReturnOk() throws Exception {
        when(sucursalServicio.findbyId(anyLong())).thenReturn(Optional.of(sucursal));

        mockMvc.perform(get("/sucursales/sucursal/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Sucursal Principal"));
    }

    @Test
    public void sucursalById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        when(sucursalServicio.findbyId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/sucursales/sucursal/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void sucursalById_WhenException_ShouldReturnInternalServerError() throws Exception {
        when(sucursalServicio.findbyId(anyLong())).thenThrow(new RuntimeException());

        mockMvc.perform(get("/sucursales/sucursal/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deleteSucursalById_WhenExists_ShouldReturnNoContent() throws Exception {
        when(sucursalServicio.findbyId(anyLong())).thenReturn(Optional.of(sucursal));
        doNothing().when(sucursalServicio).deleteSucursal(anyLong());

        mockMvc.perform(delete("/sucursales/eliminar/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteSucursalById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        when(sucursalServicio.findbyId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/sucursales/eliminar/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteSucursalById_WhenException_ShouldReturnInternalServerError() throws Exception {
        when(sucursalServicio.findbyId(anyLong())).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/sucursales/eliminar/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void updateSucursal_WhenExists_ShouldReturnOk() throws Exception {
        when(sucursalServicio.updateSucursal(anyLong(), any(ModelSucursal.class))).thenReturn(sucursal);

        mockMvc.perform(put("/sucursales/sucursal/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Sucursal Principal"));
    }

    @Test
    public void updateSucursal_WhenException_ShouldReturnInternalServerError() throws Exception {
        when(sucursalServicio.updateSucursal(anyLong(), any(ModelSucursal.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(put("/sucursales/sucursal/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isInternalServerError());
    }
}