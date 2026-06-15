package com.tp.jpa.repository;

import com.tp.jpa.model.Pedido;
import com.tp.jpa.model.enums.Estado;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Repositorio de Pedido. Además del CRUD heredado implementa consultas por
 * usuario y por estado.
 */
public class PedidoRepository extends BaseRepository<Pedido> {

    public PedidoRepository() {
        super(Pedido.class);
    }

    /**
     * Retorna los pedidos activos del usuario indicado.
     */
    public List<Pedido> buscarPorUsuario(Long idUsuario) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery( // filtra por usuario y eliminado = false
                            "SELECT p FROM Usuario u JOIN u.pedidos p WHERE u.id = :idUsuario AND p.eliminado = false",
                            Pedido.class)
                    .setParameter("idUsuario", idUsuario) // parametro
                    .getResultList(); // devuelvo lista
        } finally {
            em.close();
        }
    }

    /**
     * Retorna los pedidos activos que coinciden con el estado indicado.
     */
    public List<Pedido> buscarPorEstado(Estado estado) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery( // filtra por estado y eliminado = false
                            "SELECT p FROM Pedido p WHERE p.estado = :estado AND p.eliminado = false",
                            Pedido.class)
                    .setParameter("estado", estado) // parametro
                    .getResultList(); // devuelvo lista
        } finally {
            em.close();
        }
    }
}

// la primer query selecciona pedidos desde usuario, usando el set pedidos
// filtra por id de usuario y eliminado = false

// la segunda query selecciona pedidos activos que tengan el estado recibido
// sirve para ver pendientes, confirmados, terminados o cancelados
