package alten.datasource.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "wishlist_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProductEntity product;

    @ManyToOne
    private UserEntity user;
}
