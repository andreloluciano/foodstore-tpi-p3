package DTOs;

public record UsuarioDTO(

        int id,
        String nombre,
        String apellido,
        String email
)
{ }

// no escribo ni rol ni password