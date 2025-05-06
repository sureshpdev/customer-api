package com.customer.api.dto;


import java.math.BigDecimal;

public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private String tier;
    private BigDecimal annualSpend;
    private String lastPurchaseDate;

    public CustomerResponse() {
    }

    public CustomerResponse(Long id, String name, String email, String tier, BigDecimal annualSpend, String lastPurchaseDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tier = tier;
        this.annualSpend = annualSpend;
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public BigDecimal getAnnualSpend() {
        return annualSpend;
    }

    public void setAnnualSpend(BigDecimal annualSpend) {
        this.annualSpend = annualSpend;
    }

    public String getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(String lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }
}
