package Ecommerce.controller;

// import java.io.IOException;
import java.util.List;

// import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import Ecommerce.model.Product;
import Ecommerce.service.ProductService;

@RestController
@CrossOrigin("*")
@RequestMapping("")
public class ProductController {

    @RequestMapping("")
    public String greet() {
        return "hello world";
    }

    @Autowired // Spring injects ProductService object automatically
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {// allows control hhtp response, body, headers
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = service.getProductById(id);

        if (product != null)
            return new ResponseEntity<>(product, HttpStatus.OK);
        else
            return new ResponseEntity<>(product, HttpStatus.NOT_FOUND);
    }

    // @PostMapping("/product")
    // public ResponseEntity<?> addProduct(@RequestPart Product product,
    // @RequestPart MultipartFile imageFile) {
    // try {
    // Product product1 = service.addProduct(product, imageFile);
    // return new ResponseEntity<>(product1, HttpStatus.CREATED);
    // } catch (Exception e) {
    // return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    // }

    // }
    @PostMapping(value = "/product", consumes = "multipart/form-data") // file+data together
    public ResponseEntity<?> addProduct(
            @RequestPart("product") Product product,
            @RequestPart("imageFile") MultipartFile imageFile) {

        try {
            Product savedProduct = service.addProduct(product, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageDate();

        return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImage_type())).body(product.getImageDate());
    }

    @PutMapping(value = "/product/{id}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart("product") Product product,
            @RequestPart("imageFile") MultipartFile imageFile) {
        Product product1 = null;
        try {
            product1 = service.updateProduct(id, product, imageFile);
            if (!imageFile.isEmpty()) {
                product.setImageDate(imageFile.getBytes());
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("fail to update", HttpStatus.BAD_REQUEST);
        }
        if (product1 != null)
            return new ResponseEntity<String>("updated", HttpStatus.OK);
        else
            return new ResponseEntity<String>("fail to update", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        boolean deleted = service.deleteProduct(id);
        if (deleted)
            return ResponseEntity.ok("deleted");
        else
            return ResponseEntity.badRequest().body("product not found");
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {
        System.out.println("Searching with: " + keyword);
        List<Product> products = service.searchProduct(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

}