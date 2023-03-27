package com.an.visma;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class InitApplication {

    public static void main(String[] args) throws IOException {
        String content = Utils.readFileToString("data.csv");
        VismaApp app = new VismaApp(content);

        System.out.println("Total revenue: " + app.totalRevenue());
        System.out.println("Total unique customers: " + app.totalUniqueCustomers());
        System.out.println("Most popular item: " + app.mostPopularItem());
        System.out.println("Date with highest revenue: "
                + new SimpleDateFormat(VismaApp.DATE_FORMAT).format(app.highestRevenueDate()));
    }

}
