package dev.rnandor.gtfstestgenerator.model.yaml;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YAMLTrip {
    private Integer id;
    private Integer line;
    private Integer service;
}
