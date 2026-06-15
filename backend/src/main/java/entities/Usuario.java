package entities;
import enums.Rol;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Getter
@Setter
@ToString(exclude = {"password", "pedidos"}) // evita mostrar password, y pedidos para evitar stackoverflow
@EqualsAndHashCode(of = "email", callSuper = false) // compara usuarios por email
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity //TP JPA
@Table(name = "usuarios")
public class Usuario extends Base {
    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private Rol rol;

    @Builder.Default  // asociacion con pedido
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true) // clase no propietario
    private Set<Pedido> pedidos = new HashSet<>(); // un usuario puede tener muchos pedidos

    // agrega pedidos al usuario, helper
    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
        pedido.setUsuario(this);
    }
}
