package entities;
import java.util.Objects;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "nombre", callSuper = false) // compara productos por nombre
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity //TP JPA
@Table(name = "productos")
public class Producto extends Base {

    @Column(unique = true)
    private String nombre;

    @Column(nullable = false)
    private double precio;

    @Column(length = 500)
    private String descripcion;

    @Column
    private int stock;

    private String imagen;

    @Column
    private boolean disponible;

    // cambio parcial                   // cambio a EAGER por lazyinitializationexeption
    @ManyToOne(fetch = FetchType.EAGER) // muchas productos pueden pertenecer a una categoria
    @JoinColumn(name = "categoria_id") //  propietaria por tener la fk
    private Categoria categoria;

}
