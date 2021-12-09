package pl.mkotra.demo.core.service;

import org.springframework.data.util.Pair;
import pl.mkotra.demo.core.model.PointsByMonth;
import pl.mkotra.demo.core.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointsByMonthCalculator {

    public static final int FIRST_THRESHOLD = 50;
    public static final int SECOND_THRESHOLD = 100;
    public static final int SECOND_THRESHOLD_FACTOR = 2;

    public static List<PointsByMonth> calculate(List<Transaction> transactions) {
        Map<Pair<String, Integer>, Integer> pointsMap = new HashMap<>();
        transactions.forEach(transaction -> addPoints(pointsMap, transaction));
        return mapToList(pointsMap);
    }

    private static void addPoints(Map<Pair<String, Integer>, Integer> pointsMap, Transaction transaction) {
        String customerId = transaction.getCustomerId();
        int month = transaction.month();
        int points = transaction.points(FIRST_THRESHOLD, 1) + transaction.points(SECOND_THRESHOLD, SECOND_THRESHOLD_FACTOR);
        Pair<String, Integer> key = Pair.of(customerId, month);
        Integer totalPoints = pointsMap.getOrDefault(key, 0);
        totalPoints += points;
        pointsMap.put(key, totalPoints);
    }

    private static List<PointsByMonth> mapToList(Map<Pair<String, Integer>, Integer> pointsMap) {
        List<PointsByMonth> pointsByMonths = new ArrayList<>();
        pointsMap.forEach((key, value) -> {
            pointsByMonths.add(new PointsByMonth(key.getFirst(), key.getSecond(), value));
        });
        return pointsByMonths;
    }
}