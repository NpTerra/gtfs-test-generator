package dev.rnandor.gtfstestgenerator.model.inner;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Route {

    @Parsed(field = "route_id")
    private String id;

    @Parsed(field = "route_short_name")
    private String shortName;

    @Parsed(field = "route_long_name")
    private String longName;

    @Parsed(field = "route_type")
    private int type;

    @Parsed(field = "route_color")
    private String color;
}
