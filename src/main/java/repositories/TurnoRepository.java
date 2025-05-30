package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import entities.Turno;
import java.time.LocalDateTime;
import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByPaciente(String paciente);

    List<Turno> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Turno> findByEstado(String estado);
}

