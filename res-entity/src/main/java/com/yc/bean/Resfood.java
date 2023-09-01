package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CORBA.IDLType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author zp
 * @Date 2023/8/18 15:30
 * @PackageName:com.yc
 * @ClassName: ResFood
 * @Description:
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resfood implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer fid;
    private String fname;
    private Double normprice;
    private Double realprice;
    private String detail;
    private String fphoto;

    //点赞数  redis提供 不是数据库的  mybatis在操作时将其忽略
    @TableField(select = false)
    private Long praise;
}
