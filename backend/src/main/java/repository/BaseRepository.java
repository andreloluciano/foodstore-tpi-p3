package repository;

import entities.Base;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import util.JPAUtil;
import java.util.List;
import java.util.Optional;


public abstract class BaseRepository<T extends Base> {
    private final Class<T> clase;
    protected final EntityManagerFactory emf; // private no puede ser usado por hijas, protected si

    public BaseRepository(Class<T> clase) {
        this.clase = clase;
        this.emf = JPAUtil.getEntityManagerFactory(); // obtengo el emf desde jpautil
    }


    //1 guardo en la bd
    public T guardar(T entity) {

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T entidadGuardada = em.merge(entity); //  guardo si es nuevo o actualiza si ya existe
            em.getTransaction().commit();
            return entidadGuardada; // devuelvo la entidad guardada
        } catch (Exception e) {
            // si hubo error y la transaccion sigue activa hago rollback
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("error al guardar la entidad"); // aviso
            throw e;
        } finally {
            em.close();
        }
    }
    //2 busca por id
    public Optional<T> buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager(); // abro emf
        try {
            T entidad = em.find(clase, id);
            // si existe devuelve valor, sino 'caja' vacia
            return Optional.ofNullable(entidad);
        } finally { em.close();
        }
    }

    //3 muestro registros que no esten eliminados
    public List<T> listarActivos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM " + clase.getSimpleName() + " e WHERE e.eliminado = false",
                    clase
            ).getResultList(); // ejecuto la consulta jpql y devuelvo la lista
        } finally {
            em.close();
        }
    }

    //4 eliminar por id
    public boolean eliminarLogico(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T entidad = em.find(clase, id);     // busco por id

            if (entidad == null) {
                em.getTransaction().rollback();
                return false;
            } // si no existe corto el metodo

            entidad.setEliminado(true);
            em.merge(entidad);  // guardo el cambio y confirmo en commit
            em.getTransaction().commit();

            return true;  // aviso que se encontro y se elimino
        } catch (Exception e) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            // si hubo error y la transaccion sigue activa hago rollback y muestro el error
            System.out.println("error al eliminar la entidad");

            throw e;

        } finally { em.close();
        }
    }
}