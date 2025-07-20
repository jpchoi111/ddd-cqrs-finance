package com.mycorp.finance.customer.domain.model.vo;

import com.mycorp.finance.global.exception.domain.InvalidAddressException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public final class Address {

    @Column(name = "country", nullable = false)
    private final String country;

    @Column(name = "state", nullable = false)
    private final String state;

    @Column(name = "city", nullable = false)
    private final String city;

    @Column(name = "street", nullable = false)
    private final String street;

    @Column(name = "postal_code", nullable = false)
    private final String postalCode;

    protected Address() {
        this.country = null;
        this.state = null;
        this.city = null;
        this.street = null;
        this.postalCode = null;
    }

    public Address(String country, String state, String city, String street, String postalCode) {
        if (isBlank(country) || isBlank(state) || isBlank(city) || isBlank(street) || isBlank(postalCode)) {
            throw new InvalidAddressException("All address fields must be provided.");
        }
        this.country = country;
        this.state = state;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }

    public String fullAddress() {
        return String.format("%s, %s, %s, %s, %s", street, city, state, postalCode, country);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    //getter
    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }
    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }
}