package mx.edu.utez.proyecto1E.controller.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.proyecto1E.controller.persona.dto.ResponsePersonaDto;
import mx.edu.utez.proyecto1E.model.usuario.Usuario;

//Respuesta de los servicios de usuario: NO incluye el password
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUsuarioDto {
    private Long id;
    private String username;
    private String descripcion;
    private String rol;
    private boolean enabled;
    private ResponsePersonaDto persona;

    public static ResponseUsuarioDto from(Usuario usuario) {
        return new ResponseUsuarioDto(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getDescripcion(),
                usuario.getRol() != null ? usuario.getRol().name() : null,
                usuario.is_enable(),
                ResponsePersonaDto.from(usuario.getPersona())
        );
    }
}
