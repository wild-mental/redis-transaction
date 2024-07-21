package ac.su.kdt.redistransaction.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private int salesQuantityAccumulated;
    private CategoryResponseDTO category;

    public static ProductResponseDTO fromProduct(Product product) {
        return new ProductResponseDTO(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getSalesQuantityAccumulated(),
            CategoryResponseDTO.fromCategory(product.getCategory())
        );
    }

    public static List<ProductResponseDTO> fromProducts(List<Product> productsList) {
        List<ProductResponseDTO> products = new ArrayList<>();
        for (Product product : productsList) {
            products.add(ProductResponseDTO.fromProduct(product));
        }
        return products;
    }
}
