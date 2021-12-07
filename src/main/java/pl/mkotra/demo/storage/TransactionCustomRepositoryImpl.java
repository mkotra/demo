package pl.mkotra.demo.storage;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import pl.mkotra.demo.model.PointsByMonth;

import java.util.List;

@Repository
public class TransactionCustomRepositoryImpl implements TransactionCustomRepository {

    private static final int FIRST_THRESHOLD = 50;
    private static final int SECOND_THRESHOLD = 100;
    private static final int SECOND_THRESHOLD_FACTOR = 2;

    private final MongoTemplate mongoTemplate;

    public TransactionCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<PointsByMonth> calculatePointsByMonth() {
        AggregationOperation match = Aggregation.match(Criteria.where("amount").gt(FIRST_THRESHOLD));

        AggregationOperation project1 = Aggregation.project("_id", "customerId")
                .and(DateOperators.Month.month("$timestamp")).as("month")
                .and("amount").minus(FIRST_THRESHOLD).as("amountOverFirstThreshold")
                .and("amount").minus(SECOND_THRESHOLD).as("amountOverSecondThreshold");

        AggregationOperation group = Aggregation.group("customerId", "month")
                .sum("amountOverFirstThreshold").as("totalAmountOverFirstThreshold")
                .sum("amountOverSecondThreshold").as("totalAmountOverSecondThreshold");

        AggregationOperation project2 = Aggregation.project("totalAmountOverFirstThreshold", "totalAmountOverSecondThreshold")
                .and("_id.customerId").as("customerId")
                .and("_id.month").as("month")
                .and("totalAmountOverFirstThreshold").as("pointsThreshold1")
                .and("totalAmountOverSecondThreshold").as("pointsThreshold2");

        AggregationOperation project3 = Aggregation.project("customerId", "month")
                .and(ArithmeticOperators.Floor.floorValueOf("$pointsThreshold1")).as("points1")
                .and(ArithmeticOperators.Floor.floorValueOf("$pointsThreshold2")).as("points2");

        AggregationOperation project4 = Aggregation.project("customerId", "month")
                .and("points2").multiply(SECOND_THRESHOLD_FACTOR).plus("$points1").as("points");

        Aggregation aggregation = Aggregation.newAggregation(match, project1, group, project2, project3, project4);
        return mongoTemplate.aggregate(aggregation, "transactions", PointsByMonth.class).getMappedResults();
    }
}