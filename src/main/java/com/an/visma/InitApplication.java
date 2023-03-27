package com.an.visma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class InitApplication {

    public static void main(String[] args) throws IOException {
        System.out.println("Enter data file name (default is data.csv):");
        var reader = new BufferedReader(new InputStreamReader(System.in));
        var fileName = reader.readLine();
        fileName = (fileName.equals("")) ? "data.csv" : fileName;

        String content = Utils.readFileToString(fileName);
        VismaApp app = new VismaApp(content);

        System.out.println("Total revenue: " + app.totalRevenue());
        System.out.println("Total unique customers: " + app.totalUniqueCustomers());
        System.out.println("Most popular item: " + app.mostPopularItem().orElse("N/A"));
        Optional<Date> dt = app.highestRevenueDate();
        if (dt.isPresent())
                System.out.println("Date with highest revenue: " + new SimpleDateFormat(VismaApp.DATE_FORMAT).format(dt.get()));
            else
                System.out.println("Date with highest revenue: N/A");
    }

}
