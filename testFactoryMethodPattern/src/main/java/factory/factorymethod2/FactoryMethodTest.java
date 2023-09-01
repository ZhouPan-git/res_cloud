package factory.factorymethod2;

import factory.ICourse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author zp
 * @Date 2023/8/27 18:30
 * @PackageName:factory.factorymethod2
 * @ClassName: FactoryMethodTest
 * @Description:
 * @Version 1.0
 */
public class FactoryMethodTest {
    public static void main(String[] args) {
        ICourseFactory factory = new PythonCourseFactory();
        ICourse course = factory.create();
        course.record();

        factory = new JavaCourseFactory();
        course = factory.create();
        course.record();

// java.util.List 就是工厂方法模式的一个应用，List 接口有不同的实现
        List l = new ArrayList<>();
        List ll = new LinkedList<>();
        ll.iterator();

    }
}