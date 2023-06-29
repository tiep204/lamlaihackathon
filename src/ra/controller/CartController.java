package ra.controller;

import ra.model.CartItem;
import ra.service.CartService;

import java.util.List;

public class CartController implements IController<CartItem,Integer> {
    private CartService cartService = new CartService();

    @Override
    public List<CartItem> getAll() {
        return cartService.getAll();
    }

    @Override
    public void save(CartItem cartItem) {
        cartService.save(cartItem);
    }

    @Override
    public CartItem findById(Integer integer) {
        return cartService.findById(integer);
    }

    @Override
    public void delete(Integer integer) {
        cartService.delete(integer);
    }
    public CartItem findByProductId(String id){
        return cartService.findByProductId(id);
    }
    public void changeQuantity(CartItem cartItem){
        cartService.changeQuantity(cartItem);
    }
    public  int getNewCartItemId(){
        return cartService.getNewCartItemId();
    }
}
