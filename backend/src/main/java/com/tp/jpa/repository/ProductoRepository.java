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
}

// la query selecciona productos desde la categoria, usando el set productos
// filtra por el id de la categoria y por eliminado = false
