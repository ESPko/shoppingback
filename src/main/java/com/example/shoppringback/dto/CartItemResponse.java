package com.example.shoppringback.dto;

import com.example.shoppringback.entity.CartItem;
import com.example.shoppringback.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("productId")
    private Long productId;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("selectedSize")
    private String selectedSize;

    @JsonProperty("selected")
    private boolean selected;

    private int price;

    private String infoImage;

    // CartItem을 인자로 받는 생성자
    public CartItemResponse(CartItem item, Product product) {
        this.id = item.getId();
        this.productId = item.getProductId();
        this.quantity = item.getQuantity();
        this.selectedSize = item.getSelectedSize();
        this.selected = item.isSelected();
        this.price = product.getPrice();  // or product.getPrice()
        this.infoImage = product.getInfoImage();
    }
}
