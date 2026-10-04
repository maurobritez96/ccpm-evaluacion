package com.ccpm.evaluacion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @JsonProperty("citycode")
    private String cityCode;

    private String contryCode;

    public Phone() {}

    private Phone(Builder builder) {
        this.number = builder.number;
        this.cityCode = builder.cityCode;
        this.contryCode = builder.contryCode;
    }

    // Static Builder Class
    public static class Builder {
        private String number;
        private String cityCode;
        private String contryCode;

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder cityCode(String cityCode) {
            this.cityCode = cityCode;
            return this;
        }

        public Builder contryCode(String contryCode) {
            this.contryCode = contryCode;
            return this;
        }

        public Phone build() {
            return new Phone(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getCityCode() { return cityCode; }
    public void setCityCode(String cityCode) { this.cityCode = cityCode; }
    public String getContryCode() { return contryCode; }
    public void setContryCode(String contryCode) { this.contryCode = contryCode; }
}