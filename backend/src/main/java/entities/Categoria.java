package entities;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Getter
@Setter
@ToString(exclude = {"productos"}) // para evitar stackoverflow
@EqualsAndHashCode(of = "nombre", callSuper = false) // compara categorias por nombre
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity //TP JPA
@Table(name = "categorias")
public class Categoria extends Base {

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    // TP JPA
    // una categoria puede tener muchos productos
    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY) // la relacion se maneja desde producto
    @Builder.Default
    // inicializo el set
    private Set<Producto> productos = new HashSet<>();

    // agregacion con producto
    // la categoria agrupa productos pero producto puede existir sin categoria
    public void agregarProducto(Producto producto) {
        productos.add(producto);
        producto.setCategoria(this);
    }

    // TP JPA, elimino producto de la categoria para evitar error de referencia
    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
        producto.setCategoria(null);
    }


}
