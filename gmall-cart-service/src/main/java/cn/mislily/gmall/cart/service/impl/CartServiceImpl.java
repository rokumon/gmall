package cn.mislily.gmall.cart.service.impl;

import cn.mislily.gmall.bean.CartInfo;
import cn.mislily.gmall.cart.mapper.CartInfoMapper;
import cn.mislily.gmall.service.CartService;
import cn.mislily.gmall.util.RedisUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartInfoMapper cartInfoMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public CartInfo ifCartExists(CartInfo cartInfo) {

        CartInfo cartInfo1 = new CartInfo();
        cartInfo1.setUserId(cartInfo.getUserId());
        cartInfo1.setSkuId(cartInfo.getSkuId());
        CartInfo select = cartInfoMapper.selectOne(cartInfo1);

        return select;
    }

    @Override
    public void updateCart(CartInfo cartInfoDb) {
        cartInfoMapper.updateByPrimaryKeySelective(cartInfoDb);
    }

    @Override
    public void saveCart(CartInfo cartInfo) {
        cartInfoMapper.insertSelective(cartInfo);
    }

    @Override
    public void syncCache(String userId) {

        Jedis jedis = redisUtil.getJedis();

        CartInfo cartInfo = new CartInfo();
        cartInfo.setUserId(userId);
        List<CartInfo> select = cartInfoMapper.select(cartInfo);

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        for (CartInfo info : select) {
            stringStringHashMap.put(info.getId(), JSON.toJSONString(info));
        }

        jedis.hmset("carts:" + userId + ":info", stringStringHashMap);

        jedis.close();

    }

    @Override
    public List<CartInfo> getCartCache(String userId) {

        List<CartInfo> cartInfos = new ArrayList<>();
        Jedis jedis = redisUtil.getJedis();

        List<String> hvals = jedis.hvals("carts:" + userId + ":info");

        if (hvals != null && hvals.size() > 0) {
            for (String hval : hvals) {
                CartInfo cartInfo = JSON.parseObject(hval, CartInfo.class);
                cartInfos.add(cartInfo);
            }

        }

        return cartInfos;
    }

    @Override
    public void updateCartChecked(CartInfo cartInfo) {

        // update table set a = 1 , b = 2 where id = ? and id2 = ?

        Example e = new Example(CartInfo.class);
        e.createCriteria().andEqualTo("skuId", cartInfo.getSkuId()).andEqualTo("userId", cartInfo.getUserId());

        cartInfoMapper.updateByExampleSelective(cartInfo, e);

        syncCache(cartInfo.getUserId());
    }
}
