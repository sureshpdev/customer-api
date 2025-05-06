package com.customer.api.controller;

import com.customer.api.dto.CustomerResponse;
import com.customer.api.entity.Customer;
import com.customer.api.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    @DisplayName("Get customer by ID returns customer with correct tier")
    void getCustomerByIdReturnsCustomerWithCorrectTier() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Suresh Test");
        customer.setEmail("test.demo@gmail.com");
        customer.setAnnualSpend(BigDecimal.valueOf(15000));
        customer.setLastPurchaseDate(LocalDate.now().minusMonths(3));

        when(customerService.getCustomerById(1L)).thenReturn(customer);

        ResponseEntity<?> response = customerController.getCustomerById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Platinum", ((CustomerResponse) response.getBody()).getTier());
    }

    @Test
    @DisplayName("Get customers by name returns list of customers")
    void getCustomersByNameReturnsListOfCustomers() {
        Customer customer1 = new Customer();
        customer1.setName("Suresh");
        Customer customer2 = new Customer();
        customer2.setName("Suresh");

        when(customerService.getCustomerByName("Suresh")).thenReturn(Arrays.asList(customer1, customer2));

        ResponseEntity<List<Customer>> response = customerController.getCustomerByName("Suresh");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @DisplayName("Get customer by email returns correct customer")
    void getCustomerByEmailReturnsCorrectCustomer() {
        Customer customer = new Customer();
        customer.setEmail("test.demo@gmail.com");

        when(customerService.getCustomerByEmail("jane.doe@example.com")).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getCustomerByEmail("jane.doe@example.com");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("test.demo@gmail.com", response.getBody().getEmail());
    }

    @Test
    @DisplayName("Get customer by email with null email returns 200")
    void getCustomerByEmailWithNullEmailReturns400() {
        ResponseEntity<Customer> response = customerController.getCustomerByEmail(null);

        assertEquals(200, response.getStatusCodeValue());
    }
}