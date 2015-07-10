package com.panpages.bow.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

@Entity
@Table(name = "Customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID", nullable = false)
    private int customerId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ALIAS")
    private String alias;

    @Column(name = "IMAGE_PATH")
    private String imagePath;

    @Column(name = "IMAGE_HOVER_PATH")
    private String imageHoverPath;

    @Column(name = "ENABLE")
    private boolean enable;

    @JsonIgnore
    @OneToMany(mappedBy = "customerId", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CustomerSurveys> customerSurveys;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageHoverPath() {
        return imageHoverPath;
    }

    public void setImageHoverPath(String imageHoverPath) {
        this.imageHoverPath = imageHoverPath;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<CustomerSurveys> getCustomerSurveys() {
        return customerSurveys;
    }

    public void setCustomerSurveys(List<CustomerSurveys> customerSurveys) {
        this.customerSurveys = customerSurveys;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
