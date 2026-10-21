package mx.edu.utez.proyecto1E.controller.usuario;

import jakarta.validation.Valid;
import mx.edu.utez.proyecto1E.controller.usuario.dto.RequestCreateUserDto;
import mx.edu.utez.proyecto1E.controller.usuario.dto.RequestUpdateUserDto;
import mx.edu.utez.proyecto1E.controller.usuario.dto.ResponseUsuarioDto;
import mx.edu.utez.proyecto1E.model.usuario.Usuario;

import mx.edu.utez.proyecto1E.repository.usuario.UsuarioRepository;
import mx.edu.utez.proyecto1E.service.usuario.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/usuario")
public class UsuarioController {
    //inyección de dependencias
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //servicios expuestos Mostrar
    @GetMapping
    public ResponseEntity<List<Usuario>> getallUsers(){
        return ResponseEntity.
                status(200)
                .body(
                        usuarioService.getAllUsers()
                );
    }

    @PostMapping
    public ResponseEntity<Usuario> CreateUser(@RequestBody @Valid RequestCreateUserDto payload){
        return ResponseEntity.status(201).
                body(usuarioService.createUser(payload));
    }

    //Consultar un usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUsuarioDto> getUserById(@PathVariable Long id){
        return ResponseEntity.status(200)
                .body(usuarioService.getUserById(id));
    }

    //Actualizar username y descripcion
    @PutMapping("/{id}")
    public ResponseEntity<ResponseUsuarioDto> updateUser(
            @PathVariable Long id,
            @RequestBody @Valid RequestUpdateUserDto payload){
        return ResponseEntity.status(200)
                .body(usuarioService.updateUser(id, payload));
    }

    //Activar usuario
    @PatchMapping("/{id}/activar")
    public ResponseEntity<ResponseUsuarioDto> activateUser(@PathVariable Long id){
        return ResponseEntity.status(200)
                .body(usuarioService.activateUser(id));
    }

    //Desactivar usuario
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<ResponseUsuarioDto> deactivateUser(@PathVariable Long id){
        return ResponseEntity.status(200)
                .body(usuarioService.deactivateUser(id));
    }
}
