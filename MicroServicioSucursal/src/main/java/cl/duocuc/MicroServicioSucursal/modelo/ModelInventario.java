package cl.duocuc.MicroServicioSucursal.modelo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="inventario")

public class ModelInventario {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_inventario")
    @SequenceGenerator(name = "seq_inventario", sequenceName = "inventario_seq", allocationSize = 1)
    private long id;
    private String nombre;
    private String descripcion;




    @ManyToOne
    @JoinColumn(name="id_sucursal")
    @JsonIgnoreProperties({"inventario", "empleados"})

    private ModelSucursal sucursal;

    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ModelProducto> productos;

}
