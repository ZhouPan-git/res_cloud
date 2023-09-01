package com.yc.resfoods.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.resfoods.biz.ResfoodBiz;
import com.yc.web.model.PageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author zp
 * @Date 2023/8/20 18:42
 * @PackageName:com.yc.resfoods.controller
 * @ClassName: ResfoodController
 * @Description:
 * @Version 1.0
 */
@RestController
@RequestMapping("resfood")
@Slf4j
@Api(tags = "菜品管理")
public class ResfoodController {
    @Autowired
    private ResfoodBiz resfoodBiz;

    //集合set，存线程
    public Set<Thread> set=new HashSet<Thread>();

//    @GetMapping("findById/{fid}")
//    @ApiOperation(value = "根据菜品编号查询操作")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "fid", value = "菜品号", required = true)
//    })
//    public Map<String, Object> findById(@PathVariable Integer fid,@RequestHeader String token){
//        System.out.println("token:"+token);
//        Map<String, Object> map = new HashMap<String, Object>();
//        Resfood rf=null;
//        try{
//            rf=this.resfoodBiz.findById(fid);
//        }catch (Exception e){
//            e.printStackTrace();
//            map.put("code",0);
//            map.put("message",e.getCause());
//            return map;
//        }
//        map.put("code",1);
//        map.put("obj",rf);
////        int flag=1;
////        if (flag==1){
////            throw new RuntimeException("sleuth 异常");
////        }
//        return map;
//    }

    @GetMapping("findById/{fid}")
    @ApiOperation(value = "根据菜品编号查询操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "菜品号", required = true)
    })
    public Map<String, Object> findById(@PathVariable Integer fid){
        Map<String, Object> map = new HashMap<String, Object>();
        Resfood rf=null;
        try{
            rf=this.resfoodBiz.findById(fid);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",0);
            map.put("message",e.getCause());
            return map;
        }
        map.put("code",1);
        map.put("obj",rf);
//        int flag=1;
//        if (flag==1){
//            throw new RuntimeException("sleuth 异常");
//        }
        return map;
    }

//    private Map<String,Object> exceptionFallback(Throwable t){
//        t.printStackTrace();
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("code",0);
//        map.put("msg","resource is under exception.Cause as following:"+t.getCause());
//        return map;
//    }

    @GetMapping("findAll")
    @ApiOperation(value = "查询所有菜品")
    @SentinelResource(value = "findAll",fallback = "exceptionFallback")
    public Map<String, Object> findAll(){
//        int flag=0;
//        if (flag==0){
//            throw new RuntimeException("查询所有业务异常");
//        }
        Thread thread=Thread.currentThread();
        set.add(thread);
        log.info("线程数为："+set.size()+",当前线程编号为："+thread.getId());
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Map<String, Object> map = new HashMap<String, Object>();
        List<Resfood> list=null;
        try{
            list=this.resfoodBiz.findAll();
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",0);
            map.put("message",e.getCause());
            return map;
        }
        map.put("code",1);
        map.put("obj",list);
        return map;
    }


//    private Map<String,Object> handleBlock(@RequestParam int pageno, @RequestParam int pagesize,
//                                          @RequestParam String sortby, @RequestParam String sort, BlockException exception){
//        exception.printStackTrace();
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("code",0);
//        map.put("msg","资源："+exception.getRuleLimitApp()+"被限流，规则为："+exception.getRule().toString());
//        return map;
//
//    }

    @RequestMapping(value ="findByPage", method = {RequestMethod.GET,RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageno",value = "页号",required = true),
            @ApiImplicitParam(name = "pagesize",value = "每页记录数",required = true),
            @ApiImplicitParam(name="sortby",value = "排序列",required = true),
            @ApiImplicitParam(name="sort",value = "排序方式",required = true)
    })
    @ApiOperation(value = "分页查询操作")
    @SentinelResource(value = "hotkey-page",blockHandler = "handleBlock")
    public Map<String,Object> findByPage(@RequestParam int pageno, @RequestParam int pagesize,
                                         @RequestParam String sortby, @RequestParam String sort){
        Map<String,Object> map = new HashMap<String,Object>();
        Page<Resfood> page=null;
        try {
            page=this.resfoodBiz.findByPage(pageno,pagesize,sortby,sort);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",0);
            map.put("message",e.getCause());
            return map;
        }
        map.put("code",1);
        //包装一个web model  用于web界面/app界面上显示结果
        PageBean pageBean=new PageBean();
        pageBean.setPageNo(pageno);
        pageBean.setSort(sort);
        pageBean.setSortby(sortby);
        pageBean.setTotal(page.getTotal());
        pageBean.setDataset(page.getRecords());
        //其他的分页数据
        //计总页数
        long totalPages=page.getTotal()%pageBean.getPageSize()==0?
                page.getTotal()/pageBean.getPageSize():page.getTotal()/pageBean.getPageSize()+1;
        pageBean.setTotalpages((int)totalPages);
        //上一页页号的计算
        if (pageBean.getPageNo()<=1){
            pageBean.setPre(1);
        }else {
            pageBean.setPre(pageBean.getPageNo()-1);
        }
        //下一页的页号
        if (pageBean.getPageNo()==totalPages){
            pageBean.setNext((int)totalPages);
        }else {
            pageBean.setNext(pageBean.getPageNo()+1);
        }
        map.put("data", pageBean);
        return map;
    }
}