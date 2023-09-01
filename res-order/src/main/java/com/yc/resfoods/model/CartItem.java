package com.yc.resfoods.model;

import com.yc.bean.Resfood;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author zp
 * @Date 2023/8/22 11:41
 * @PackageName:com.yc.resfoods.model
 * @ClassName: CartItem
 * @Description:
 * @Version 1.0
 */
@Data
public class CartItem implements Serializable {
    private Resfood food;
    private Integer num;
    private Double smallCount;

    public Double getSmallCount() {
        if (food!=null){
            smallCount=this.food.getRealprice()*this.num;
        }
        return smallCount;
    }
}