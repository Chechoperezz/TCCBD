package Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "inventario")
@Data
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItem;

    @Enumerated(EnumType.STRING)
    private TipoItem tipoItem;

    private String titulo;
    private String descripcion;
    private Integer cantidadTotal;
    private Integer cantidadDisponible;

    public enum TipoItem {
        Libro, Pelicula, Herramienta
    }
}
