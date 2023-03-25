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

    public VismaApp(String csvData) {
        transactions = Stream.of(csvData.split(System.lineSeparator()))
                .map(line -> line.split(","))
                .map(row -> {
                    try {
                        return new Transaction(
                                row[0], // transaction id
                                row[1], // customer id
                                row[2], // item id
                                new SimpleDateFormat("yyyy-MM-dd").parse(row[3]), // transaction date
                                Double.parseDouble(row[4]), // item price
                                Integer.parseInt(row[5]) // item quantity
                        );
                    } catch (ParseException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        var totalRevenue = transactions.stream()
                .mapToDouble(t -> t.itemPrice() * t.itemQuantity())
                .sum();
        System.out.println("Total revenue:" +totalRevenue);

        Set<String> customers = new HashSet<>();
        transactions.forEach(t -> customers.add(t.customerId()));
        System.out.println("Total unique customers: " + customers.size());

        Map<String, Integer> itemCounts = new HashMap<>();
        for (Transaction transaction: transactions)
            if (itemCounts.containsKey(transaction.itemId()))
                itemCounts.put(transaction.itemId(),itemCounts.get(transaction.itemId()) + 1);
            else
                itemCounts.put(transaction.itemId(), 1);
        var sortedItemCounts = itemCounts.entrySet().stream()
                .sorted(this::itemCountComparator)
                .toList();
        System.out.println("Most popular item: " + sortedItemCounts.get(sortedItemCounts.size() - 1).getKey());

        Map<Date, Double> dateRevenues = new HashMap<>();
        for (Transaction transaction: transactions)
            if (dateRevenues.containsKey(transaction.date()))
                dateRevenues.put(transaction.date(), dateRevenues.get(transaction.date()) + transaction.itemPrice() * transaction.itemQuantity());
            else
                dateRevenues.put(transaction.date(), transaction.itemPrice() * transaction.itemQuantity());
        var sortedDateRevenues = dateRevenues.entrySet().stream()
                .sorted(this::dateRevenuesComparator)
                .toList();
        System.out.println("Date with highest revenue: " +sortedDateRevenues.get(sortedDateRevenues.size() - 1).getKey());

    }

    private int itemCountComparator(Map.Entry<String, Integer> item1, Map.Entry<String, Integer> item2) {
        return item1.getValue().compareTo(item2.getValue());
    }

    private int dateRevenuesComparator(Map.Entry<Date, Double> item1, Map.Entry<Date, Double> item2) {
        return item1.getValue().compareTo(item2.getValue());
    }
}

record Transaction(String id, String customerId, String itemId, Date date, Double itemPrice, Integer itemQuantity) {
}