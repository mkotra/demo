package pl.mkotra.demo.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

public class TransactionFixture {

    static final List<String> CUSTOMERS = Arrays.asList(
            "CUSTOMER1", "CUSTOMER2", "CUSTOMER3", "CUSTOMER4", "CUSTOMER5"
    );

    static final List<BigDecimal> AMOUNTS = Arrays.asList(
            BigDecimal.TEN,
            new BigDecimal("50.30"),
            new BigDecimal("75.20"),
            new BigDecimal("100.25"),
            new BigDecimal("120.50"),
            new BigDecimal("150.75"),
            new BigDecimal("200.70"),
            new BigDecimal("250.50"),
            new BigDecimal("250.50")
    );

    static final List<Integer> MONTHS = Arrays.asList(
            -1, -2, -3
    );

    public static Transaction transaction() {
        return new Transaction(null, "CUSTOMER_ID", new BigDecimal("120.25"), OffsetDateTime.parse("2021-12-12T00:00:00.000Z"));
    }

    public static List<Transaction> random(int size) {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Collections.shuffle(CUSTOMERS);
            Collections.shuffle(AMOUNTS);
            Collections.shuffle(MONTHS);
            Transaction transaction = new Transaction(
                    null,
                    CUSTOMERS.get(0),
                    AMOUNTS.get(0),
                    OffsetDateTime.now(TimeZone.getTimeZone("UTC").toZoneId()).plusMonths(MONTHS.get(0))
            );

            transactions.add(transaction);
        }
        return transactions;
    }
}
