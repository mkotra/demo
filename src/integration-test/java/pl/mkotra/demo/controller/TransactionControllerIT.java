package pl.mkotra.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.BodyInserters;
import pl.mkotra.demo.core.model.Transaction;
import pl.mkotra.demo.core.model.fixture.TransactionFixture;
import pl.mkotra.demo.core.request.CreateTransactionRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionControllerIT extends BaseIT {

    @Test
    void shouldReturnErrorWhenRequestIsNotValid() {
        //given
        CreateTransactionRequest request = new CreateTransactionRequest(null, null);

        //when
        Error result = webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/transactions")
                        .build())
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(Error.class)
                .returnResult()
                .getResponseBody();

        //then
        assertNotNull(result);
        assertNotNull(result.getMessage());
    }

    @Test
    void shouldCreateTransaction() {
        //given
        CreateTransactionRequest request = new CreateTransactionRequest("CUSTOMER_ID", BigDecimal.TEN);

        //when
        Transaction result = webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/transactions")
                        .build())
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Transaction.class)
                .returnResult()
                .getResponseBody();

        //then
        assertNotNull(result);
        assertNotNull(result.getId());

        //when
        Transaction createdTransaction = transactionService.find(result.getId()).orElse(null);
        assertNotNull(createdTransaction);
        assertEquals(request.customerId(), createdTransaction.getCustomerId());
        assertEquals(request.amount(), createdTransaction.getAmount());
        assertNotNull(result.getTimestamp());
    }

    @Test
    void shouldReturnTransactions() {
        //given
        Transaction transaction = TransactionFixture.transaction();
        transactionService.save(transaction);

        //when
        List<Transaction> result = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/transactions")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Transaction.class)
                .returnResult()
                .getResponseBody();

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        Transaction actual = result.get(0);

        assertNotNull(actual.getId());
        assertEquals(transaction.getCustomerId(), actual.getCustomerId());
        assertEquals(transaction.getAmount(), actual.getAmount());
        assertEquals(transaction.getTimestamp(), actual.getTimestamp());
    }
}
