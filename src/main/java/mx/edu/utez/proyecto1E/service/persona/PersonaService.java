package mx.edu.utez.proyecto1E.service.persona;

import jakarta.transaction.Transactional;
import mx.edu.utez.proyecto1E.controller.persona.dto.RequestUpdatePersonaDto;
import mx.edu.utez.proyecto1E.controller.persona.dto.ResponsePersonaDto;
import mx.edu.utez.proyecto1E.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.proyecto1E.model.persona.Persona;
import mx.edu.utez.proyecto1E.repository.persona.PersonaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {
    //Inyeccion de dependencias
    private final PersonaRepository personaRepository;

    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    //servicios
    @Transactional
    public ResponsePersonaDto updatePersona(Long id, RequestUpdatePersonaDto payload) {
        //Validar que la persona exista (se trabaja sobre el registro existente, no sobre uno nuevo)
        Persona persona = personaRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(
                        "No existe una persona registrada con el id " + id));

        persona.setNombres(payload.getNombres());
        persona.setPrimerApellido(payload.getPrimerApellido());
        persona.setSegundoApellido(payload.getSegundoApellido());
        persona.setCurp(payload.getCurp());
        persona.setEmail(payload.getCorreo());
        persona.setFechaNacimiento(payload.getFechaNacimiento());

        return ResponsePersonaDto.from(personaRepository.save(persona));
    }
}
