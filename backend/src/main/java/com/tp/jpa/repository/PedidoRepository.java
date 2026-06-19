package com.tp.jpa.repository;

import com.tp.jpa.model.Pedido;
import com.tp.jpa.model.enums.Estado;
import jakarta.persistence.EntityManager;
import java.util.List;
import com.tp.jpa.model.Producto;
import com.tp.jpa.model.Usuario;
import com.tp.jpa.model.enums.FormaPago;

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


// la primer query selecciona pedidos desde usuario, usando el set pedidos
// filtra por id de usuario y eliminado = false
// la segunda query selecciona pedidos activos que tengan el estado recibido
// sirve para ver pendientes, confirmados, terminados o cancelados

    public Pedido guardarPedido(Long idUsuario, FormaPago formaPago,
                                List<Long> idsProductos,
                                List<Integer> cantidades) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // busco el usuario dentro de la transaccion
            Usuario usuario = em.find(Usuario.class, idUsuario);

            if (usuario == null || usuario.isEliminado()) {
                em.getTransaction().rollback();
                return null;
            }

            // valido que las listas coincidan
            if (idsProductos.size() != cantidades.size()) {
                em.getTransaction().rollback();
                return null;
            }

            // creo el pedido
            Pedido pedido = Pedido.builder()
                    .estado(Estado.PENDIENTE)
                    .formaPago(formaPago)
                    .total(0.0)
                    .build();

            // recorro los productos elegidos
            for (int i = 0; i < idsProductos.size(); i++) {

                Long idProducto = idsProductos.get(i);
                int cantidad = cantidades.get(i);

                Producto producto = em.find(Producto.class, idProducto);

                if (producto == null || producto.isEliminado()) {
                    em.getTransaction().rollback();
                    return null;
                }

                if (!Boolean.TRUE.equals(producto.getDisponible())) {
                    em.getTransaction().rollback();
                    return null;
                }

                if (producto.getStock() < cantidad) {
                    em.getTransaction().rollback();
                    return null;
                }

                // pedido crea el detalle y calcula subtotal
                pedido.addDetallePedido(cantidad, producto);

                // descuento stock
                producto.setStock(producto.getStock() - cantidad);
            }

            // calculo total del pedido
            pedido.calcularTotal();

            // guardo el pedido para que tenga id, cambie merge por persist
            em.persist(pedido);

            // usuario tiene la relacion con pedidos
            usuario.addPedido(pedido);

            em.getTransaction().commit();

            return pedido;

        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            System.out.println("error al guardar pedido: " + e.getMessage());
            return null;

        } finally {
            em.close();
        }
    }

    public List<Pedido> listarPedidosActivos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery( //JPQL: devuelvo todos los pedidos activos ordenados por fecha (DESC)
                            "SELECT p FROM Pedido p WHERE p.eliminado = false ORDER BY p.fecha DESC",
                            Pedido.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public String buscarUsuario(Long idPedido) {
        EntityManager em = emf.createEntityManager();

        try {
            List<Usuario> usuarios = em.createQuery( // busca el usuario del pedido recibido
                            "SELECT u FROM Usuario u JOIN u.pedidos p WHERE p.id = :idPedido",
                            Usuario.class)
                    .setParameter("idPedido", idPedido) // parametro
                    .getResultList();

            if (usuarios.isEmpty()) {
                return "sin usuario";
            }
            Usuario usuario = usuarios.getFirst(); // tomo el primer resultado y muestro datos

            return usuario.getNombre() + " " + usuario.getApellido();
        } finally {
            em.close();
        }
    }
}
