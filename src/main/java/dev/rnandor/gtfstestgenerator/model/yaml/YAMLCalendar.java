package dev.rnandor.gtfstestgenerator.model.yaml;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class YAMLCalendar {
    private Integer id;
    private String start;
    private String end;
    private List<Integer> availability;
}
