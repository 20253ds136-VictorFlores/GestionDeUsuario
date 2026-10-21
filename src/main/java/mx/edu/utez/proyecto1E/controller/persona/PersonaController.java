package mx.edu.utez.proyecto1E.controller.persona;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1E.controller.persona.dto.RequestUpdatePersonaDto;
import mx.edu.utez.proyecto1E.controller.persona.dto.ResponsePersonaDto;
import mx.edu.utez.proyecto1E.service.persona.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/persona")
public class PersonaController {
    //inyección de dependencias
    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    //Actualizar la información de una persona (id de la PERSONA, no del usuario)
    @PutMapping("/{id}")
    public ResponseEntity<ResponsePersonaDto> updatePersona(
            @PathVariable Long id,
            @RequestBody @Valid RequestUpdatePersonaDto payload) {
        return ResponseEntity.status(200)
                .body(personaService.updatePersona(id, payload));
    }
}
