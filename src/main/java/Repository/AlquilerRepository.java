package Repository;

import Entities.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {

    @Query("SELECT a FROM Alquiler a WHERE a.cliente.idCliente = :idCliente")
    List<Alquiler> findByCliente_IdCliente(@Param("idCliente") Integer idCliente);


    @Query("SELECT a FROM Alquiler a WHERE a.fechaDevolucionReal IS NULL")
    List<Alquiler> findAlquileresPendientes();

}
