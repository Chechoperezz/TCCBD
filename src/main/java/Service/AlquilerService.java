package Service;

import Entities.Alquiler;
import Entities.DetalleAlquiler;
import Entities.Inventario;
import Repository.AlquilerRepository;
import Repository.DetalleAlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AlquilerService {

    private final AlquilerRepository alquilerRepository;
    private final DetalleAlquilerRepository detalleAlquilerRepository;
    private final InventarioService inventarioService;

    @Autowired
    public AlquilerService(AlquilerRepository alquilerRepository,
                           DetalleAlquilerRepository detalleAlquilerRepository,
                           InventarioService inventarioService) {
        this.alquilerRepository = alquilerRepository;
        this.detalleAlquilerRepository = detalleAlquilerRepository;
        this.inventarioService = inventarioService;
    }

    // Guardar encabezado de alquiler
    public void guardarAlquiler(Alquiler alquiler) {
        alquilerRepository.save(alquiler);
    }

    // Guardar detalle de cada item alquilado
    public void guardarDetalle(DetalleAlquiler detalle) {
        detalleAlquilerRepository.save(detalle);
    }

    // Listar todos los alquileres
    public List<Alquiler> listarAlquileres() {
        return alquilerRepository.findAll();
    }

    // Obtener alquiler por id
    public Alquiler obtenerPorId(Integer id) {
        Optional<Alquiler> alquiler = alquilerRepository.findById(id);
        return alquiler.orElse(null);
    }

    // Eliminar alquiler
    public void eliminarAlquiler(Integer id) {
        alquilerRepository.deleteById(id);
    }

    // Registrar devolución y calcular multa
    public BigDecimal devolverAlquiler(Integer idAlquiler) {
        Alquiler alquiler = alquilerRepository.findById(idAlquiler)
                .orElseThrow(() -> new RuntimeException("Alquiler no encontrado"));

        // Registrar la fecha de devolución actual
        alquiler.setFechaDevolucionReal(LocalDate.now());
        alquiler.setEstado(Alquiler.Estado.DEVUELTO);
        alquilerRepository.save(alquiler);

        // Devolver el stock de cada item
        for (DetalleAlquiler detalle : alquiler.getDetalles()) {
            Inventario item = detalle.getItem();
            item.setCantidadDisponible(item.getCantidadDisponible() + detalle.getCantidad());
            inventarioService.guardarInventario(item);
        }

        // Calcular multa por días de retraso
        long diasRetraso = java.time.temporal.ChronoUnit.DAYS.between(
                alquiler.getFechaDevolucionPrevista(), alquiler.getFechaDevolucionReal());

        BigDecimal multa = BigDecimal.ZERO;
        if (diasRetraso > 0) {
            multa = BigDecimal.valueOf(diasRetraso * 2000); // $2000 por día de retraso
        }

        return multa;
    }
}
