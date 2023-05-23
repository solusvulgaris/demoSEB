package com.ak.springbootdemo.seb.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import java.util.Objects;

/**
 * Subsidiary Entity
 */
@Entity
@Table(name = "SUBSIDIARY")
public class Subsidiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //is OK for H2
    @Column(name = "SUBSIDIARY_ID")
    private long id;

    @Column(name = "INNER_CODE")
    private String innerCode;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    public Subsidiary(String innerCode, String address, String name, String phoneNumber) {
        this.innerCode = innerCode;
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    protected Subsidiary() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void update(String innerCode, String address, String name, String phoneNumber) {
        this.innerCode = innerCode;
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    @Override
    public String toString() {
        return "Subsidiary{" +
                "id=" + id +
                ", innerCode='" + innerCode + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subsidiary that = (Subsidiary) o;
        return getId() == that.getId() && getInnerCode().equals(that.getInnerCode()) && Objects.equals(getAddress(),
                that.getAddress()) && Objects.equals(getName(), that.getName()) && Objects.equals(getPhoneNumber(),
                that.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getInnerCode(), getAddress(), getName(), getPhoneNumber());
    }

}
