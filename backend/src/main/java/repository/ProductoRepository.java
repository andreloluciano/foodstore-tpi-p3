package repository;

import entities.Producto;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }
    //5 buscarPorCategoria
public List<Producto> buscarPorCategoria(Long categoriaId){
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery( // filtra por id y eliminado= false
                            "SELECT p FROM Producto p WHERE p.eliminado = false AND p.categoria.id = :categoriaId",
                    Producto.class)
                    .setParameter("categoriaId", categoriaId) // parametro
                    .getResultList(); // devuelvo Lista
        } finally {
            em.close();
        }
}
}

// 6 - la query selecciona productos desde la entidad producto, donde el producto no este eliminado
// y donde el id de su categoria sea igual al id recibido
