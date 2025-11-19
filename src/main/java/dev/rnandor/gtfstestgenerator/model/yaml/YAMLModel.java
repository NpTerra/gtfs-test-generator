package dev.rnandor.gtfstestgenerator.model.yaml;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter @Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class YAMLModel {

    @ToString.Exclude
    protected List<Map<String, List<Number>>> stations;
    protected List<YAMLLine> lines;
    protected List<YAMLCalendar> calendar;
    protected List<YAMLTrip> trips;

    @Setter(AccessLevel.NONE) // we won't set this manually, this is only used for lombok's toString xd
    protected final List<YAMLStation> mappedStations = null;

    public List<YAMLStation> getMappedStations() {
        if(stations == null)
            return Collections.emptyList();

        var mapped = new ArrayList<YAMLStation>();

        for (var entry : stations) {
            var e = entry.entrySet().iterator().next();
            YAMLStation s = new YAMLStation();
            s.setName(e.getKey());

            List<Number> data = e.getValue();
            double a = data.get(1) == null ? 0 : data.get(1).doubleValue();
            double b = data.get(2) == null ? 0 : data.get(2).doubleValue();
            double c = data.get(3) == null ? 0 : data.get(3).doubleValue();

            s.setId((Integer) data.getFirst());

            s.setA(a);
            s.setB(b);
            s.setIsGPS(c != 0);

            mapped.add(s);
        }

        return mapped;
    }


}
