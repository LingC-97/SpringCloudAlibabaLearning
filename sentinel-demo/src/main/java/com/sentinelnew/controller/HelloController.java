package com.sentinelnew.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.sentinelnew.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class HelloController {
    private static final String ESOURCE_NAME = "hello";
    private static final String USER_RESOURCE_NAME = "user";
    private static final String DEGRADE_RESOURCE_NAME = "degrade";


    @GetMapping(value = "/hello")
    public String hello() {
        Entry entry = null;
        try {
//            sentinel 针对资源进行限制
            entry = SphU.entry(ESOURCE_NAME);
//            被保护的业务逻辑
            String str = "hello world";
            log.info("=====" + str + "=====");
            return str;


        } catch (BlockException e) {
//            资源访问组织，被限流或者被降级
            log.info("block!");
            return "被流控了！";
        } catch (Exception e) {
//            若需要配置降级规则，需要通过这种方式记录业务异常
            Tracer.traceEntry(e, entry);
//            e.printStackTrace();
        } finally {
//            若需要配置降级规则，需要通过这种方式记录业务异常
            if (entry != null) {
                entry.exit();
            }
        }
        return null;

    }


    //    和xml中的这个语句是一样的作用<bean init-method>。这是spring的注解
    // @PostConstruct  /*这个注解的作用：在helloController这个bean在创建之后，会初始调用下面这个方法*/

    @PostConstruct
    public static void initFlowRules() {
        //流控
        List<FlowRule> rules = new ArrayList<>();
//
        FlowRule rule = new FlowRule();
//      为哪个资源进行流控
        rule.setResource(ESOURCE_NAME);
//        设置流控规则QPS

        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //        设置受保护的资源阈值、set limitQPS to 20
        rule.setCount(1);//每秒钟只访问一次是可以的，多了就会被流控
        rules.add(rule);

        FlowRule rule2 = new FlowRule();
        rule2.setResource(USER_RESOURCE_NAME);
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule2.setCount(1);
        rules.add(rule2);
        FlowRuleManager.loadRules(rules);
    }


    //    和xml中的这个语句是一样的作用<bean init-method>。这是spring的注解
    @PostConstruct  /*这个注解的作用：在helloController这个bean在创建之后，会初始调用下面这个方法*/
    public void initDegradeRule() {
//        降级规则,异常
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource(DEGRADE_RESOURCE_NAME);
//        设置规则侧率，异常数
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
//        触发熔断的异常数：2
        degradeRule.setCount(2);
//        触发熔断最小请求数，触发两次，才熔断，实际开发中要比2次多
        degradeRule.setMinRequestAmount(2);
//        统计时长，多长时间段内请求了两次就给熔断
        degradeRule.setStatIntervalMs(60 * 1000);  //1分钟
//        这三个组合起来的意思就是： 一分钟内执行了两次，出现了2次异常，就会触发熔断
//        熔断持续时长 ；10秒
//        在这10秒内，一旦触发了熔断，再次请求对应的接口就会直接调用降级方法
//        10秒过后，处于搬开状态，恢复接口请求调用，如果第一次调用就异常，再次熔断，不会根据设置的条件进行判定
        degradeRule.setTimeWindow(10);

        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);
    }




/*     @SentinelResource改善接口中资源定义和北流空降级后的处理方法
1.添加依赖<>sentinel-annotation-aspectj
2.配置bean-（SentinelResourceAspect）
        value定义资源(设置资源，之前是用rule.setResource来设置)
        blockHandler 设置流控降级后的处理方法（默认该方法必须声明在同一个类）
        如果不想再同一个类中，那就设置blockHandlerClass=xxx.Class， 但是方法必须是static
        fallback 当接口出现了异常，就可以交给fallback指定的方法进行处理
        如果不想再同一个类中，那就设置fallbackClass， 但是方法必须是static
3.优先级
        如果blockHander 和fallback同时指定了，则 blockHandler优先级会更高
        用exceptionsTognore 排除哪些异常不被处理
* */
    /*
blockHandler方法有注意的地方
1.注意一定要pubic
2.返回值要和原方法保持一致(比如说这个方法public User getUser(String id)，返回值就是User)，包含原方法的参数
3.可以在参数最后添加BlockException区分是什么规则的处理方法
* */

    @RequestMapping("/user")
    @SentinelResource(value = USER_RESOURCE_NAME, fallback = "fallbackHandlerForGetUser",
            /* exceptionsToIgnore = {ArithmeticException.class},*/
            blockHandler = "blockHandlerForGetUser")
    public User user(String id) {
//        int a = 1/0;
        return new User("xuxu");

    }

    public User blockHandlerForGetUser(String id, BlockException e) {
        e.printStackTrace();
        return new User("流控!!");
    }

    public User fallbackHandlerForGetUser(String id, Throwable e) {
        e.printStackTrace();
        return new User("异常处理");
    }

    @RequestMapping("/degrade")
    @SentinelResource(value = DEGRADE_RESOURCE_NAME, entryType = EntryType.IN,
            blockHandler = "blockHandlerForFb")
    public User degrade(String id) throws InterruptedException {
        throw new RuntimeException("异常!!");
    }


    public User blockHandlerForFb(String id, BlockException e) {
        return new User("熔断降级");
    }


}
