package Ecommerce.model;

import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

// import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // this class represents a database table 
@Data // automatically generates getters,setters,tostring() etc
@AllArgsConstructor //create const with all fields
@NoArgsConstructor// a default cons to create object for jpa
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increments value no need to manually assign
    private Integer id;

    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date releaseDate;

    private Boolean productAvailable;
    private Integer stockQuantity;

    private String image_name;
    private String image_type;

    @Lob //this field can store large binary data, storing actual image in database
    private byte[] imageDate;
}
