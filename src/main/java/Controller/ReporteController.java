package Controller;

import Entities.Alquiler;
import Service.ReporteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/alquileres-cliente/{idCliente}")
    public String reporteAlquileresPorCliente(@PathVariable Integer idCliente, Model model) {
        List<Alquiler> alquileres = reporteService.obtenerAlquileresPorCliente(idCliente);
        model.addAttribute("alquileres", alquileres);
        return "reporteAlquileresCliente";
    }
}
