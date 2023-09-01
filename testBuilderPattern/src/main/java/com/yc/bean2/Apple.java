package com.yc.bean2;

import lombok.Builder;
import lombok.Data;

/**
 * @Author zp
 * @Date 2023/8/27 16:55
 * @PackageName:com.yc.bean2
 * @ClassName: Apple
 * @Description:
 * @Version 1.0
 */
@Data
@Builder
public class Apple {
    private String name;
    private Integer id;
}