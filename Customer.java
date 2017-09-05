package com.klaster.webstore.domain;
import java.io.Serializable;
/**
 * Created by MSI DRAGON on 2017-07-03.
 */
public class Customer implements Serializable {
    private static final long serialVersionUID = 2284040482222162898L;
    private String customerId;
    private String name;
    private Address billingAddress;
    private String address;
    private String phoneNumber;
    private int noOfOrdersMade;

    public Customer() {
        super();
        this.billingAddress = new Address();
    }

    public Customer(String customerId, String name, String address){
        this.customerId = customerId;
        this.name = name;
        this.address = address;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNoOfOrdersMade() {
        return noOfOrdersMade;
    }

    public void setNoOfOrdersMade(int noOfOrdersMade) {
        this.noOfOrdersMade = noOfOrdersMade;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (getNoOfOrdersMade() != customer.getNoOfOrdersMade()) return false;
        if (getCustomerId() != null ? !getCustomerId().equals(customer.getCustomerId()) : customer.getCustomerId() != null)
            return false;
        if (getName() != null ? !getName().equals(customer.getName()) : customer.getName() != null) return false;
        if (getBillingAddress() != null ? !getBillingAddress().equals(customer.getBillingAddress()) : customer.getBillingAddress() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(customer.getAddress()) : customer.getAddress() != null)
            return false;
        return getPhoneNumber() != null ? getPhoneNumber().equals(customer.getPhoneNumber()) : customer.getPhoneNumber() == null;
    }

    @Override
    public int hashCode() {
        int result = getCustomerId() != null ? getCustomerId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getBillingAddress() != null ? getBillingAddress().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + getNoOfOrdersMade();
        return result;
    }

    @Override
    public String toString(){
        return "Klient o id:" + customerId + " o nazwie: " + name + " zamieszkaly: " + address + " liczba zamowien: " + noOfOrdersMade;
    }
}
