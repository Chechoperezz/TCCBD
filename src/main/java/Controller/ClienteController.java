package Controller;

import Entities.Cliente;
import Service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes());
        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteService.guardarCliente(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editarCliente(@PathVariable("id") Integer id, Model model) {
        Cliente cliente = clienteService.obtenerClientePorId(id);
        model.addAttribute("cliente", cliente);
        return "clientes/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable("id") Integer id) {
        clienteService.eliminarCliente(id);
        return "redirect:/clientes";
    }
}
