package DTO;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AlquilerForm {
    private Integer idCliente;
    private LocalDate fechaAlquiler = LocalDate.now();
    private LocalDate fechaDevolucionPrevista = LocalDate.now().plusDays(7);
    private List<ItemAlquiler> items;

    @Data
    public static class ItemAlquiler {
        private Integer idItem;
        private Integer cantidad;
    }
}
