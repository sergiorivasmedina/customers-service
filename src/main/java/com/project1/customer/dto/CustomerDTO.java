package com.project1.customer.dto;

public class CustomerDTO {
    private String name;
    private int type;
    private String identityNumber;

    public CustomerDTO() {
    }

    public CustomerDTO(String name, int type, String identityNumber) {
        this.name = name;
        this.type = type;
        this.identityNumber = identityNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIdentityNumber() {
        return this.identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", identityNumber='" + getIdentityNumber() + "'" +
            "}";
    }
}