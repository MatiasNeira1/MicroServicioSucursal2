package cl.duocuc.MicroServicioSucursal.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="producto")
public class ModelProducto {

     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq")
     @SequenceGenerator(name = "producto_seq", sequenceName = "PRODUCTO_SEQ", allocationSize = 1)
     private long id;
     private String nombre;
     private String descripcion;
     private int stock;
     private int precio;


     @ManyToOne
     @JoinColumn(name="id_inventario")
     @JsonIgnoreProperties("productos")
     @JsonBackReference

     private ModelInventario inventario;



}
