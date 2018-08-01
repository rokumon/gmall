package cn.mislily.gmall.manager.controller;

import cn.mislily.gmall.bean.BaseAttrInfo;
import cn.mislily.gmall.service.AttributeService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AttributeController {

    @Reference
    private AttributeService attributeService;


    //Page


    //Info
    @RequestMapping("attrInfoList/{catalog3Id}")
    @ResponseBody
    public List<BaseAttrInfo> attrInfoList(@PathVariable("catalog3Id")String catalog3Id){

        List<BaseAttrInfo> attrInfoList = attributeService.attributeInfoList(catalog3Id);

        return attrInfoList;
    }


    @RequestMapping("deleteAttrInfo/{attrInfoId}")
    @ResponseBody
    public Boolean deleteAttrInfo(@PathVariable("attrInfoId")String attrInfoId){

        attributeService.deleteAttributeInfo(attrInfoId);

        return true;
    }


    //Value
    @RequestMapping("saveAttrInfo")
    @ResponseBody
    public Boolean saveAttrInfo(BaseAttrInfo baseAttrInfo){
        attributeService.saveAttribute(baseAttrInfo);
        return true;
    }

}
