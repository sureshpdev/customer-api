package com.customer.api.service;

import com.customer.api.entity.Customer;
import com.customer.api.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("Add customer successfully saves and returns customer")
    void addCustomerSuccessfullySavesAndReturnsCustomer() {
        Customer customer = new Customer();
        customer.setName("Suresh Test");
        customer.setEmail("test.demo@gmail.com");

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.addCustomer(customer);

        assertEquals("Suresh Test", result.getName());
        assertEquals("test.demo@gmail.com", result.getEmail());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    @DisplayName("Get customer by ID returns correct customer")
    void getCustomerByIdReturnsCorrectCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Suresh Test");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Suresh Test", result.getName());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get customer by ID with non-existent ID returns null")
    void getCustomerByIdWithNonExistentIdReturnsNull() {
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        Customer result = customerService.getCustomerById(999L);

        assertEquals(null, result);
        verify(customerRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Get customers by name returns list of customers")
    void getCustomersByNameReturnsListOfCustomers() {
        Customer customer1 = new Customer();
        customer1.setName("Suresh");
        Customer customer2 = new Customer();
        customer2.setName("Suresh");

        when(customerRepository.findByName("Suresh")).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> result = customerService.getCustomerByName("Suresh");

        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findByName("Suresh");
    }

    @Test
    @DisplayName("Get customer by email returns correct customer")
    void getCustomerByEmailReturnsCorrectCustomer() {
        Customer customer = new Customer();
        customer.setEmail("test.demo@gmail.com");

        when(customerRepository.findByEmail("test.demo@gmail.com")).thenReturn(customer);

        Customer result = customerService.getCustomerByEmail("test.demo@gmail.com");

        assertEquals("test.demo@gmail.com", result.getEmail());
        verify(customerRepository, times(1)).findByEmail("test.demo@gmail.com");
    }

    @Test
    @DisplayName("Update customer by ID updates customer details")
    void updateCustomerByIdUpdatesCustomerDetails() {
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setName("Old Name");
        existingCustomer.setEmail("old.email@gmail.com");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("New Name");
        updatedCustomer.setEmail("new.email@gmail.com");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);

        customerService.updateCustomerById(1L, updatedCustomer);

        assertEquals("New Name", existingCustomer.getName());
        assertEquals("new.email@gmail.com", existingCustomer.getEmail());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(existingCustomer);
    }

    @Test
    @DisplayName("Delete customer by ID successfully deletes customer")
    void deleteCustomerByIdSuccessfullyDeletesCustomer() {
        doNothing().when(customerRepository).deleteById(1L);

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).deleteById(1L);
    }
}