package mx.edu.utez.proyecto1E.controller.persona.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.proyecto1E.model.persona.Persona;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePersonaDto {
    private Long id;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private String curp;
    private String correo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    //Convierte la entidad en DTO (un usuario puede no tener persona asociada)
    public static ResponsePersonaDto from(Persona persona) {
        if (persona == null) {
            return null;
        }
        return new ResponsePersonaDto(
                persona.getId(),
                persona.getNombres(),
                persona.getPrimerApellido(),
                persona.getSegundoApellido(),
                persona.getCurp(),
                persona.getEmail(),
                persona.getFechaNacimiento()
        );
    }
}
