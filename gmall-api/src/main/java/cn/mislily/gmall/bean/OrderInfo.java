package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.enums.PaymentWay;
import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @param
 * @return
 */
public class OrderInfo implements Serializable, DataBaseUpdateEntity {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String consignee;

    @Column
    private String consigneeTel;

    @Column
    private BigDecimal totalAmount;

    @Column
    private String orderStatus;

    @Column
    private String processStatus;

    @Column
    private String userId;

    @Column
    private PaymentWay paymentWay;

    @Column
    private Date expireTime;

    @Column
    private String deliveryAddress;

    @Column
    private String orderComment;

    @Column
    private Date createTime;

    @Column
    private String parentOrderId;

    @Column
    private String trackingNo;

    @Transient
    private List<OrderDetail> orderDetailList;

    @Transient
    private List<OrderInfo> orderSubList;

    @Transient
    private String wareId;

    @Column
    private String outTradeNo;

    public void sumTotalAmount() {
        BigDecimal totalAmount = new BigDecimal("0");
        for (OrderDetail orderDetail : orderDetailList) {
            totalAmount = totalAmount.add(orderDetail.getOrderPrice().multiply(new BigDecimal(orderDetail.getSkuNum())));
        }
        this.totalAmount = totalAmount;

    }


    public String getTradeBody() {
        OrderDetail orderDetail = orderDetailList.get(0);
        String tradeBody = orderDetail.getSkuName() + "等" + orderDetailList.size() + "件商品";
        return tradeBody;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = (id == "" ? null : id);
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = (consignee == "" ? null : consignee);
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = (consigneeTel == "" ? null : consigneeTel);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = (orderStatus == "" ? null : orderStatus);
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = (processStatus == "" ? null : processStatus);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = (userId == "" ? null : userId);
    }

    public PaymentWay getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(PaymentWay paymentWay) {
        this.paymentWay = paymentWay;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = (deliveryAddress == "" ? null : deliveryAddress);
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderComment) {
        this.orderComment = (orderComment == "" ? null : orderComment);
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getParentOrderId() {
        return parentOrderId;
    }

    public void setParentOrderId(String parentOrderId) {
        this.parentOrderId = (parentOrderId == "" ? null : parentOrderId);
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = (trackingNo == "" ? null : trackingNo);
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public List<OrderInfo> getOrderSubList() {
        return orderSubList;
    }

    public void setOrderSubList(List<OrderInfo> orderSubList) {
        this.orderSubList = orderSubList;
    }

    public String getWareId() {
        return wareId;
    }

    public void setWareId(String wareId) {
        this.wareId = (wareId == "" ? null : wareId);
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = (outTradeNo == "" ? null : outTradeNo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderInfo orderInfo = (OrderInfo) o;

        if (id != null ? !id.equals(orderInfo.id) : orderInfo.id != null) return false;
        if (consignee != null ? !consignee.equals(orderInfo.consignee) : orderInfo.consignee != null) return false;
        if (consigneeTel != null ? !consigneeTel.equals(orderInfo.consigneeTel) : orderInfo.consigneeTel != null)
            return false;
        if (totalAmount != null ? !totalAmount.equals(orderInfo.totalAmount) : orderInfo.totalAmount != null)
            return false;
        if (orderStatus != null ? !orderStatus.equals(orderInfo.orderStatus) : orderInfo.orderStatus != null)
            return false;
        if (processStatus != null ? !processStatus.equals(orderInfo.processStatus) : orderInfo.processStatus != null)
            return false;
        if (userId != null ? !userId.equals(orderInfo.userId) : orderInfo.userId != null) return false;
        if (paymentWay != orderInfo.paymentWay) return false;
        if (expireTime != null ? !expireTime.equals(orderInfo.expireTime) : orderInfo.expireTime != null) return false;
        if (deliveryAddress != null ? !deliveryAddress.equals(orderInfo.deliveryAddress) : orderInfo.deliveryAddress != null)
            return false;
        if (orderComment != null ? !orderComment.equals(orderInfo.orderComment) : orderInfo.orderComment != null)
            return false;
        if (createTime != null ? !createTime.equals(orderInfo.createTime) : orderInfo.createTime != null) return false;
        if (parentOrderId != null ? !parentOrderId.equals(orderInfo.parentOrderId) : orderInfo.parentOrderId != null)
            return false;
        if (trackingNo != null ? !trackingNo.equals(orderInfo.trackingNo) : orderInfo.trackingNo != null) return false;
        if (orderDetailList != null ? !orderDetailList.equals(orderInfo.orderDetailList) : orderInfo.orderDetailList != null)
            return false;
        if (orderSubList != null ? !orderSubList.equals(orderInfo.orderSubList) : orderInfo.orderSubList != null)
            return false;
        if (wareId != null ? !wareId.equals(orderInfo.wareId) : orderInfo.wareId != null) return false;
        return outTradeNo != null ? outTradeNo.equals(orderInfo.outTradeNo) : orderInfo.outTradeNo == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (consignee != null ? consignee.hashCode() : 0);
        result = 31 * result + (consigneeTel != null ? consigneeTel.hashCode() : 0);
        result = 31 * result + (totalAmount != null ? totalAmount.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (processStatus != null ? processStatus.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (paymentWay != null ? paymentWay.hashCode() : 0);
        result = 31 * result + (expireTime != null ? expireTime.hashCode() : 0);
        result = 31 * result + (deliveryAddress != null ? deliveryAddress.hashCode() : 0);
        result = 31 * result + (orderComment != null ? orderComment.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (parentOrderId != null ? parentOrderId.hashCode() : 0);
        result = 31 * result + (trackingNo != null ? trackingNo.hashCode() : 0);
        result = 31 * result + (orderDetailList != null ? orderDetailList.hashCode() : 0);
        result = 31 * result + (orderSubList != null ? orderSubList.hashCode() : 0);
        result = 31 * result + (wareId != null ? wareId.hashCode() : 0);
        result = 31 * result + (outTradeNo != null ? outTradeNo.hashCode() : 0);
        return result;
    }
}
