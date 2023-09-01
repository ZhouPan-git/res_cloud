package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author zp
 * @Date 2023/8/18 16:10
 * @PackageName:com.yc
 * @ClassName: ResUser
 * @Description:
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resuser implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer userid;
    private String username;
    private String pwd;
    private String email;
}
