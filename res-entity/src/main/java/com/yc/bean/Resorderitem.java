package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author zp
 * @Date 2023/8/18 16:09
 * @PackageName:com.yc
 * @ClassName: ResOrderItem
 * @Description:
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resorderitem implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer roiid;
    private Integer roid;
    private Integer fid;
    private Double dealprice;
    private Integer num;
}
