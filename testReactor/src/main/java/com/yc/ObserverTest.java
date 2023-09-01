package com.yc;

import java.util.Observable;

/**
 * @Author zp
 * @Date 2023/8/27 21:02
 * @PackageName:com.yc
 * @ClassName: ObserverDemo
 * @Description:
 * @Version 1.0
 */
public class ObserverTest {
    public static void main(String[] args) {
        ObserverDemo observerDemo = new ObserverDemo(); // 创建被观察的对象

        // 注册观察者A
//        observerDemo.addObserver(new Observer(){
//        @Override
//        public void update(Observable o,Object arg){
//            System.out.println(o + "数据发生变化A: " + arg);
//        }
//        });

        observerDemo.addObserver((o, arg) -> {
            System.out.println(o + "数据发生变化A: " + arg);
        });

        // 注册观察者B
        observerDemo.addObserver((o, arg) -> {
            System.out.println(o + "数据发生变化B: " + arg);
        });

        // 更新数据并通知观察者
        observerDemo.updateNumber();
        observerDemo.updateNumber();

    }
}
class ObserverDemo extends Observable {
    private int i = 0;

    public void updateNumber() {
        i++;
        this.setChanged();
        this.notifyObservers(i);
    }
}