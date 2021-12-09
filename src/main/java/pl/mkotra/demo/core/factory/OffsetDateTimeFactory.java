package pl.mkotra.demo.core.factory;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.TimeZone;

public class OffsetDateTimeFactory {

    private static final ZoneId ZONE_ID = TimeZone.getTimeZone("UTC").toZoneId();

    public static OffsetDateTime now() {
        return OffsetDateTime.now(ZONE_ID);
    }

    public static OffsetDateTime parse(String isoString) {
        return Optional.ofNullable(isoString).map(OffsetDateTime::parse).orElse(null);
    }
}
