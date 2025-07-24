package alten.core.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product extends BaseEntity{
    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private Long shellId;
    private String inventoryStatus;
    private double rating;
}
