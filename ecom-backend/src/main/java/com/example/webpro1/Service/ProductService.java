package com.example.webpro1.Service;


import com.example.webpro1.Model.Product;
import com.example.webpro1.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;


    public List<Product> getproduct() {
        return repo.findAll();
    }

    public Product getproductsbyid(int prodid) {
        return repo.findById(prodid).orElse(null);

    }

    /*so th multipart is a interface it has some methods to use these methods and get the original details of the image
     and save in the relational database repo.save()
    using spring data jpa is this correct*/
    public Product addproduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);

    }
/*When a client uploads an image file (usually via a form), the server receives it as a MultipartFile object (in Spring).
Calling imageFile.getBytes() on this MultipartFile converts the uploaded image into a raw byte array (byte[]).
Your code then uses this byte[] to store the image or process it.*/
/*MultipartFile is an interface in Spring used to represent an uploaded file received in a multipart request.
When a client uploads a file (like an image), Spring wraps that file as a MultipartFile object.
You can get the content of the uploaded file as raw bytes by calling the method:
byte[] bytes = multipartFile.getBytes();*/
    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageData(imageFile.getBytes());
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    public void deleteproduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchproducts(String keyword) {
        return repo.searchproducts(keyword);
    }
}





