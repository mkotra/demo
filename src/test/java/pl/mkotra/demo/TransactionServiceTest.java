package pl.mkotra.demo;

import org.junit.jupiter.api.Test;
import pl.mkotra.demo.core.TransactionService;
import pl.mkotra.demo.model.Transaction;
import pl.mkotra.demo.storage.TransactionDB;
import pl.mkotra.demo.storage.TransactionRepository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {

    TransactionRepository transactionRepository = mock(TransactionRepository.class);

    TransactionService transactionService = new TransactionService(transactionRepository);

    @Test
    public void simpleUnitTest() {
        //given
        when(transactionRepository.findAll()).thenReturn(
                List.of(
                        new TransactionDB("ID", "CUSTOMER_ID", new BigDecimal(10), OffsetDateTime.parse("2021-12-12T00:00:00.000Z"))
                )
        );

        //when
        List<Transaction> transactions = transactionService.findAll();

        //then
        assertEquals(1, transactions.size());
        Transaction transaction = transactions.get(0);
        assertEquals("ID", transaction.getId());
        assertEquals("CUSTOMER_ID", transaction.getCustomerId());
        assertEquals(new BigDecimal(10), transaction.getAmount());
        assertEquals(OffsetDateTime.parse("2021-12-12T00:00:00.000Z"), transaction.getTimestamp());
    }
}
