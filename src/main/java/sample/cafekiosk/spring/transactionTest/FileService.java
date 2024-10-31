package sample.cafekiosk.spring.transactionTest;

import org.springframework.stereotype.Service;

@Service
public class FileService {

    public void createTempFile() {
        System.out.println("FileService에서 임시 파일 생성 중 (트랜잭션 없음)");
        // 파일 생성 로직을 수행합니다.
    }
}
