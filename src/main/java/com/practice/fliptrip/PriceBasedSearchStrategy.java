package com.practice.fliptrip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class PriceBasedSearchStrategy implements FlightSearchStrategy {
    private final FlightManager flightManager;

    public PriceBasedSearchStrategy(final FlightManager inputPM) {
        flightManager = inputPM;
    }

    @Override
    public StrategyType getStrategyType() {
        return StrategyType.COST_BASED;
    }

    private Itnierary getSearchResults(final City source, final City destination,
        final Map<City, List<Flight>> customAdjList) {
        final Map<Long, Long> parentLinkage = new HashMap<>();
        final Map<City, Double> cityToCost = new HashMap<>();
        final Map<City, Long> cityToStops = new HashMap<>();

        customAdjList.keySet().stream()
            .forEach(eachCity ->
                cityToCost.put(eachCity, Double.POSITIVE_INFINITY));
//        DEL -> BLR
//            DEL -> MUM -> BLR
//                DEL -> NAG -> MUM -> BLR
        cityToCost.put(source, 0.0);
        PriorityQueue<Flight> pq = new PriorityQueue<>();

        flightManager.getFlightsWithMatchingCity(source)
            .forEach(pq::offer);

        while(!pq.isEmpty()) {
            final var currentNode=  pq.poll();
            if (currentNode.getTo().equals(destination)) {
                break;
            }

            customAdjList.get(currentNode.getFrom())
                .forEach(nextFlight -> {
                    final var currentPrice = nextFlight.getPrice();
                    final var nextCity = nextFlight.getTo();

                    if (cityToCost.get(currentNode.getFrom()) + currentPrice <
                        cityToCost.get(nextCity)) {
                        parentLinkage.put(nextFlight.getId(), currentNode.getId());
                        pq.offer(nextFlight);
                    }
                    if (cityToStops.get(currentNode.getFrom()) + 1 == cityToStops.get(nextCity)) {
                        if (cityToCost.get(currentNode.getFrom()) + currentPrice <
                            cityToCost.get(nextCity)) {
                        }
                    }
                });
        }

        if (cityToCost.get(destination).equals(Double.POSITIVE_INFINITY)) {
            throw new IllegalStateException("Unable to reach destination");
        }

        return new Itnierary();
    }

    @Override
    public Itnierary getSearchResults(City source, City destination) {
        // djisktra algorithm

        return getSearchResults(
            source,
            destination,
            flightManager.getAdjacencyMap()
        );
    }

    @Override
    public Itnierary getSearchResults(City from, City to, AddOns userAddOns) {
        final List<Itnierary> multipleResults =  new ArrayList<>();
        flightManager.getFlightsBasedOnAddOns(userAddOns)
            .forEach(airlineType -> {
                    multipleResults.add(
                        getSearchResults(from, to,
                            flightManager.getAdjacencyMap(airlineType))
                );
            });

        return multipleResults.get(0);
    }
}
