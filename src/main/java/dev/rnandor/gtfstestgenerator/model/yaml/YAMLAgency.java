package dev.rnandor.gtfstestgenerator.model.yaml;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class YAMLAgency {
    private String name;
    private String url;
    private String timezone;
    private String language;
    private String phone;
}
