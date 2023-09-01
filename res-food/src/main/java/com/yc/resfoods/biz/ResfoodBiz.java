package com.yc.resfoods.biz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;

import java.util.List;

/**
 * @Author zp
 * @Date 2023/8/20 15:55
 * @PackageName:com.yc.resfoods.biz
 * @ClassName: ResfoodBiz
 * @Description:
 * @Version 1.0
 */
public interface ResfoodBiz {
    public List<Resfood> findAll();
    public Resfood findById(Integer fid);
    public Page<Resfood> findByPage(int pageNO,int pagesize,String sortby,String sort);
}