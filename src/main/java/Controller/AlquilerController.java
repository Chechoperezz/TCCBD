package Controller;

import DTO.AlquilerForm;
import Entities.Alquiler;
import Entities.Cliente;
import Entities.DetalleAlquiler;
import Entities.Inventario;
import Service.AlquilerService;
import Service.ClienteService;
import Service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/alquileres")
public class AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private InventarioService inventarioService;

    // Listar todos los alquileres
    @GetMapping
    public String listar(Model model) {
        List<Alquiler> lista = alquilerService.listarAlquileres();
        model.addAttribute("alquileres", lista);
        return "alquileres/lista";
    }

    // Formulario para nuevo alquiler
    @GetMapping("/nuevo")
    public String nuevoAlquiler(Model model) {
        AlquilerForm alquilerForm = new AlquilerForm();
        alquilerForm.setFechaAlquiler(LocalDate.now());
        alquilerForm.setFechaDevolucionPrevista(LocalDate.now().plusDays(7));

        model.addAttribute("alquilerForm", alquilerForm);
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("inventario", inventarioService.listarInventario());
        return "alquileres/formulario";
    }

    // Guardar alquiler
    @PostMapping("/guardar")
    public String guardarAlquiler(@ModelAttribute AlquilerForm alquilerForm) {
        Cliente cliente = clienteService.obtenerClientePorId(alquilerForm.getIdCliente());

        Alquiler alquiler = new Alquiler();
        alquiler.setCliente(cliente);
        alquiler.setFechaAlquiler(alquilerForm.getFechaAlquiler());
        alquiler.setFechaDevolucionPrevista(alquilerForm.getFechaDevolucionPrevista());
        alquiler.setEstado(Alquiler.Estado.PENDIENTE);
        alquilerService.guardarAlquiler(alquiler);

        for (AlquilerForm.ItemAlquiler itemDto : alquilerForm.getItems()) {
            if (itemDto.getCantidad() != null && itemDto.getCantidad() > 0) {
                Inventario item = inventarioService.obtenerPorId(itemDto.getIdItem());
                DetalleAlquiler detalle = new DetalleAlquiler();
                detalle.setAlquiler(alquiler);
                detalle.setItem(item);
                detalle.setCantidad(itemDto.getCantidad());
                alquilerService.guardarDetalle(detalle);

                // Actualizar inventario (restar stock)
                item.setCantidadDisponible(item.getCantidadDisponible() - itemDto.getCantidad());
                inventarioService.guardarInventario(item);
            }
        }

        return "redirect:/alquileres";
    }

    // Eliminar alquiler
    @GetMapping("/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable("id") Integer id) {
        alquilerService.eliminarAlquiler(id);
        return "redirect:/alquileres";
    }

    // Listar alquileres pendientes
    @GetMapping("/pendientes")
    public String listarPendientes(Model model) {
        List<Alquiler> pendientes = alquilerService.listarAlquileres()
                .stream()
                .filter(a -> a.getEstado() == Alquiler.Estado.PENDIENTE)
                .toList();
        model.addAttribute("alquileres", pendientes);
        return "alquileres/pendientes";
    }

    // Registrar devolución
    @GetMapping("/devolver/{id}")
    public String devolverAlquiler(@PathVariable("id") Integer id, Model model) {
        BigDecimal multa = alquilerService.devolverAlquiler(id);
        model.addAttribute("multa", multa);
        return "alquileres/multa";
    }
}
