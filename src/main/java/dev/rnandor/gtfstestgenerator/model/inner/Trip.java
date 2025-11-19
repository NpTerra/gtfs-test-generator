package dev.rnandor.gtfstestgenerator.model.inner;

import com.univocity.parsers.annotations.Parsed;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Trip {

    @Parsed(field = "route_id")
    private String routeId;

    @Parsed(field = "service_id")
    private String serviceId;

    @Parsed(field = "trip_id")
    private String tripId;
}