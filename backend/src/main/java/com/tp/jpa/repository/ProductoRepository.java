package com.tp.jpa.repository;

import com.tp.jpa.model.Categoria;
import com.tp.jpa.model.Producto;
import jakarta.persistence.EntityManager;

import java.util.List;

/**
 * Repositorio de Producto. Además del CRUD heredado implementa la consulta
 * de productos activos por categoría.
 */
public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    /**
     * Retorna los productos activos que pertenecen a la categoría indicada.
     */
    //5 buscarPorCategoria
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery( // filtra por categoria y eliminado = false
                            "SELECT p FROM Categoria c JOIN c.productos p WHERE c.id = :categoriaId AND p.eliminado = false",
                            Producto.class)
                    .setParameter("categoriaId", categoriaId) // parametro
                    .getResultList(); // devuelvo lista
        } finally {
            em.close();
        }
    }
    // la query selecciona productos desde la categoria, usando el set productos
    // filtra por el id de la categoria y por eliminado = false

    // helpers para el menu de productos
    public Producto guardarProductoEnCategoria(Long idCategoria, Producto producto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Categoria categoria = em.find(Categoria.class, idCategoria);

            if (categoria == null || categoria.isEliminado()) {
                em.getTransaction().rollback();
                return null;
            }

            em.persist(producto); // guardo producto para que tenga id
            categoria.addProducto(producto); // lo agrego a la categoria

            em.getTransaction().commit();

            return producto;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("error al guardar producto: " + e.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

    public String buscarNombreCategoriaProducto(Long idProducto) {
        EntityManager em = emf.createEntityManager();
        try {
            List<String> nombres = em.createQuery( // busca la categoria de un producto
                            "SELECT c.nombre FROM Categoria c JOIN c.productos p WHERE p.id = :idProducto",
                            String.class)
                    .setParameter("idProducto", idProducto) // parametro
                    .getResultList(); // devuelvo lista

            if (nombres.isEmpty()) {
                return "sin categoria";
            }

            return nombres.getFirst();
        } finally {
            em.close();
        }
    }


}


