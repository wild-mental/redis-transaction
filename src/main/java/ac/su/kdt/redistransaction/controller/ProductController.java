package ac.su.kdt.redistransaction.controller;

import ac.su.kdt.redistransaction.domain.*;
import ac.su.kdt.redistransaction.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    // CRUD API
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDTO>> getProducts(
        // Get products by category id and pagination
        // with default page and size
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        // Get the products by category id and pagination
        List<Product> productsList = productService.getProducts(categoryId, page, size);
        // Convert the products to ProductResponseDTO
        return new ResponseEntity<>(ProductResponseDTO.fromProducts(productsList), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(
        // Get the product by id
        @PathVariable Long id
    ) {
        // Get the product by id
        Optional<Product> product = productService.getProduct(id);
        return product.map(
            value -> new ResponseEntity<>(
                ProductResponseDTO.fromProduct(value)
                , HttpStatus.OK)
        ).orElseGet(
            () -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDTO> createProduct(
        // Get the request body as ProductDTO and create a product
        @RequestBody ProductDTO productDTO
        ) {
        Product product = productService.createProduct(productDTO);
        if (product == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ProductResponseDTO.fromProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
        @PathVariable Long id,
        // Get the request body and update the product
        @RequestBody ProductDTO productDTO
    ) {
        Product product = productService.updateProduct(id, productDTO);
        if (product == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ProductResponseDTO.fromProduct(product), HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(
        // Delete the product by id
        @PathVariable Long id
    ) {
        Boolean result = productService.deleteProduct(id);
        if (!result) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/products/{id}/categories")
    public List<Map<String, String>> getProductCategory(
        // Get the product category by id
        @PathVariable Long id
    ) {
        // Get the product category by id
        return productService.getProductCategory(id);
    }
}
