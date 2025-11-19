package dev.rnandor.gtfstestgenerator.model.inner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class GTFSModel {

    @Setter
    private Agency agency;

    private List<Route> routes = new ArrayList<>();
    private List<Station> stations = new ArrayList<>();
    private List<Calendar> calendars = new ArrayList<>();

    private List<Trip> trips = new ArrayList<>();
    private List<StopTime> stopTimes = new ArrayList<>();

}
