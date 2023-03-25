package com.an.visma;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    }

}

record Transaction(String id, String customerId, String itemId, Date date, Double itemPrice, Integer itemQuantity) {
}