package com.yc.bean;

import lombok.Data;

/**
 * @Author zp
 * @Date 2023/8/23 15:31
 * @PackageName:com.yc.bean
 * @ClassName: Contributor
 * @Description:
 * @Version 1.0
 */
@Data
public class Contributor {
    String login;
    int contributions;
    int id;
    String node_id;
    String avatar_url;
    String gravatar_id;
    String url;
    String html_url;
    String followers_url;
}