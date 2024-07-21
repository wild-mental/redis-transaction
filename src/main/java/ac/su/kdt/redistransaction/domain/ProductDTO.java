package ac.su.kdt.redistransaction.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private int salesQuantityAccumulated;
    private Long categoryId;

    public ProductDTO fromProduct(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.salesQuantityAccumulated = product.getSalesQuantityAccumulated();
        this.categoryId = product.getCategory().getId();
        return this;
    }

    public Product toEntity() {
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setPrice(this.price);
        product.setStockQuantity(this.stockQuantity);
        product.setSalesQuantityAccumulated(this.salesQuantityAccumulated);
        Category category = new Category();
        category.setId(this.categoryId);
        product.setCategory(category);
        return product;
    }
}
