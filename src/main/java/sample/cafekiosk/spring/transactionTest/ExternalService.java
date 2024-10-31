package sample.cafekiosk.spring.transactionTest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExternalService {

    @Transactional
    public void updateData() {
        System.out.println("ExternalService에서 데이터 업데이트 중");
        // 트랜잭션 내에서 데이터 업데이트 로직을 수행합니다.
    }
}
