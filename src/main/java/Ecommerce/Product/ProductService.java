package Ecommerce.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.io.IOException;

import Ecommerce.Product.Dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.repository.query.Param;
// import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        if(product.getReleaseDate()==null){
            product.setReleaseDate(LocalDateTime.now());
        }
        if (product.getProductAvailable() == null) {
            product.setProductAvailable(true); // default value
        }
        product.setImage_name(imageFile.getOriginalFilename());
        product.setImage_type(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        Product existingProduct = repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setStockQuantity(product.getStockQuantity());

        if (!imageFile.isEmpty()) {
            existingProduct.setImageDate(imageFile.getBytes());
            existingProduct.setImage_name(imageFile.getOriginalFilename());
            existingProduct.setImage_type(imageFile.getContentType());
        }
        return repo.save(product);
    }

    public boolean deleteProduct(int id) {
        if (!repo.existsById(id))
            return false;
        repo.deleteById(id);
        return true;
    }

    public List<Product> searchProduct(String keyword) {
        // TODO Auto-generated method stub
        return repo.searchProduct(keyword);
    }

}
