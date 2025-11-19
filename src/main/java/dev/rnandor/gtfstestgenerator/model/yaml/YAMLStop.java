package dev.rnandor.gtfstestgenerator.model.yaml;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YAMLStop {
    private Integer id;
    private String name;
    private String arrival;
    private String departure;
}
