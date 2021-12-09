package pl.mkotra.demo.core.factory;

import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OffsetDateTimeFactoryTest {

    @Test
    public void shouldParseIsoString() {
        //when
        OffsetDateTime result = OffsetDateTimeFactory.parse("2021-01-12T00:00:00.000Z");

        //then
        assertEquals(result.toString(), "2021-01-12T00:00Z");
    }

    @Test
    public void shouldParseNullWithoutExceptionIfAcceptsNullValues() {
        //when
        OffsetDateTime result = OffsetDateTimeFactory.parse(null);

        //then
        assertNull(result);
    }
}