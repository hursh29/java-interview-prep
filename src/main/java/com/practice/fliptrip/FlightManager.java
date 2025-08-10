package com.practice.fliptrip;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.practice.fliptrip.AddOns.FOOD;
import static com.practice.fliptrip.Airline.DELTA;
import static com.practice.fliptrip.Airline.INDIGO;

class Flight implements Comparator<Flight> {
    private final Long id;
    private final City from;
    private final City to;
    private final Double price;
    private final Airline airline;

    public Flight(City from, City to, Double price, Airline airline) {
        this.id = UUID.randomUUID().node();
        this.from = from;
        this.to = to;
        this.price = price;
        this.airline = airline;
    }

    public Long getId() {
        return id;
    }

    public City getFrom() {
        return from;
    }

    public City getTo() {
        return to;
    }

    public Double getPrice() {
        return price;
    }

    public Airline getAirline() {
        return airline;
    }

    @Override
    public int compare(Flight o1, Flight o2) {
        return o1.getPrice().compareTo(o2.getPrice());
    }
}

enum City {
    DEL, BLR, LDN, SYD, MEX
}

enum Airline {
    DELTA, INDIGO, AIR_INDIA;
}

enum AddOns {
    FOOD, DRINKS, EXCESS_BAGGAGE
}

public class FlightManager {
    private final Map<Long, Flight> flightIdToFlight;
    private final Map<Airline, List<Flight>> airlineToFlights;
    private final Map<AddOns, List<Airline>> addOnsToAirlines;

    public FlightManager() {
        this.flightIdToFlight = new HashMap<>();
        this.airlineToFlights = new HashMap<>();
        addOnsToAirlines = new HashMap<>();
        final List<Airline> foodFlights = new ArrayList<>();
        foodFlights.add(INDIGO);
        foodFlights.add(DELTA);

        addOnsToAirlines.put(FOOD, foodFlights);
    }

    public boolean addFlight(final City from,
                             final City to,
                             final Double price,
                             final Airline airline) {
        boolean flightAlreadyExist = flightIdToFlight.values().stream()
            .anyMatch((flight) -> from.equals(flight.getFrom()) && to.equals(flight.getTo()));

        if (flightAlreadyExist) {
            throw new IllegalStateException("Cannot add existing flight");
        }
        final Flight newFlight = new Flight(from, to, price, airline);
        flightIdToFlight.put(newFlight.getId(), newFlight); // 2
//        Delta -> 1, 2
        airlineToFlights.getOrDefault(newFlight.getAirline(), new ArrayList<>()).add(newFlight);

        return true;
    }

    public Map<City, List<Flight>> getAdjacencyMap() {
        return this.airlineToFlights.values().stream()
            .flatMap(Collection::stream)
            .collect(Collectors.groupingBy(Flight::getFrom));
    }

    public Map<City, List<Flight>> getAdjacencyMap(final Airline inputAirline) {
        return this.airlineToFlights.values().stream()
            .flatMap(Collection::stream)
            .filter(flight -> flight.getAirline().equals(inputAirline))
            .collect(Collectors.groupingBy(Flight::getFrom));
    }

    public Optional<Flight> getFlight(final Long inputId) {
        return Optional.ofNullable(flightIdToFlight.get(inputId));
    }

    public List<Flight> getFlightsWithMatchingCity(final City city) {
        return this.airlineToFlights.values().stream()
            .flatMap(Collection::stream)
            .filter(flight -> flight.getFrom().equals(city))
            .collect(Collectors.toList());
    }

    public List<Airline> getFlightsBasedOnAddOns(final AddOns inputAddOn) {
        return addOnsToAirlines.getOrDefault(inputAddOn, new ArrayList<>());
    }
}
