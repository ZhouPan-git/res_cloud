package com.yc;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zp
 * @Date 2023/8/25 13:57
 * @PackageName:com.yc
 * @ClassName: Test
 * @Description:
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
            initFlowRules();//初始化sentinel流量规则的代码
            while (true) {
                Entry entry = null;
                try {
                    entry = SphU.entry("HelloWorld");
                    /*您的业务逻辑 - 开始*/
                    System.out.println("hello world");
                    /*您的业务逻辑 - 结束*/
                } catch (BlockException e1) {
                    /*流控逻辑处理 - 开始*/
                    System.out.println("block!");
                    /*流控逻辑处理 - 结束*/
                } finally {
                    if (entry != null) {
                        entry.exit();
                    }
                }
            }
        }
    private static void initFlowRules(){
        //规则的容器
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");//resourcename资源名    sentinel API
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);//设定流控类型
        // Set limit QPS to 20.
        rule.setCount(200000);//阈值 超过每秒200000次
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}