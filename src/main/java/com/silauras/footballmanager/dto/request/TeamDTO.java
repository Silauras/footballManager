package com.silauras.footballmanager.dto.request;


import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TeamDTO {

    public TeamDTO() {
    }

    public TeamDTO(Integer id, String name, BigDecimal commission, BigDecimal money) {
        this.id = id;
        this.name = name;
        this.commission = commission;
        this.money = money;
    }

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    @DecimalMax("10")
    @DecimalMin("0")
    private BigDecimal commission;

    @NotNull
    @DecimalMin("0")
    private BigDecimal money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
