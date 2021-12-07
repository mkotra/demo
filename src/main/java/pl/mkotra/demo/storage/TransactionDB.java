package pl.mkotra.demo.storage;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128;

@Document(collection = "transactions")
@TypeAlias("TransactionDB")
public record TransactionDB(
        @Id
        String id,
        String customerId,
        @Field(targetType = DECIMAL128)
        BigDecimal amount,
        OffsetDateTime timestamp)  {
}
