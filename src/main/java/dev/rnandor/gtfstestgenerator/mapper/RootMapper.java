package dev.rnandor.gtfstestgenerator.mapper;

import dev.rnandor.gtfstestgenerator.model.inner.*;
import dev.rnandor.gtfstestgenerator.model.yaml.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class RootMapper {
    private RootMapper() {}

    private static final Random rn = new SecureRandom();

    public static TestCase parseYAML(YAMLRoot root) {

        var test = new TestCase();

        if(root.getRandomizeOffsets()) {
            var offsetLat = rn.nextInt(121)-60;
            var offsetLon = rn.nextInt(151)-300;
            test.setOffsetLat(offsetLat);
            test.setOffsetLon(offsetLon);
        }

        test.setName(root.getName());
        test.setDescription(root.getDescription());

        test.setAct(root.getAct());

        var arrange = mapModel(root.getArrange());
        arrange.getStations().forEach(s -> s.moveGPS(test.getOffsetLat(), test.getOffsetLon()));
        test.setArrange(arrange);

        var agency = mapAgency(root.getAgency());
        arrange.setAgency(agency);

        if(root.getExpected().getException() != null) {
            test.setException(root.getExpected().getException());
            return test;
        }

        if(root.getExpected().getModel().getCalendar() == null)
            root.getExpected().getModel().setCalendar(root.getArrange().getCalendar());

        var expected = mapModel(root.getExpected().getModel());
        expected.getStations().forEach(s -> s.moveGPS(test.getOffsetLat(), test.getOffsetLon()));
        test.setExpected(expected);
        expected.setAgency(agency);

        if(expected.getCalendars().isEmpty())
            expected.getCalendars().addAll(arrange.getCalendars());

        return test;
    }

    private static GTFSModel mapModel(YAMLModel model) {
        var mappedModel = new GTFSModel();

        model.getMappedStations().forEach(station -> {
            mappedModel.getStations().add(mapStation(station));
        });

        model.getLines().forEach(line -> {
                mappedModel.getRoutes().add(mapLine(line));
        });

        model.getCalendar().forEach(calendar ->
            mappedModel.getCalendars().add(mapCalendar(calendar))
        );

        model.getTrips().forEach(trip -> {
            var mapped = mapTrip(trip);

            if(mappedModel.getRoutes().stream().map(Route::getId).noneMatch(id -> id.equals(mapped.getRouteId())))
                throw new IllegalStateException("Failed to find route with id: " + trip.getLine() + " on trip #" + trip.getId());

            if(mappedModel.getCalendars().stream().map(Calendar::getService).noneMatch(id -> id.equals(mapped.getServiceId())))
                throw new IllegalStateException("Failed to find calendar with id: " + trip.getService() + " on trip #" + trip.getId());

            mappedModel.getTrips().add(mapped);
        });

        model.getLines().forEach(line -> {
           mappedModel.getStopTimes().addAll(mapStopTimes(line, mappedModel.getTrips(), mappedModel.getStations()));
        });

        return mappedModel;
    }

    private static Agency mapAgency(YAMLAgency agency) {
        var mappedAgency = new Agency();

        mappedAgency.setName(agency.getName());
        mappedAgency.setUrl(agency.getUrl());
        mappedAgency.setTimezone(agency.getTimezone());
        mappedAgency.setLanguage(agency.getLanguage());
        mappedAgency.setPhone(agency.getPhone());

        return mappedAgency;
    }

    private static Route mapLine(YAMLLine line) {
        var route = new Route();

        route.setId("R"+line.getId());
        route.setType(line.getType());
        route.setLongName(line.getName());
        route.setShortName(line.getShort_name());
        route.setColor(line.getColor());

        if(route.getColor().startsWith("#"))
            route.setColor(route.getColor().substring(1));

        return route;
    }

    private static List<StopTime> mapStopTimes(YAMLLine line, List<Trip> trips, List<Station> stations) {
        var mappedStopTimes = new ArrayList<StopTime>();
        var stops = line.getStops();
        for(int i = 0; i < stops.size(); i++) {
            for(var trip : trips) {
                var stop = stops.get(i);
                if(!trip.getRouteId().equals("R" + line.getId()))
                    continue;

                var mappedStop = new StopTime();
                mappedStop.setStopSequence(i+1);
                mappedStop.setTrip(trip.getTripId());
                mappedStop.setArrival(stop.getArrival());
                mappedStop.setDeparture(stop.getDeparture());

                if(mappedStop.getArrival() == null)
                    mappedStop.setArrival("00:00:00");

                if(mappedStop.getDeparture() == null)
                    mappedStop.setDeparture("00:00:00");

                if(stop.getId() != null)
                    mappedStop.setStop("S"+stop.getId());
                else {
                    var station = stations.stream().filter(s-> s.getName().equals(stop.getName())).findFirst().orElse(null);
                    if(station == null)
                        throw new IllegalStateException("Failed to find station with name: " + stop.getName() + " at stop #" + (i+1) + " on trip #" + trip.getTripId());
                    mappedStop.setStop(station.getId());
                }

                mappedStopTimes.add(mappedStop);
            }
        }

        return mappedStopTimes;
    }

    private static Calendar mapCalendar(YAMLCalendar calendar) {
        var mappedCalendar = new Calendar();

        mappedCalendar.setService("C"+calendar.getId());

        mappedCalendar.setMonday(calendar.getAvailability().get(0));
        mappedCalendar.setTuesday(calendar.getAvailability().get(1));
        mappedCalendar.setWednesday(calendar.getAvailability().get(2));
        mappedCalendar.setThursday(calendar.getAvailability().get(3));
        mappedCalendar.setFriday(calendar.getAvailability().get(4));
        mappedCalendar.setSaturday(calendar.getAvailability().get(5));
        mappedCalendar.setSunday(calendar.getAvailability().get(6));

        mappedCalendar.setStartDate(calendar.getStart().replace("-", ""));
        mappedCalendar.setEndDate(calendar.getEnd().replace("-", ""));

        return mappedCalendar;
    }

    private static Trip mapTrip(YAMLTrip trip) {
        return new Trip("R"+trip.getLine(), "C"+trip.getService(), "T"+trip.getId());
    }

    private static Station mapStation(YAMLStation station) {
        var mappedStation = new Station();

        mappedStation.setName(station.getName());
        if(Boolean.TRUE.equals(station.getIsGPS()))
            mappedStation.setGPS(station.getA(), station.getB());
        else
            mappedStation.setXY(station.getA(), station.getB());

        mappedStation.setId("S"+station.getId());

        return mappedStation;
    }
}
