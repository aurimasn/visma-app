package com.an.visma;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class VismaApp {

    private final List<Transaction> transactions;

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public VismaApp(String csvData) {
        transactions = Stream.of(csvData.split(System.lineSeparator()))
                .map(line -> line.split(","))
                .map(row -> {
                    try {
                        return new Transaction(
                                row[0], // transaction id
                                row[1], // customer id
                                row[2], // item id
                                new SimpleDateFormat(DATE_FORMAT).parse(row[3]), // transaction date
                                Double.parseDouble(row[4]), // item price
                                Integer.parseInt(row[5]) // item quantity
                        );
                    } catch (ParseException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    private int itemCountComparator(Map.Entry<String, Integer> item1, Map.Entry<String, Integer> item2) {
        return item1.getValue().compareTo(item2.getValue());
    }

    private int dateRevenuesComparator(Map.Entry<Date, Double> item1, Map.Entry<Date, Double> item2) {
        return item1.getValue().compareTo(item2.getValue());
    }

    public Double totalRevenue() {
        return transactions.stream()
                .mapToDouble(t -> t.itemPrice() * t.itemQuantity())
                .sum();
    }

    public Integer totalUniqueCustomers() {
        Set<String> customers = new HashSet<>();
        transactions.forEach(t -> customers.add(t.customerId()));
        return customers.size();
    }

    public String mostPopularItem() {
        Map<String, Integer> itemCounts = new HashMap<>();
        for (Transaction t: transactions)
            if (itemCounts.containsKey(t.itemId()))
                itemCounts.put(t.itemId(),itemCounts.get(t.itemId()) + 1);
            else
                itemCounts.put(t.itemId(), 1);
        var sortedItemCounts = itemCounts.entrySet().stream()
                .sorted(this::itemCountComparator)
                .toList();
        return sortedItemCounts.get(sortedItemCounts.size() - 1).getKey();
    }

    public Date highestRevenueDate() {
        Map<Date, Double> dateRevenues = new HashMap<>();
        for (Transaction t: transactions)
            if (dateRevenues.containsKey(t.date()))
                dateRevenues.put(t.date(), dateRevenues.get(t.date()) + t.itemPrice() * t.itemQuantity());
            else
                dateRevenues.put(t.date(), t.itemPrice() * t.itemQuantity());
        var sortedDateRevenues = dateRevenues.entrySet().stream()
                .sorted(this::dateRevenuesComparator)
                .toList();
        return sortedDateRevenues.get(sortedDateRevenues.size() - 1).getKey();
    }
}

record Transaction(String id, String customerId, String itemId, Date date, Double itemPrice, Integer itemQuantity) {
}