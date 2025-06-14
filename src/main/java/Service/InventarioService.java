package Service;

import Entities.Inventario;
import Repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    // Listar todo el inventario
    public List<Inventario> listarInventario() {
        return inventarioRepository.findAll();
    }

    // Obtener un item por su ID
    public Inventario obtenerPorId(Integer id) {
        Optional<Inventario> optional = inventarioRepository.findById(id);
        return optional.orElse(null);
    }

    // Guardar o actualizar un item de inventario
    public void guardarInventario(Inventario inventario) {
        inventarioRepository.save(inventario);
    }

    // Eliminar item de inventario
    public void eliminarInventario(Integer id) {
        inventarioRepository.deleteById(id);
    }
}
