package com.example.ecommerce.constants;

public class SaleProductQueries {
    public static final String FIND_BY_SALE_ID = "SELECT sp FROM SaleProduct sp WHERE sp.sale.id = :id";
    public static final String TOP_5_BEST_SELLING_PRODUCTS = """
        SELECT p.*, SUM(sp.quantity) AS total_sold
                FROM sale_products sp
                JOIN product p ON sp.product_id = p.id
                GROUP BY p.id
                ORDER BY total_sold DESC
                LIMIT 5;
    """;
}
