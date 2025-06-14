package Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false)
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;
}
