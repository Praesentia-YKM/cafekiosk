package sample.cafekiosk.spring.transactionTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private FileService fileService;

    @Test
    @DisplayName("트랜잭션 체이닝 전파가 정상적으로 적용되는지 검증")
    @Transactional
    public void testTransactionChaining() {
        // given: 트랜잭션을 시작할 준비가 되었을 때
        // (여기서는 트랜잭션 시작 준비가 되어 있다는 가정이므로 추가 준비는 생략)

        // when: TransactionService의 트랜잭션 메서드를 호출했을 때
        transactionService.executeWithTransaction();

        // then: 트랜잭션이 활성화되어 있어야 한다
        assertTrue(isTransactionActive(), "트랜잭션 체이닝이 적용되지 않았습니다.");
    }

    @Test
    @DisplayName("FileService에 트랜잭션이 적용되지 않는지 검증")
    public void testFileServiceWithoutTransaction() {
        // given: 트랜잭션이 없는 FileService를 사용할 때

        // when: FileService의 파일 생성 메서드를 호출했을 때
        fileService.createTempFile();

        // then: 트랜잭션이 활성화되지 않아야 한다
        assertFalse(isTransactionActive(), "FileService에는 트랜잭션이 적용되지 않았습니다.");

        // 추가 검증: 파일이 실제로 생성되었는지 확인
        File tempFile = new File("tempFile.txt");
        assertTrue(tempFile.exists(), "임시 파일이 생성되지 않았습니다.");
    }

    @Test
    @DisplayName("트랜잭션이 없는 메서드는 트랜잭션이 활성화되지 않아야 한다")
    public void testNonTransactionalMethod() {
        // given: 트랜잭션이 없는 메서드를 사용할 때

        // when: @Transactional이 없는 메서드를 호출하면
        boolean noTransactionActive = transactionService.b1WithoutTransaction();

        // then: 트랜잭션이 활성화되지 않아야 한다
        assertFalse(noTransactionActive, "b1WithoutTransaction 메서드에 트랜잭션이 적용되었습니다.");
    }

    @Test
    @DisplayName("트랜잭션이 있는 메서드는 트랜잭션이 활성화되어야 한다")
    public void testTransactionalMethod() {
        // given: 트랜잭션이 있는 메서드를 사용할 때

        // when: @Transactional이 있는 메서드를 호출하면
        boolean transactionActive = transactionService.b1WithTransaction();

        // then: 트랜잭션이 활성화되어 있어야 한다
        assertTrue(transactionActive, "b1WithTransaction 메서드에 트랜잭션이 적용되지 않았습니다.");
    }

    // 트랜잭션 활성화 상태 확인 헬프 메서드
    private boolean isTransactionActive() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }
}
