package ac.su.kdt.redistransaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplateDb0;
    private final RedisTemplate<String, String> redisTemplateDb1;

    // 키가 없을 때만 값을 설정
    public boolean setIfAbsent(String key, String value) {
        Boolean result = redisTemplateDb0.opsForValue().setIfAbsent(
            key, value,
            Duration.ofMinutes(1)
        );
        return Boolean.TRUE.equals(result);
    }

    // 키 존재 여부 확인
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplateDb0.hasKey(key));
    }

    // ######################
    // DB Index 1번 사용하는 경우 호출
    public boolean setIfAbsent1(String key, String value) {
        Boolean result = redisTemplateDb1.opsForValue().setIfAbsent(
            key, value,
            Duration.ofMinutes(1)
        );
        return Boolean.TRUE.equals(result);
    }

    public boolean hasKey1(String key) {
        return Boolean.TRUE.equals(redisTemplateDb1.hasKey(key));
    }
}
