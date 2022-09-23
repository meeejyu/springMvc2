package hello.itemservice.domain.item;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Item {

    private Long id;
    
    @NotNull
    private String itemName;
    
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
