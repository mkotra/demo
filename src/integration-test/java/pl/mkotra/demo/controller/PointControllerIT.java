package pl.mkotra.demo.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.mkotra.demo.core.model.PointsByMonth;
import pl.mkotra.demo.core.model.Transaction;
import pl.mkotra.demo.core.model.fixture.TransactionFixture;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PointControllerIT extends BaseIT {

    @ParameterizedTest
    @ValueSource(strings = {"/points/database", "/points/java"})
    void shouldCalculatePointsForBasicScenario(String uriPath) {
        //given
        Transaction transaction = TransactionFixture.transaction();
        transactionService.save(List.of(transaction));

        //when
        List<PointsByMonth> result = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(uriPath)
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
        assertEquals(90, actual.getPoints());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/points/database", "/points/java"})
    void shouldCalculatePointsForRealScenario(String uriPath) {
        //given
        List<Transaction> transactions = TransactionFixture.example1();
        transactionService.save(transactions);

        //when
        List<PointsByMonth> result = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(uriPath)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(PointsByMonth.class)
                .returnResult()
                .getResponseBody();

        //then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertResultContains(result, "CUSTOMER_1", 11, 90);
        assertResultContains(result, "CUSTOMER_1", 12, 180);
        assertResultContains(result, "CUSTOMER_2", 12, 270);
    }

    private void assertResultContains(List<PointsByMonth> result, String customerId, int month, int points) {
        Optional<PointsByMonth> actual = result.stream()
                .filter(r -> r.getCustomerId().equals(customerId) && r.getMonth() == month && r.getPoints() == points)
                .findFirst();
        assertTrue(actual.isPresent());
    }
}
