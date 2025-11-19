package dev.rnandor.gtfstestgenerator.model.inner;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StopTime {
    @Parsed(field = "trip_id")
    private String trip;

    @Parsed(field = "arrival_time")
    private String arrival;

    @Parsed(field = "departure_time")
    private String departure;

    @Parsed(field = "stop_id")
    private String stop;

    @Parsed(field = "stop_sequence")
    private Integer stopSequence;
}
