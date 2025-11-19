package dev.rnandor.gtfstestgenerator.model.yaml;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class YAMLLine {
    private Integer id;
    private String name;
    private String short_name;
    private String color;
    private Integer type;
    private List<YAMLStop> stops;
}
