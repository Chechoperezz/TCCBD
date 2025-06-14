package Service;

import Entities.Alquiler;
import Repository.AlquilerRepository;

import java.util.List;

@Service
public class ReporteService {

    private final AlquilerRepository alquilerRepository;

    public ReporteService(AlquilerRepository alquilerRepository) {
        this.alquilerRepository = alquilerRepository;
    }

    public List<Alquiler> obtenerAlquileresPorCliente(Integer idCliente) {
        return alquilerRepository.findByCliente_IdCliente(idCliente);

    }
}
