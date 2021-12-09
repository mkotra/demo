package pl.mkotra.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.mkotra.demo.storage.converter.OffsetDateTimeReaderConverter;
import pl.mkotra.demo.storage.converter.OffsetDateTimeWriterConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoRepositories
public class ApplicationConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new OffsetDateTimeReaderConverter());
        converters.add(new OffsetDateTimeWriterConverter());
        return new MongoCustomConversions(converters);
    }
}
