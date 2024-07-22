package ac.su.kdt.redistransaction.controller;

import ac.su.kdt.redistransaction.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/redis")
public class RedisController {
    private final RedisService redisService;

    @GetMapping("/transaction-key")
    public String getTransactionKey(
        @RequestParam(name="dup-check" ,required = false) String dupCheck
    ) {
        if (Objects.equals(dupCheck, "1")) {
            String key = UUID.randomUUID().toString();
            // 기존에 동일한 key 가 존재하면 다시 생성
            while (redisService.hasKey(key)) {
                key = UUID.randomUUID().toString();
            }
            return key;
        }
        return UUID.randomUUID().toString();
    }

    @GetMapping("/transaction-result-test")
    public ResponseEntity<String> setTransactionKey(@RequestParam String key) {
        boolean transaction = redisService.setIfAbsent(key, "value");
        return new ResponseEntity<>(
            transaction ? "Transaction Success" : "Transaction Fail",
            transaction ? HttpStatus.OK : HttpStatus.CONFLICT
        );
    }

    @GetMapping("/transaction-key1")
    public String getTransactionKey1(
        @RequestParam(name="dup-check" ,required = false) String dupCheck
    ) {
        if (Objects.equals(dupCheck, "1")) {
            String key = UUID.randomUUID().toString();
            // 기존에 동일한 key 가 존재하면 다시 생성
            while (redisService.hasKey1(key)) {
                key = UUID.randomUUID().toString();
            }
            return key;
        }
        return UUID.randomUUID().toString();
    }

    @GetMapping("/transaction-result-test1")
    public ResponseEntity<String> setTransactionKey1(@RequestParam String key) {
        boolean transaction = redisService.setIfAbsent1(key, "value");
        return new ResponseEntity<>(
            transaction ? "Transaction Success" : "Transaction Fail",
            transaction ? HttpStatus.OK : HttpStatus.CONFLICT
        );
    }
}
