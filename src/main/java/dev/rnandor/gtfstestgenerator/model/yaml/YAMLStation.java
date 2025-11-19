package dev.rnandor.gtfstestgenerator.model.yaml;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YAMLStation {
    private Integer id;
    private String name;
    private Double a; // x or lat
    private Double b; // y or lon
    private Boolean isGPS;
}
