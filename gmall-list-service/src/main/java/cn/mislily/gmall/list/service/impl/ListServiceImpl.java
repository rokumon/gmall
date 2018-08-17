package cn.mislily.gmall.list.service.impl;

import cn.mislily.gmall.bean.SkuLsInfo;
import cn.mislily.gmall.bean.SkuLsParam;
import cn.mislily.gmall.service.ListService;
import com.alibaba.dubbo.config.annotation.Service;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ListServiceImpl implements ListService {

    @Autowired
    JestClient jestClient;

    /**
     *
     * @param skuLsParam
     * @return
     */
    public List<SkuLsInfo> search(SkuLsParam skuLsParam){

        List<SkuLsInfo> skuLsInfoList = new ArrayList<>();

        Search search = new Search.Builder(getMyDsl(skuLsParam)).addIndex("gmall").addType("SkuLsInfo").build();

        try {
            SearchResult execute = jestClient.execute(search);

            List<SearchResult.Hit<SkuLsInfo, Void>> hits = execute.getHits(SkuLsInfo.class);

            for (SearchResult.Hit<SkuLsInfo, Void> hit : hits) {
                SkuLsInfo source = hit.source;
                skuLsInfoList.add(source);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return skuLsInfoList;

    }


    public  String getMyDsl(SkuLsParam skuLsParam) {

        String keyword = skuLsParam.getKeyword();
        String[] valueId = skuLsParam.getValueId();
        String catalog3Id = skuLsParam.getCatalog3Id();

        // 创建一个dsl工具对象
        SearchSourceBuilder dsl = new SearchSourceBuilder();

        // 创建一个先过滤后搜索的query对象
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        // query对象过滤语句
        if(StringUtils.isNotBlank(catalog3Id)){
            TermsQueryBuilder t = new TermsQueryBuilder("catalog3Id", catalog3Id);// 并集
            boolQueryBuilder.filter(t);
        }

        if(null!=valueId&&valueId.length>0){
            for (int i = 0; i < valueId.length; i++) {
                TermsQueryBuilder t = new TermsQueryBuilder("skuAttrValueList.valueId", valueId[i]);// 并集
                boolQueryBuilder.filter(t);
            }
        }

//          // 并集过滤
//        String[] s = new String[2];
//        s[0] = "51";
//        s[1] = "54";
//        TermsQueryBuilder t4 = new TermsQueryBuilder("skuAttrValueList.valueId", s);// 并集
//        boolQueryBuilder.filter(t4);

        // query对象搜索语句
        if(StringUtils.isNotBlank(keyword)){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }

        // 将query和from和size放入dsl
        dsl.query(boolQueryBuilder);
        dsl.size(100);
        dsl.from(0);

        System.out.println(dsl.toString());
        return dsl.toString();
    }
}
