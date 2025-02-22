package com.example.ecommerce.constants;

public class QueryConstants {
    public static final String TOP_5_FREQUENT_CUSTOMERS = """
        WITH SaleDifferences AS ( 
            SELECT s.person_email, s.created_at, 
            LAG(s.created_at) OVER (PARTITION BY s.person_email ORDER BY s.created_at) AS prev_sale_time 
            FROM sale s
        ), 
        TimeBetweenSales AS ( 
            SELECT person_email, 
            EXTRACT(EPOCH FROM (created_at - prev_sale_time)) / 86400 AS days_between_sales 
            FROM SaleDifferences 
            WHERE prev_sale_time IS NOT NULL
        ), 
        AvgTimeBetweenSales AS ( 
            SELECT person_email, AVG(days_between_sales) AS avg_days_between_sales 
            FROM TimeBetweenSales 
            GROUP BY person_email
        ) 
        SELECT p.email, p.name, COALESCE(AvgTimeBetweenSales.avg_days_between_sales, 0) AS avg_days_between_sales 
        FROM AvgTimeBetweenSales 
        JOIN person p ON AvgTimeBetweenSales.person_email = p.email 
        ORDER BY AvgTimeBetweenSales.avg_days_between_sales ASC 
        LIMIT 5;
    """;
}