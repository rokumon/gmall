package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.CartInfo;
import java.util.List;

public interface CartService {

    public CartInfo ifCartExists(CartInfo cartInfo);

    public void updateCart(CartInfo cartInfoDb);

    public void saveCart(CartInfo cartInfo);

    public void syncCache(String userId);

    public List<CartInfo> getCartCache(String userId);

    public void updateCartChecked(CartInfo cartInfo);

}
