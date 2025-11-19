package dev.rnandor.gtfstestgenerator.model.inner;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Calendar {
    @Parsed(field = "service_id")
    private String service;
    @Parsed(field = "monday")
    private Integer monday;
    @Parsed(field = "tuesday")
    private Integer tuesday;
    @Parsed(field = "wednesday")
    private Integer wednesday;
    @Parsed(field = "thursday")
    private Integer thursday;
    @Parsed(field = "friday")
    private Integer friday;
    @Parsed(field = "saturday")
    private Integer saturday;
    @Parsed(field = "sunday")
    private Integer sunday;
    @Parsed(field = "start_date")
    private String startDate;
    @Parsed(field = "end_date")
    private String endDate;
}
