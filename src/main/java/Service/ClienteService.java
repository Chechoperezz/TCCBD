package Service;

import Entities.Cliente;
import Repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public void guardarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Cliente obtenerClientePorId(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElse(null);
    }

    public void eliminarCliente(Integer id) {
        clienteRepository.deleteById(id);
    }
}
