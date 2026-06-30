package com.prova.controle_estoque.model;

import java.time.LocalDateTime;

public class MovementLog {
    private int id;
    private Integer productId;
    private String productName;
    private String action; // INSERT, UPDATE, DELETE
    private int quantityChanged;
    private String userLogin;
    private LocalDateTime timestamp;

    public MovementLog() {}

    public MovementLog(Integer productId, String productName, String action, int quantityChanged, String userLogin) {
        this.productId = productId;
        this.productName = productName;
        this.action = action;
        this.quantityChanged = quantityChanged;
        this.userLogin = userLogin;
        this.timestamp = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getQuantityChanged() {
        return quantityChanged;
    }

    public void setQuantityChanged(int quantityChanged) {
        this.quantityChanged = quantityChanged;
    }


    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
