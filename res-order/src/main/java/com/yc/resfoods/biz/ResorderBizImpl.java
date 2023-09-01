package com.yc.resfoods.biz;

import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.bean.Resuser;
import com.yc.resfoods.dao.ResorderDao;
import com.yc.resfoods.dao.ResorderitemDao;
import com.yc.resfoods.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @Author zp
 * @Date 2023/8/22 11:42
 * @PackageName:com.yc.resfoods.biz
 * @ClassName: ResorderBizImpl
 * @Description:
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,
               isolation = Isolation.DEFAULT,timeout = 2000,
                readOnly = false,rollbackFor = RuntimeException.class)
@Slf4j
public class ResorderBizImpl implements ResorderBiz{
    @Autowired
    private ResorderDao resorderDao;
    @Autowired
    private ResorderitemDao resorderitemDao;

    @Override
    public int order(Resorder resorder, Set<CartItem> cartItems, Resuser resuser) {
        resorder.setUserid(resuser.getUserid());
        this.resorderDao.insert(resorder);
        for (CartItem cartItem : cartItems) {
            Resorderitem roi=new Resorderitem();
            roi.setRoid(resorder.getRoid());
            roi.setFid(cartItem.getFood().getFid());
            roi.setNum(cartItem.getNum());
            roi.setDealprice(cartItem.getFood().getRealprice());
            this.resorderitemDao.insert(roi);
        }
        return 1;
    }
}