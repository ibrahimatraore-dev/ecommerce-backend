package alten.datasource.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "carte_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProductEntity product;

    @ManyToOne
    private UserEntity user;

    private int quantity;
}
