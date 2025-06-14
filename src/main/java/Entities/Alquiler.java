package Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "alquileres")
@Data
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAlquiler;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    private LocalDate fechaAlquiler;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public enum Estado {
        PENDIENTE, DEVUELTO
    }

    @OneToMany(mappedBy = "alquiler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleAlquiler> detalles;
}
