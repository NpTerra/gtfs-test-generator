package dev.rnandor.gtfstestgenerator.model.inner;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TestCase {

    private String name;
    private String description;
    private String act;

    private GTFSModel arrange;
    private GTFSModel expected;
    private String exception;

    private int offsetLat = 0;
    private int offsetLon = 0;

}
