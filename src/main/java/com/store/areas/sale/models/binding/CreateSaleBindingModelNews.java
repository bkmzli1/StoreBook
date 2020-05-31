package com.store.areas.sale.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateSaleBindingModelNews {
    @NotEmpty
    private String user;

    @NotEmpty
    private String ProductNews;

    @NotNull
    private BigDecimal price;

    public CreateSaleBindingModelNews() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getProductNews() {
        return ProductNews;
    }

    public void setProductNews(String ProductNews) {
        this.ProductNews = ProductNews;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
