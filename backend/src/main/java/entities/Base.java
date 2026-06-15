package entities;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass; // abstract

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass // por ser abstract

public abstract class Base {

    @Id // TP JPA
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Builder.Default
    private boolean eliminado = false;

    @Column(name = "creado_fecha")
    @Builder.Default
    private LocalDateTime creadoFecha = LocalDateTime.now();

}
