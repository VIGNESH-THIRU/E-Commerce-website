package com.example.webpro1.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

/*@NoArgsConstructor:
Required by Spring, JPA, Jackson, etc.
Used to create an empty object.
Then setters (from @Data) are used to assign values.
@AllArgsConstructor:
Used when you manually want to create the object with all values in one line.
Sets all values via constructor parameters.
@Data:
Generates getters and setters for all fields.
Setters: Used by Spring/JPA to populate object fields.
Getters: Used when you need to read the values later.*/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "`desc`") // avoid MySQL keyword conflict
    private String desc;

    private String brand;

    private BigDecimal price;

    private String category;


    private LocalDate releaseDate;

    private boolean available;

    private int quantity;

    private String imageName;
    private String imageType;
    @Lob
    //@Lob on imageData tells JPA to store the image in the database as a large binary object (BLOB).
    private byte[] imageData;
    //The browser (or any client) converts raw bytes + MIME type into the original image format to display it.
    // The server just stores and serves raw bytes with the correct content type.
}
//You store images as raw bytes (`byte[]`) with their MIME type (e.g., "image/jpeg").
// When retrieved, the server sends these bytes unchanged with the correct `Content-Type` header.
// The client (browser) uses this header to decode the bytes and display the image in its original format.



