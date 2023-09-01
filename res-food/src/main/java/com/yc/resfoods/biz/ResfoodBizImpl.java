package com.yc.resfoods.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.resfoods.dao.ResfoodDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author zp
 * @Date 2023/8/20 15:59
 * @PackageName:com.yc.resfoods.biz
 * @ClassName: ResfoodBizImpl
 * @Description:
 * @Version 1.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS,
               isolation = Isolation.DEFAULT,timeout = 2000,
                readOnly = true,rollbackFor = RuntimeException.class)
@Slf4j
public class ResfoodBizImpl implements ResfoodBiz{
    @Autowired
    private ResfoodDao resfoodDao;

    @Override
    public List<Resfood> findAll() {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.orderByDesc("fid");
        return this.resfoodDao.selectList(wrapper);
    }

    @Override
    public Resfood findById(Integer fid) {
        return this.resfoodDao.selectById(fid);
    }

    @Override
    public Page<Resfood> findByPage(int pageNO, int pagesize, String sortby, String sort) {
        QueryWrapper wrapper=new QueryWrapper();
        if ("asc".equalsIgnoreCase(sort)){
            wrapper.orderByAsc(sortby);
        }else {
            wrapper.orderByDesc(sortby);
        }
        //Page是mybatis-plus提供的分页组件，拼接sql的分页语句
        //因为不同的数据库使用的分页语句不同
        Page<Resfood> page=new Page<>(pageNO, pagesize);
        //执行分页查询 可以采用map提供的selectPage（）来完成分页 wrapper 可以完成排序条件通过page中的pageno，pagesize就知道如何分页
        Page<Resfood> resfoodPage=this.resfoodDao.selectPage(page,wrapper);
        log.info("总记录数="+resfoodPage.getTotal());
        log.info("总页数="+resfoodPage.getPages());
        log.info("当前页码="+resfoodPage.getCurrent());
        return resfoodPage;
    }
}