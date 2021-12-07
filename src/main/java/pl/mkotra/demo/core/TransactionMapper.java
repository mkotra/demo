package pl.mkotra.demo.core;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.mkotra.demo.model.Transaction;
import pl.mkotra.demo.storage.TransactionDB;

@Mapper
interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    Transaction toModel(TransactionDB transactionDB);
    TransactionDB toDB(Transaction transaction);
}
