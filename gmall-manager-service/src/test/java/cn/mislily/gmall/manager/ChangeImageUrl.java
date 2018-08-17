package cn.mislily.gmall.manager;

import cn.mislily.gmall.bean.SkuImage;
import cn.mislily.gmall.bean.SpuImage;
import cn.mislily.gmall.manager.mapper.SkuImageMapper;
import cn.mislily.gmall.manager.mapper.SpuImageMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan(basePackages = "cn.mislily.gmall.manager")
public class ChangeImageUrl {

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Test
    public void changeUrl() {

        String baseUrl = "http://192.168.127.133";

        List<SkuImage> skuImageList = skuImageMapper.selectAll();

        if (skuImageList != null) {
            for (SkuImage skuImage : skuImageList) {
                skuImage.setImgUrl(baseUrl + handleUrl(skuImage.getImgUrl()));
                skuImageMapper.updateByPrimaryKey(skuImage);
            }
        }

        List<SpuImage> spuImageList = spuImageMapper.selectAll();

        if (spuImageList!=null){
            for (SpuImage spuImage : spuImageList) {
                spuImage.setImgUrl(baseUrl + handleUrl(spuImage.getImgUrl()));
                spuImageMapper.updateByPrimaryKey(spuImage);
            }
        }
    }

    public static String handleUrl(String url) {
        int startIndex = url.indexOf("group");
        return url.substring(startIndex - 1);
    }

}
