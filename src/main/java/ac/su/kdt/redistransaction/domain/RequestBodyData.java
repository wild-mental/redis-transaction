package ac.su.kdt.redistransaction.domain;

import lombok.Getter;
import lombok.Setter;

@Getter  @Setter
public class RequestBodyData {
    private String userId;
    private String productId;
    private String transactionId;  //  UUID.randomUUID().toString()
}