package com.example.webpro1.Repository;

import com.example.webpro1.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
//This is a custom JPQL (Java Persistence Query Language) query.
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchproducts(@Param("keyword") String keyword);
}
//LIKE is an SQL command used to search for patterns inside text.
//% is a special symbol (wildcard) in SQL.
//CONCAT means join strings together.
//keyword is the text you want to search for (passed from your program).
//So, CONCAT('%', :keyword, '%') makes a new string that has % before and after the keyword.
//Imagine keyword is "phone".
//CONCAT('%', 'phone', '%') = '%phone%'
//The query looks for any text that contains "phone" anywhere inside.
//Examples of matching words:
//        "smartphone" (because it contains "phone")
//        "headphones"
//        "phone case"
/*Runs the query
Gets the results from the database
Converts them into a List<Product>
Returns that list to your service layer*/