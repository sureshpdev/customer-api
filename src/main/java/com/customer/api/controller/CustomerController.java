package com.customer.api.controller;

import com.customer.api.dto.CustomerResponse;
import com.customer.api.entity.Customer;
import com.customer.api.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customers", description = "CRUD operations for customers")
public class CustomerController {
        @Autowired
        private CustomerService customerService;

        @Operation(
            summary = "Create a new customer",
            description = "Creates a customer. The request body must not contain an ID."
        )
        @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
        })
        @PostMapping
        public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
            return ResponseEntity.status(HttpStatus.CREATED).body(customerService.addCustomer(customer));
        }

        @GetMapping("/{id}")
        public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
            Customer customer = customerService.getCustomerById(id);
            BigDecimal spend = customer.getAnnualSpend();
            LocalDate lastPurchase = customer.getLastPurchaseDate();
            LocalDate now = LocalDate.now();
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setId(customer.getId());
            customerResponse.setName(customer.getName());
            customerResponse.setEmail(customer.getEmail());
            customerResponse.setAnnualSpend(customer.getAnnualSpend());
            customerResponse.setLastPurchaseDate(customer.getLastPurchaseDate().toString());
            if (spend.compareTo(BigDecimal.valueOf(10000)) >= 0 &&
                    lastPurchase != null &&
                    lastPurchase.isAfter(now.minusMonths(6))) {
                customerResponse.setTier("Platinum");
            } else if (spend.compareTo(BigDecimal.valueOf(1000)) >= 0 &&
                    spend.compareTo(BigDecimal.valueOf(10000)) < 0 &&
                    lastPurchase != null &&
                    lastPurchase.isAfter(now.minusMonths(12))) {
                customerResponse.setTier("Gold");
            } else {
                customerResponse.setTier("Silver");
            }
            return ResponseEntity.status(HttpStatus.OK).body(customerResponse);
        }

        @GetMapping("/by-name")
        public ResponseEntity<List<Customer>> getCustomerByName(@RequestParam(required = false) String name) {
            return ResponseEntity.ok(customerService.getCustomerByName(name));
        }

        @GetMapping("/by-email")
        public ResponseEntity<Customer> getCustomerByEmail(@RequestParam(required = false) String email) {
            return ResponseEntity.ok(customerService.getCustomerByEmail(email));
        }

        @PutMapping("/{id}")
        public void updateCustomerById(@PathVariable Long id, @RequestBody Customer customer) {
            customerService.updateCustomerById(id, customer);
        }

        @DeleteMapping("/{id}")
        public void deleteCustomer(@PathVariable Long id) {
            customerService.deleteCustomer(id);
        }

    }
