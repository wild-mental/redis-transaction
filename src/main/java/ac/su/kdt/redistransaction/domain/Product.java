package ac.su.kdt.redistransaction.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private int salesQuantityAccumulated;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
