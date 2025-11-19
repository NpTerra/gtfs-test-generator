package dev.rnandor.gtfstestgenerator.model.yaml;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YAMLRoot {
    private String name;
    private String description;
    private String act;

    private YAMLAgency agency;

    private Boolean randomizeOffsets = true;

    private YAMLModel arrange;
    private YAMLModelExpected expected;
}
