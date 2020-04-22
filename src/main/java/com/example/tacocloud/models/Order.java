package com.example.tacocloud.models;

import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Taco_Order")
public class Order implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date placedAt;

    @NotBlank(message = "name must not be empty")
    private String name;
    @NotBlank(message = "address must not be empty")
    private String street;
    @NotBlank(message = "city must not be empty")
    private String city;
    @NotBlank(message = "state must not be empty")
    private String state;
    @NotBlank(message = "zip must not be empty")
    private String zip;
    @CreditCardNumber(message = "credit card number format invalid")
    private String creditCard;//example value for testing:4647828386366574
    @Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
    private String expiration;
    @Digits(integer = 3, fraction = 0, message = "Must be 3 digits long")
    private String cvv;
    @ManyToMany(targetEntity = Taco.class)
    private List<Taco> tacos = new ArrayList<>();

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }
}
