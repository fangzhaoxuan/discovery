package io.discovery.pddDemo;


import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.*;
import com.pdd.pop.sdk.http.api.response.*;

import java.util.Arrays;
import java.util.List;

/**
 * Demo 拼多多
 *
 * @author fzx
 * @date 2019/1/23
 */
public class PopClientDemo {

  public static void main(String[] args) throws Exception {

    String clientId = "63995b6677f74e48adefd32cb47433f2";
    String clientSecret = "dd16bf1286658dca051388946f0c498b16054356";
    PopClient client = new PopHttpClient(clientId, clientSecret);


    //商品查询（不含推广链接）
    PddDdkGoodsSearchRequest goodsSearchRequest = new PddDdkGoodsSearchRequest();
    goodsSearchRequest.setKeyword("鞋子");
    goodsSearchRequest.setOptId(0L);
    goodsSearchRequest.setPage((int) 1L);
    goodsSearchRequest.setPageSize((int) 10L);
    goodsSearchRequest.setSortType((int) 0L);

    PddDdkGoodsSearchResponse goodsSearchResponse = client.syncInvoke(goodsSearchRequest);
    PddDdkGoodsSearchResponse.GoodsSearchResponse result = goodsSearchResponse.getGoodsSearchResponse();
    List<PddDdkGoodsSearchResponse.GoodsListItem> list = result.getGoodsList();
    for (int i = 0; i < list.size(); i++) {
      PddDdkGoodsSearchResponse.GoodsListItem item = list.get(i);
    }

    //单个商品生成推广链接(进商品详情时生成，可以自定义参数custom_parameters，如用户ID)
    PddDdkGoodsPromotionUrlGenerateRequest goodsPromotionUrlGenerateRequest = new PddDdkGoodsPromotionUrlGenerateRequest();
    goodsPromotionUrlGenerateRequest.setPId("testStr");
    goodsPromotionUrlGenerateRequest.setGoodsIdList(Arrays.asList(0L));

    PddDdkGoodsPromotionUrlGenerateResponse goodsPromotionUrlGenerateResponse = client.syncInvoke(goodsPromotionUrlGenerateRequest);
    PddDdkGoodsPromotionUrlGenerateResponse.GoodsPromotionUrlGenerateResponse goodsPromotionUrlResponse = goodsPromotionUrlGenerateResponse.getGoodsPromotionUrlGenerateResponse();
    List<PddDdkGoodsPromotionUrlGenerateResponse.GoodsPromotionUrlListItem> urlList = goodsPromotionUrlResponse.getGoodsPromotionUrlList();
    for (int i = 0; i < urlList.size(); i++) {
      PddDdkGoodsPromotionUrlGenerateResponse.GoodsPromotionUrlListItem item = urlList.get(i);
    }

    //商品详情
    PddDdkOrderDetailGetRequest orderDetailGetRequest = new PddDdkOrderDetailGetRequest();
    orderDetailGetRequest.setOrderSn("546543123");
    PddDdkOrderDetailGetResponse orderDetailGetResponse = client.syncInvoke(orderDetailGetRequest);
    PddDdkOrderDetailGetResponse.OrderDetailResponse orderDetailResponse = orderDetailGetResponse.getOrderDetailResponse();


    //热销产品
    PddDdkTopGoodsListQueryRequest topGoodsListQueryRequest = new PddDdkTopGoodsListQueryRequest();
    topGoodsListQueryRequest.setPId("testStr");
    topGoodsListQueryRequest.setLimit((int) 10L);

    PddDdkTopGoodsListQueryResponse topGoodsListQueryResponse = client.syncInvoke(topGoodsListQueryRequest);
    PddDdkTopGoodsListQueryResponse.TopGoodsListGetResponse topGoodsListGetResponse = topGoodsListQueryResponse.getTopGoodsListGetResponse();
    List<PddDdkTopGoodsListQueryResponse.ListItem> topList = topGoodsListGetResponse.getList();
    for (int i = 0; i < topList.size(); i++) {
      PddDdkTopGoodsListQueryResponse.ListItem item = topList.get(i);
    }

    //增量订单获取
    PddDdkOrderListIncrementGetRequest incrementGetRequest = new PddDdkOrderListIncrementGetRequest();
    incrementGetRequest.setStartUpdateTime(System.currentTimeMillis()-(24*60*60));
    incrementGetRequest.setEndUpdateTime(System.currentTimeMillis());
    incrementGetRequest.setPageSize((int) 10L);
    incrementGetRequest.setPage((int) 1L);
    incrementGetRequest.setReturnCount(false);

    PddDdkOrderListIncrementGetResponse incrementGetResponse = client.syncInvoke(incrementGetRequest);
    PddDdkOrderListIncrementGetResponse.OrderListGetResponse orderListGetResponse = incrementGetResponse.getOrderListGetResponse();
    List<PddDdkOrderListIncrementGetResponse.OrderListItem> orderList = orderListGetResponse.getOrderList();
    for (int i = 0; i < orderList.size(); i++) {
      PddDdkOrderListIncrementGetResponse.OrderListItem item = orderList.get(i);
    }
  }
}



