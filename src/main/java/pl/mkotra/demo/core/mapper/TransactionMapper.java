package pl.mkotra.demo.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.mkotra.demo.core.model.Transaction;
import pl.mkotra.demo.storage.TransactionDB;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    Transaction toModel(TransactionDB transactionDB);
    TransactionDB toDB(Transaction transaction);
}
