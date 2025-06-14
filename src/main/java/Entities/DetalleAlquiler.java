package Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalle_alquiler")
@Data
public class DetalleAlquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "idAlquiler")
    private Alquiler alquiler;

    @ManyToOne
    @JoinColumn(name = "idItem")
    private Inventario item;

    private Integer cantidad;
}
