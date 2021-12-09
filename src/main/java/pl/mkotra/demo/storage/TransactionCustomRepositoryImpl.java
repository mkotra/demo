package pl.mkotra.demo.storage;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import pl.mkotra.demo.core.model.PointsByMonth;

import java.util.List;

@Repository
class TransactionCustomRepositoryImpl implements TransactionCustomRepository {

    private final MongoTemplate mongoTemplate;

    public TransactionCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<PointsByMonth> calculatePointsByMonth(int firstThreshold, int secondThreshold, int secondThresholdFactor) {
        AggregationOperation match = Aggregation.match(Criteria.where("amount").gt(firstThreshold));

        AggregationOperation project1 = Aggregation.project("customerId")
                .and(DateOperators.Month.month("$timestamp")).as("month")
                .and("amount").minus(firstThreshold).as("amountOverFirstThresholdIncludingAmountOverSecondThreshold")
                .and("amount").minus(secondThreshold).as("amountOverSecondThreshold");

        AggregationOperation project2 = Aggregation.project("customerId", "month", "amountOverFirstThresholdIncludingAmountOverSecondThreshold")
                .and(ConditionalOperators.when(Criteria.where("amountOverSecondThreshold").gte(0))
                        .thenValueOf("amountOverSecondThreshold")
                        .otherwise(0)).as("amountOverSecondThreshold");

        AggregationOperation project3 = Aggregation.project("customerId", "month", "amountOverSecondThreshold")
                .and("amountOverFirstThresholdIncludingAmountOverSecondThreshold").minus("$amountOverSecondThreshold")
                .as("amountOverFirstThreshold");

        AggregationOperation project4 = Aggregation.project("customerId", "month")
                .and(ArithmeticOperators.Floor.floorValueOf("$amountOverFirstThreshold")).as("amountOverFirstThresholdInt")
                .and(ArithmeticOperators.Floor.floorValueOf("$amountOverSecondThreshold")).as("mountOverSecondThresholdInt");

        AggregationOperation group = Aggregation.group("customerId", "month")
                .sum("amountOverFirstThresholdInt").as("totalAmountOverFirstThreshold")
                .sum("mountOverSecondThresholdInt").as("totalAmountOverSecondThreshold");

        AggregationOperation project5 = Aggregation.project("totalAmountOverFirstThreshold", "totalAmountOverSecondThreshold")
                .and("_id.customerId").as("customerId")
                .and("_id.month").as("month")
                .and("totalAmountOverFirstThreshold").as("pointsThreshold1")
                .and("totalAmountOverSecondThreshold").as("pointsThreshold2");

        AggregationOperation project6 = Aggregation.project("customerId", "month")
                .and("pointsThreshold1").as("effectivePoints1")
                .and("pointsThreshold2").multiply(secondThresholdFactor).as("effectivePoints2");

        AggregationOperation project7 = Aggregation.project("customerId", "month")
                .andExpression("effectivePoints1 + effectivePoints2").as("points");

        Aggregation aggregation = Aggregation.newAggregation(match, project1, project2, project3, project4, group, project5, project6, project7);
        return mongoTemplate.aggregate(aggregation, "transactions", PointsByMonth.class).getMappedResults();
    }
}
