package ac.su.kdt.redistransaction.service;

import ac.su.kdt.redistransaction.domain.Product;
import ac.su.kdt.redistransaction.domain.ProductDTO;
import ac.su.kdt.redistransaction.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getProducts(Long categoryId, int page, int size) {
        // get products by category id and pagination
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.getContent();
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(ProductDTO productDTO) {
        try {
            return productRepository.save(productDTO.toEntity());
        } catch (Exception e) {
            return null;
        }
    }

    public Product updateProduct(Long productId, ProductDTO productDTO) {
        try {
            Product product = productDTO.toEntity();
            product.setId(productId);
            return productRepository.save(product);
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean deleteProduct(Long id) {
        return productRepository.findById(id).map(
            product -> {
                productRepository.delete(product);
                return true;
            }
        ).orElse(
            false
        );
    }

    public List<Map<String, String>> getProductCategory(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(
            value -> value.getCategory().getParentsAsc()
        ).orElse(
            null
        );
    }
}
