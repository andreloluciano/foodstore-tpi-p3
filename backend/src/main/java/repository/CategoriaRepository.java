package repository;
import entities.Categoria;

public class CategoriaRepository extends BaseRepository<Categoria> {
    public CategoriaRepository() {
        super(Categoria.class);
    }
}

// el <T> de Base pasa a ser <Categoria> y hereda los metodos