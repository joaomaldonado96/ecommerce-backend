package com.example.ecommerce.constants;

public class SaleProductQueries {
    public static final String TOP_5_BEST_SELLING_PRODUCTS = "SELECT sp.product FROM SaleProduct sp GROUP BY sp.product ORDER BY SUM(sp.quantity) DESC LIMIT 5";
}
