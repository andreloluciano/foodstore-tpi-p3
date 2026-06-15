package entities;
import enums.Estado;
import enums.FormaPago;
import interfaces.Calculable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Getter
@Setter
@ToString(exclude = "usuario") // evito usuario para prevenir stackoverflow
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity //TP JPA
@Table(name = "pedidos")
public class Pedido extends Base implements Calculable {

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column
    private Estado estado;

    @Builder.Default
    @Column(nullable = false)
    private double total = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPago formaPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // asociacion con usuario, un pedido pertenece a un usuario

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // si desaparece pedido sus detalles tambien
    @JoinColumn(name = "pedido_id")
    private Set<DetallePedido> detallesPedido = new HashSet<>(); // composicion con DetallePedido
    // un pedido contiene muchos detalles, si desaparece el pedido, los detalles tambien

    /*@Override
    public void calcularTotal() {

        total = 0;  // reinicio el  total
        for (DetallePedido detalle : detallesPedido) { // recorro los detalles
            total += detalle.getSubtotal(); // sumo subtotales
        }
    }*/

    // TP progFuncional
    @Override
    public void calcularTotal() {
        total = detallesPedido.stream()
                .map(detalle -> detalle.getSubtotal()) // map transforma cada detalle en subtotal
                .reduce(0.0, (acumulador, subtotalActual) -> acumulador + subtotalActual);
        // reduce para sumar subtotales
    }

    // TP progFuncional
    public int calcularCantidadItems() {
        return detallesPedido.stream()
                .map(detalle -> detalle.getCantidad())
                .reduce(0, (acumulador, cantidadActual) -> acumulador + cantidadActual);
    }

    // metodo para agregar detalles al pedido, helper
    public void addDetallePedido(int cantidad, Producto producto){
        // creo el detalle dentro de pedido
        DetallePedido detalle = DetallePedido.builder()
                .cantidad(cantidad)
                .producto(producto)
                .build();

        detalle.calcularSubtotal(); // el subtotal ya no se calcula dentro del constructor, entonces calculo manual

        detallesPedido.add(detalle); // agrego detalle al set
        calcularTotal(); // recalculo total
    }

}
