package Service;

import org.springframework.stereotype.Service;

import entities.Turno;
import repositories.TurnoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public List<Turno> obtenerTurnosPorPaciente(String paciente) {
        return turnoRepository.findByPaciente(paciente);
    }

    public List<Turno> obtenerTurnosEntreFechas(LocalDateTime inicio, LocalDateTime fin) {
        return turnoRepository.findByFechaHoraBetween(inicio, fin);
    }

    public List<Turno> obtenerTurnosPorEstado(String estado) {
        return turnoRepository.findByEstado(estado);
    }
}

