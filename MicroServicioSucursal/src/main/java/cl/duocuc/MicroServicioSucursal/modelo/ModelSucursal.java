package cl.duocuc.MicroServicioSucursal.modelo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Sucursal")

public class ModelSucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sucursal_seq")
    @SequenceGenerator(name = "sucursal_seq", sequenceName = "SEQ_SUCURSAL", allocationSize = 1)
    private long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String telefono;
    private String horario_apertura;
    private String horario_cierre;

    @OneToMany(mappedBy="sucursal",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("sucursal")
    private List<ModelInventario> inventario;



}
