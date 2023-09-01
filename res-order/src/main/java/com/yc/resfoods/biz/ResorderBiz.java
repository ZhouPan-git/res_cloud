package com.yc.resfoods.biz;

import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.resfoods.model.CartItem;

import java.util.Set;

/**
 * @Author zp
 * @Date 2023/8/22 11:42
 * @PackageName:com.yc.resfoods.biz
 * @ClassName: ResorderBiz
 * @Description:
 * @Version 1.0
 */
public interface ResorderBiz {
    public int order(Resorder resorder, Set<CartItem> cartItems, Resuser resuser);
}