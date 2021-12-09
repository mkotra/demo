package pl.mkotra.demo.controller;

import org.junit.jupiter.api.Test;
import pl.mkotra.demo.core.model.PointsByMonth;
import pl.mkotra.demo.core.model.Transaction;
import pl.mkotra.demo.core.model.fixture.TransactionFixture;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PointControllerIT extends BaseIT {

    @Test
    void shouldCalculatePoints() {
        //given
        Transaction transaction = TransactionFixture.transaction();
        transactionService.save(List.of(transaction, transaction));

        //when
        List<PointsByMonth> result = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/points")
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(PointsByMonth.class)
                .returnResult()
                .getResponseBody();

        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        PointsByMonth actual = result.get(0);
        assertEquals(transaction.getCustomerId(), actual.getCustomerId());
        assertEquals(180, actual.getPoints());
    }
}
