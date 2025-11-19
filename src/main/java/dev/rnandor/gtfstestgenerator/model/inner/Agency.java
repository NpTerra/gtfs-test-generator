package dev.rnandor.gtfstestgenerator.model.inner;

import com.univocity.parsers.annotations.Parsed;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Agency {
    @Parsed(field = "agency_name")
    private String name;

    @Parsed(field = "agency_url")
    private String url;

    @Parsed(field = "agency_timezone")
    private String timezone;

    @Parsed(field = "agency_lang")
    private String language;

    @Parsed(field = "agency_phone")
    private String phone;
}
