package cn.mislily.gmall.cart.controller;


import cn.mislily.gmall.annotation.LoginRequire;
import cn.mislily.gmall.bean.CartInfo;
import cn.mislily.gmall.bean.SkuInfo;
import cn.mislily.gmall.service.CartService;
import cn.mislily.gmall.service.SkuService;
import cn.mislily.gmall.util.CookieUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Reference
    SkuService skuService;

    @Reference
    CartService cartService;

    /***
     * 订单系统中，必须登陆才能访问的方法
     * @param request
     * @param response
     * @param map
     * @return
     */
    @LoginRequire(ifNeedSuccess = true)
    @RequestMapping("toTrade")
    private String toTrade(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
        String userId = "";

        // 需要被单点登录的拦截器

        return "toTrade";
    }

    @LoginRequire(ifNeedSuccess = false)
    @RequestMapping("checkCart")
    private String checkCart(HttpServletRequest request,HttpServletResponse response,CartInfo cartInfo , ModelMap map) {


        List<CartInfo> cartInfos = new ArrayList<>();
        String userId = "";
        // 修改购物车的选中状态
        // 更新数据后将最新数据查询出来
        if(StringUtils.isBlank(userId)){
            // 更新cookie
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if(StringUtils.isNotBlank(cartListCookie)){
                cartInfos = JSON.parseArray(cartListCookie,CartInfo.class);
                for (CartInfo info : cartInfos) {
                    if(info.getSkuId().equals(cartInfo.getSkuId())){
                        info.setIsChecked(cartInfo.getIsChecked());
                    }
                }
            }
            CookieUtil.setCookie(request,response,"cartListCookie",JSON.toJSONString(cartInfos),60*60*24*7,true);
        }else{
            // 更新db和缓存
            cartInfo.setUserId(userId);
            cartService.updateCartChecked(cartInfo);
            cartInfos = cartService.getCartCache(userId);
        }

        map.put("cartList",cartInfos);
        map.put("totalPrice",getTotalPrice(cartInfos));
        return "cartListInner";
    }


    @LoginRequire(ifNeedSuccess = false)
    @RequestMapping("cartList")
    private String cartList(HttpServletRequest request , ModelMap map) {
        List<CartInfo> cartInfos = new ArrayList<>();

        String userId = "";

        if(StringUtils.isBlank(userId)){
            // 取cookie中的数据
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if(StringUtils.isNotBlank(cartListCookie)){
                cartInfos = JSON.parseArray(cartListCookie,CartInfo.class);
            }
        }else{
            // 去缓存数据
            cartInfos = cartService.getCartCache(userId);
        }

        map.put("cartList",cartInfos);
        map.put("totalPrice",getTotalPrice(cartInfos));
        return "cartList";
    }

    private BigDecimal getTotalPrice(List<CartInfo> cartInfos) {
        BigDecimal b = new BigDecimal("0");
        for (CartInfo cartInfo : cartInfos) {

            if(cartInfo.getIsChecked().equals("1")){
                b = b.add(cartInfo.getCartPrice());
            }

        }
        return b;
    }


    @LoginRequire(ifNeedSuccess = false)
    @RequestMapping("cartSuccess")
    public String cartSuccess(){

        return "success";
    }

    @LoginRequire(ifNeedSuccess = false)
    @RequestMapping("addToCart")
    public String addToCart(HttpServletRequest request, HttpServletResponse response, CartInfo cartInfo){
        String id = cartInfo.getSkuId();
        SkuInfo sku = skuService.skuInfo(id);

        cartInfo.setCartPrice(sku.getPrice().multiply(new BigDecimal(cartInfo.getSkuNum())));
        cartInfo.setIsChecked("1");
        cartInfo.setImgUrl(sku.getSkuDefaultImg());
        cartInfo.setSkuPrice(sku.getPrice());
        cartInfo.setSkuName(sku.getSkuName());

        String userId = "";
        List<CartInfo> cartInfos = new ArrayList<>();
        if(StringUtils.isBlank(userId)){
             // 用户未登陆，添加cookie
            String cartListCookieStr = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if(StringUtils.isBlank(cartListCookieStr)){
                cartInfos.add(cartInfo);
            }else{
                 cartInfos = new ArrayList<>();
                cartInfos = JSON.parseArray(cartListCookieStr, CartInfo.class);
                // 判断是否重复的sku
                boolean b = ifNewCart(cartInfos,cartInfo);
                if(b){
                    cartInfos.add(cartInfo);//添加

                }else{
                    for (CartInfo info : cartInfos) {
                        String skuId = info.getSkuId();
                        if(skuId.equals(cartInfo.getSkuId())){
                            info.setSkuNum(info.getSkuNum()+cartInfo.getSkuNum());
                            info.setCartPrice(info.getSkuPrice().multiply(new BigDecimal(info.getSkuNum())));
                        }
                    }
                }
            }

            // 操作完成后覆盖cookie
            CookieUtil.setCookie(request,response,"cartListCookie", JSON.toJSONString(cartInfos),60*60*24*7,true);

        }else{
            // 用户已登陆，添加db
            String skuId = cartInfo.getSkuId();
            // select * from catr_info where sku_id = skuId and user_id = userId
            cartInfo.setUserId(userId);
            CartInfo cartInfoDb = cartService.ifCartExists(cartInfo);

            if(cartInfoDb!=null){
                // 更新数据库
                cartInfoDb.setSkuNum(cartInfoDb.getSkuNum()+cartInfo.getSkuNum());
                cartInfoDb.setCartPrice(cartInfoDb.getSkuPrice().multiply(new BigDecimal(cartInfoDb.getSkuNum())));
                cartService.updateCart(cartInfoDb);
            }else{
                // 插入数据库
                cartService.saveCart(cartInfo);
            }

            // 同步缓存
            cartService.syncCache(userId);// skuInfo:skuId:info cart:userId:info (hash)

        }

        return "redirect:/cartSuccess";
    }

    private boolean ifNewCart(List<CartInfo> cartInfos, CartInfo cartInfo) {

        boolean b = true;
        for (CartInfo info : cartInfos) {
            String skuId = info.getSkuId();
            if(skuId.equals(cartInfo.getSkuId())){
                b = false;
            }
        }
        return b;
    }
}
