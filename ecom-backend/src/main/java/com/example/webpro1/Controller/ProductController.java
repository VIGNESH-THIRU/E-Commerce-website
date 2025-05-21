package com.example.webpro1.Controller;

import com.example.webpro1.Model.Product;
import com.example.webpro1.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
/*ResponseEntity in Spring is a powerful way to build and return an HTTP response. It lets you:
Set the HTTP status code (like 200 OK, 404 Not Found)
Set headers (like Content-Type, Cache-Control)
Return the response body (the actual data, e.g., JSON, image bytes, text)*/
import java.io.IOException;
import java.util.List;
//The browser enforces cross-origin restrictions.
//The Spring Boot server must explicitly allow cross-origin requests using CORS settings (@CrossOrigin or global config).
// If allowed, the browser will accept and process the response.
//ResponseEntity<?> is very flexible and can return various types of data (objects, arrays, images, etc.)
// If you want a more specific response type, you can use something like ResponseEntity<Product>,
// ResponseEntity<byte[]>,// or ResponseEntity<List<Product>>, depending on what you need to return.
/*Frontend (browser) makes a request →
Browser checks if it’s cross-origin →
Browser sends preflight request (if needed) →
Backend responds with CORS headers →
Browser matches headers and allows the request.*/
// ResponseEntity is used to send both the data and the status code in the response.
//It allows you to have full control over the HTTP response sent back to the client.
//@RequestBody is suitable for mapping a JSON object (or similar structured data) directly to a Java object from the body of the request.
//@RequestPart is useful when you're working with multipart data,
// which could include multiple types of content, such as an image file, a JSON object, or other pieces of data,
// each being mapped to different parameters in the controller method.
//`MultipartFile` handles only uploaded files in multipart requests; JSON data is handled separately using `@RequestPart` with objects.
@RestController
@CrossOrigin

@RequestMapping("/api")


public class ProductController {
    @Autowired
    ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> gets() {
        return new ResponseEntity<>(service.getproduct(),HttpStatus.OK);
    }

    @GetMapping("/products/{prodid}")
    public ResponseEntity<Product> getproductbyid(@PathVariable int prodid) {
        Product pro=service.getproductsbyid(prodid);
        if(pro!=null)
        return new ResponseEntity<>(pro, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

@PostMapping("/products")
public ResponseEntity<?> addproduct(@RequestPart Product product,
                                    @RequestPart MultipartFile imageFile){
        try {
            Product product1 = service.addproduct(product, imageFile);
            return new ResponseEntity<>(product1,HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
//why we call the repo beacuse the product is already called in the above getmapping
    @GetMapping("/products/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {

        Product product = service.getproductsbyid(id);
        byte[] imageFile=product.getImageData();
//MediaType is a class in Spring that represents the type of content in HTTP (like images, text, or JSON).
        //returns MediaType.IMAGE_PNG
        return  ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);

    }


    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateproduct(@PathVariable int id,@RequestPart Product product,
                                                @RequestPart MultipartFile imageFile){
        Product product1= null;
        try {
            product1 = service.updateProduct(id,product,imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(product1!=null)
            return new ResponseEntity<>("updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteproduct(@PathVariable int id){
        Product product=service.getproductsbyid(id);
        if(product!=null) {
            service.deleteproduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else
             return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
    }

    //For searching, use @RequestParam this is used for search of multiple items like name,id,brand is this correct
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchproducts(@RequestParam String keyword) {
        List<Product> products = service.searchproducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


}

