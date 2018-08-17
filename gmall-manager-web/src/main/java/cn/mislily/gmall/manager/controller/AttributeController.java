package cn.mislily.gmall.manager.controller;

import cn.mislily.gmall.bean.BaseAttrInfo;
import cn.mislily.gmall.service.BaseAttrService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AttributeController {

    @Reference
    private BaseAttrService baseAttrService;


    //==== Page ====


    //==== Info ====

    /**
     *
     * @param catalog3Id
     * @return
     */
    @RequestMapping("attrInfoList/{catalog3Id}")
    @ResponseBody
    public List<BaseAttrInfo> attrInfoList(@PathVariable("catalog3Id")String catalog3Id){

        List<BaseAttrInfo> attrInfoList = baseAttrService.getBaseAttrInfoListByCatalog3Id(catalog3Id);

        return attrInfoList;
    }

    /**
     *
     * @param baseAttrInfoId
     * @return
     */
    @RequestMapping("deleteAttrInfo/{attrInfoId}")
    @ResponseBody
    public Boolean deleteAttrInfo(@PathVariable("attrInfoId")String baseAttrInfoId){
        baseAttrService.deleteAttributeInfoById(baseAttrInfoId);
        return true;
    }

    //==== Value ====

    /**
     *
     * @param baseAttrInfo
     * @return
     */
    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public Boolean saveAttrInfo(BaseAttrInfo baseAttrInfo){
        baseAttrService.saveBaseAttrInfo(baseAttrInfo);
        return true;
    }

    @RequestMapping("updateAttrInfo")
    @ResponseBody
    public Boolean updateAttrInfo(BaseAttrInfo baseAttrInfo){
        baseAttrService.updateBaseAttrInfo(baseAttrInfo);
        return true;
    }

}
