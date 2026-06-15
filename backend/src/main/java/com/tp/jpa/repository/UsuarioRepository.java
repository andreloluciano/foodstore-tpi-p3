package com.tp.jpa.repository;

import com.tp.jpa.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Usuario. Además del CRUD heredado implementa la búsqueda
 * de un usuario activo por su mail.
 */
public class UsuarioRepository extends BaseRepository<Usuario> {

    public UsuarioRepository() {
        super(Usuario.class);
    }

    /**
     * Retorna el usuario activo con el mail indicado.
     */
    public Optional<Usuario> buscarPorMail(String mail) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Usuario> usuarios = em.createQuery( // busca por mail y eliminado = false
                            "SELECT u FROM Usuario u WHERE u.mail = :mail AND u.eliminado = false",
                            Usuario.class)
                    .setParameter("mail", mail) // parametro
                    .getResultList(); // devuelvo lista

            if (usuarios.isEmpty()) {
                return Optional.empty(); // si no hay usuario devuelvo caja vacia
            }

            return Optional.of(usuarios.getFirst()); // si existe devuelvo el primer usuario
        } finally {
            em.close();
        }
    }
}

// la query selecciona usuarios activos donde el mail sea igual al mail recibido
// uso optional para manejar el caso donde no exista ningun usuario con ese mail
