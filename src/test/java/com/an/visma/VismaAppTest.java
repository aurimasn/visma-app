package com.an.visma;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertTrue;

class VismaAppTest {

    @Test
    public void testVismaApp_Ok() {
        var dataCsv = """
                transaction_id,customer_id,item_id,transaction_date,item_price,item_quantity
                1001,cust-001,product-001,2022-01-01,12.50,3
                1002,cust-002,product-002,2022-01-01,5.99,5
                1003,cust-003,product-001,2022-01-01,12.50,2
                1004,cust-004,product-003,2022-01-02,25.00,1
                1005,cust-005,product-004,2022-01-02,10.00,4
                1006,cust-006,product-002,2022-01-02,5.99,3
                """;
        VismaApp app = new VismaApp(dataCsv);
        assertTrue(app.transactions().size() == 6);
    }

    @Test
    public void testVismaApp_wrongDataFormats() {
        var dataCsv = """
                1002,product-002,2022-01-01,5.99,5
                1002,cust-002,product-002,2022/01/01,5.99,5
                1003,cust-003,product-001,2022-01-01,aa,2
                1004,cust-004,product-003,2022-01-02,25.00,q
                """;
        VismaApp app = new VismaApp(dataCsv);
        assertTrue(app.transactions().isEmpty());
    }

    @Test
    public void testVismaApp_emptyData() {
        var dataCsv = """
                """;
        VismaApp app = new VismaApp(dataCsv);
        assertTrue(app.transactions().isEmpty());
    }

    @Test
    public void testVismaApp_wrongData() {
        var dataCsv = """
                aaaa,sdddd
                cccc,qqqq,cccc,bbbb,kkkk,llll
                1,2,3,4,5,6
                1,2,3,4,5,6,7,8
                """;
        VismaApp app = new VismaApp(dataCsv);
        assertTrue(app.transactions().isEmpty());
    }

    @Test
    public void testVismaApp_totalRevenueOk() {
        var dataCsv = """
                1001,cust-001,product-001,2022-01-01,12.50,3
                1002,cust-002,product-002,2022-01-01,5.99,5
                1003,cust-003,product-001,2022-01-01,12.50,2
                """;
        VismaApp app = new VismaApp(dataCsv);
        assertTrue(app.totalRevenue() == 92.45D );
    }

    @Test
    public void testVismaApp_totalUniqueCustomersOk() {
        var dataCsv = """
                1001,cust-001,product-001,2022-01-01,12.50,3
                1002,cust-002,product-002,2022-01-01,5.99,5
                1003,cust-003,product-001,2022-01-01,12.50,2
                1003,cust-003,product-001,2022-01-01,12.50,2
                1003,cust-002,product-001,2022-01-01,12.50,2
                1003,cust-003,product-001,2022-01-01,12.50,2 
                """;
        VismaApp app = new VismaApp(dataCsv);
        assertTrue(app.totalUniqueCustomers() == 3);
    }

    @Test
    public void testVismaApp_popularItemOk() {
        var dataCsv = """
                1001,cust-001,product-001,2022-01-01,12.50,3
                1002,cust-002,product-002,2022-01-01,5.99,5
                1003,cust-003,product-001,2022-01-01,12.50,2
                1003,cust-003,product-002,2022-01-01,12.50,2
                1003,cust-002,product-004,2022-01-01,12.50,2
                1003,cust-002,product-004,2022-01-01,12.50,2
                1003,cust-002,product-002,2022-01-01,12.50,2 
                1003,cust-003,product-003,2022-01-01,12.50,2  
                """;
        VismaApp app = new VismaApp(dataCsv);
        assertTrue(app.mostPopularItem().get().equals("product-002"));
    }

    @Test
    public void testVismaApp_dateHighestRevenueOk() throws ParseException {
        var dataCsv = """
                1001,cust-001,product-001,2022-01-01,12.50,3
                1002,cust-002,product-002,2022-01-01,5.99,5
                1003,cust-003,product-001,2022-01-02,12.50,2
                1003,cust-003,product-002,2022-01-02,65.50,2
                1003,cust-002,product-004,2022-01-01,12.50,2
                1003,cust-002,product-004,2022-01-03,12.50,2
                1003,cust-002,product-002,2022-01-03,13.50,2  
                1003,cust-003,product-003,2022-01-01,12.50,2  
                """;
        VismaApp app = new VismaApp(dataCsv);
        var dateFormat = new SimpleDateFormat(VismaApp.DATE_FORMAT);
        var date = dateFormat.parse("2022-01-02");
        assertTrue(app.highestRevenueDate().get().compareTo(date) == 0);
    }

    @Test
    public void testVismaApp_totalRevenueEmpty() {
        var dataCsv = """
                aaa,bbb
                """;
        VismaApp app = new VismaApp(dataCsv);
        assertTrue(app.totalRevenue() == 0);
    }

    @Test
    public void testVismaApp_totalUniqueCustomersEmpty() {
        var dataCsv = """
                aaa,bbb
                """;
        VismaApp app = new VismaApp(dataCsv);
        assertTrue(app.totalUniqueCustomers() == 0);
    }

    @Test
    public void testVismaApp_mostPopularItemWithIncorrectData() {
        var dataCsv = """
                aaa,bbb
                """;
        VismaApp app = new VismaApp(dataCsv);
        assertTrue(app.mostPopularItem().isEmpty());
    }

    @Test
    public void testVismaApp_highestRevenueDateWithIncorrectData() {
        var dataCsv = """
                aaa,bbb
                """;
        VismaApp app = new VismaApp(dataCsv);
        assertTrue(app.highestRevenueDate().isEmpty());
    }

}