package mx.edu.utez.proyecto1E.service.usuario;

import jakarta.transaction.Transactional;
import mx.edu.utez.proyecto1E.controller.usuario.dto.RequestCreateUserDto;
import mx.edu.utez.proyecto1E.controller.usuario.dto.RequestUpdateUserDto;
import mx.edu.utez.proyecto1E.controller.usuario.dto.ResponseUsuarioDto;
import mx.edu.utez.proyecto1E.exception.customExceptions.CustomBadException;
import mx.edu.utez.proyecto1E.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.proyecto1E.model.persona.Persona;
import mx.edu.utez.proyecto1E.model.usuario.Roles;
import mx.edu.utez.proyecto1E.model.usuario.Usuario;
import mx.edu.utez.proyecto1E.repository.persona.PersonaRepository;
import mx.edu.utez.proyecto1E.repository.usuario.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    //Inyeccion de dependencias
    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PersonaRepository personaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
    }

    //servicios
    public List<Usuario> getAllUsers(){
        return usuarioRepository.findAll();
    }


    @Transactional
    public Usuario createUser(RequestCreateUserDto payload){
        //Buscar si el usuario No esta registrado
        Optional<Usuario>usuarioExist=usuarioRepository.findByUsername(payload.getUsername());
        if(usuarioExist.isPresent()){
            throw  new CustomBadException("El username ya existe");
        }
          //craer persona
        Persona newPersona= new Persona();
        newPersona.setNombres(payload.getNombres());
        newPersona.setPrimerApellido(payload.getPrimerApellido());
        newPersona.setSegundoApellido(payload.getSegundoApellido());
        newPersona.setCurp(payload.getCurp());
        newPersona.setEmail(payload.getCorreo());
        newPersona.setFechaNacimiento(payload.getFechaNacimiento());
        //devuelve todod slos datos de la persona con id
        newPersona=personaRepository.save(newPersona);
        //crear usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(payload.getUsername());
        nuevoUsuario.setDescripcion(payload.getDescripcion());
        nuevoUsuario.setPassword(payload.getPassword());
        nuevoUsuario.setRol(Roles.valueOf(payload.getRol()));
        nuevoUsuario.set_enable(true);
        //relacionar el registro de persona con el nuevo usuario
        nuevoUsuario.setPersona(newPersona);

        return usuarioRepository.save(nuevoUsuario);
    }

    //Consultar la información de un usuario por id
    public ResponseUsuarioDto getUserById(Long id){
        return ResponseUsuarioDto.from(findUserOrThrow(id));
    }

    //Activar usuario por id
    @Transactional
    public ResponseUsuarioDto activateUser(Long id){
        return changeEnabled(id, true);
    }

    //Desactivar usuario por id
    @Transactional
    public ResponseUsuarioDto deactivateUser(Long id){
        return changeEnabled(id, false);
    }

    //Actualizar SOLO username y descripcion
    @Transactional
    public ResponseUsuarioDto updateUser(Long id, RequestUpdateUserDto payload){
        //Se carga el registro existente: asi password, rol, estado y persona se conservan al hacer save
        Usuario usuario = findUserOrThrow(id);

        //El username nuevo no debe pertenecer a OTRO usuario (conservar el propio es válido)
        Optional<Usuario> usernameExist = usuarioRepository.findByUsername(payload.getUsername());
        if(usernameExist.isPresent() && !usernameExist.get().getId().equals(id)){
            throw new CustomBadException("El username ya existe");
        }

        usuario.setUsername(payload.getUsername());
        usuario.setDescripcion(payload.getDescripcion());

        return ResponseUsuarioDto.from(usuarioRepository.save(usuario));
    }

    //Metodos auxiliares
    private Usuario findUserOrThrow(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(
                        "No existe un usuario registrado con el id " + id));
    }

    private ResponseUsuarioDto changeEnabled(Long id, boolean enabled){
        Usuario usuario = findUserOrThrow(id);
        usuario.set_enable(enabled);
        return ResponseUsuarioDto.from(usuarioRepository.save(usuario));
    }
}
