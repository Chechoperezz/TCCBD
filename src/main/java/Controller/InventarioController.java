package Controller;

import Entities.Inventario;
import Service.InventarioService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public String listarInventario(Model model) {
        model.addAttribute("inventario", inventarioService.listarInventario());
        return "inventario/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoItem(Model model) {
        model.addAttribute("item", new Inventario());
        model.addAttribute("tipos", Inventario.TipoItem.values());
        return "inventario/formulario";
    }

    @PostMapping("/guardar")
    public String guardarItem(@ModelAttribute("item") Inventario item) {
        inventarioService.guardarInventario(item);
        return "redirect:/inventario";
    }

    @GetMapping("/editar/{id}")
    public String editarItem(@PathVariable("id") Integer id, Model model) {
        Inventario item = inventarioService.obtenerPorId(id);
        model.addAttribute("item", item);
        model.addAttribute("tipos", Inventario.TipoItem.values());
        return "inventario/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarItem(@PathVariable("id") Integer id) {
        inventarioService.eliminarInventario(id);
        return "redirect:/inventario";
    }
}
