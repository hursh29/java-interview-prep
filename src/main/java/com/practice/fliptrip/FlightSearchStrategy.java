package com.practice.fliptrip;

import java.util.ArrayList;
import java.util.List;

class Itnierary {
    final List<Flight> flightsEnroute;
    Double totalCost;

    public Itnierary(List<Flight> flightsEnroute, Double totalCost) {
        this.flightsEnroute = flightsEnroute;
        this.totalCost = totalCost;
    }

    public Itnierary() {
        this.flightsEnroute = new ArrayList<>();
    }
}

public interface FlightSearchStrategy {

    StrategyType getStrategyType();

    Itnierary getSearchResults(final City from, final City to);

    Itnierary getSearchResults(final City from, final City to, final AddOns userAddOns);

}

enum StrategyType {
    COST_BASED, STOPS_BASED
}
