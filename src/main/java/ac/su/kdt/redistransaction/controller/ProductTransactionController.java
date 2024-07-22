package ac.su.kdt.redistransaction.controller;

import ac.su.kdt.redistransaction.domain.ProductDTO;
import ac.su.kdt.redistransaction.domain.ProductResponseDTO;
// import ac.su.kdt.redistransaction.service.ProductService;
import ac.su.kdt.redistransaction.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-transaction")
public class ProductTransactionController {
    // private final ProductService productService;
    private final RedisService redisService;
    private final ProductController productController;

    @GetMapping("/transaction-key")
    public String getTransactionKey() {
        String key = UUID.randomUUID().toString();
        while (redisService.hasKey(key)) {
            key = UUID.randomUUID().toString();
        }
        return key;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProductWithTransaction(
        @RequestParam(name = "tr-key") String trKey,
        @RequestBody ProductDTO productDTO
    ) {
        if (redisService.setIfAbsent(trKey, "value")) {
            return productController.createProduct(productDTO);
        }
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
}
