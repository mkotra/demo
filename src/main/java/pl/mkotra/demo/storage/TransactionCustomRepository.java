package pl.mkotra.demo.storage;

import pl.mkotra.demo.core.model.PointsByMonth;

import java.util.List;

interface TransactionCustomRepository {
    List<PointsByMonth> calculatePointsByMonth(int firstThreshold, int secondThreshold, int secondThresholdFactor);
}
