package ac.su.kdt.redistransaction.controller;

import ac.su.kdt.redistransaction.domain.RequestBodyData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class LogController {

    private static final Logger logger = LogManager.getLogger(LogController.class);

    @GetMapping("/products")
    public void showProducts(@RequestParam(required = false) String userid) {
        // loop 10 random product id
        for (int i = 0; i < 10; i++) {
            int randomProductId = (int) (Math.random() * 100) + 1;
            logRequest("expose", "/products/", "GET", String.valueOf(randomProductId), userid, null);
        }
    }

    @GetMapping("/products/{productId}")
    public void getProduct(
        @PathVariable String productId,
        @RequestParam(name="user-id", required = false) String userId
    ) {
        logRequest("detail", "/products/" + productId, "GET", productId, userId, null);
    }

    @PostMapping("/cart")
    public void addToCart(@RequestBody RequestBodyData data) {
        logRequest("cart", "/cart", "POST", data.getProductId(), data.getUserId(), data.getTransactionId());
    }

    @PostMapping("/order")
    public void placeOrder(@RequestBody RequestBodyData data) {
        logRequest("order", "/order", "POST", data.getProductId(), data.getUserId(), data.getTransactionId());
    }

    private void logRequest(String logType, String url, String method, String productId, String userid, String transactionId) {
        String logMessage = String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s",
            logType,
            LocalDateTime.now(),
            url,
            method,
            productId,
            userid,
            transactionId != null ? transactionId : "null");
        logger.info(logMessage);
    }
}
