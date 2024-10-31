package sample.cafekiosk.spring.transactionTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class TransactionService {

    private final ExternalService externalService;
    private final FileService fileService;

    @Autowired
    public TransactionService(ExternalService externalService, FileService fileService) {
        this.externalService = externalService;
        this.fileService = fileService;
    }

    @Transactional
    public void executeWithTransaction() {
        System.out.println("TransactionService 트랜잭션 시작");

        // 외부 서비스의 메서드 호출 (트랜잭션에 포함)
        externalService.updateData();

        // 파일 서비스의 메서드 호출 (트랜잭션 적용되지 않음)
        fileService.createTempFile();

        System.out.println("TransactionService 트랜잭션 정상 종료");
    }

    @Transactional
    public boolean b1WithTransaction() {
        System.out.println("b1WithTransaction 호출 - 트랜잭션 있음");
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

    public boolean b1WithoutTransaction() {
        System.out.println("b1WithoutTransaction 호출 - 트랜잭션 없음");
        return TransactionSynchronizationManager.isActualTransactionActive();
    }
}
