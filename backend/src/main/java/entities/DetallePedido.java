package entities;
import java.util.Objects;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "producto", callSuper = false) // compara detalles por producto
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity // TP JPA
@Table(name = "detalles_pedido")
public class DetallePedido extends Base {

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private double subtotal; // subtotal se calcula automaticamente, depende de cantidad y precio

    @ManyToOne(fetch = FetchType.LAZY) // muchos detalles pueden apuntar al mismo producto
    @JoinColumn(name = "producto_id", nullable = false) // fk
    // asociacion con producto
    private Producto producto;  // un detalle pertenece a un producto

    // recalcula subtotal segun cantidad y producto
    public void calcularSubtotal() {
        subtotal = cantidad * producto.getPrecio();
    }

}
