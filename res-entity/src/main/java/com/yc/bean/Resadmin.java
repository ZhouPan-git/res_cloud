package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author zp
 * @Date 2023/8/18 15:28
 * @PackageName:com.yc
 * @ClassName: ResAdmin
 * @Description:
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resadmin implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer raid;
    private String raname;
    private String rapwd;
}
