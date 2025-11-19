package dev.rnandor.gtfstestgenerator.model.yaml;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class YAMLModelExpected{
    private YAMLModel model;
    private String exception;
}
