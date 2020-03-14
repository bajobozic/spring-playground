package com.example.tacocloud.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class Order {
    @NotBlank(message = "name must not be empty")
    private String name;
    @NotBlank(message = "address must not be empty")
    private String streetAddress;
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
}
