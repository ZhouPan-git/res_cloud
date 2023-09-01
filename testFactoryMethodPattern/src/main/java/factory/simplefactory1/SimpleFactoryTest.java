package factory.simplefactory1;

import factory.JavaCourse;

/**
 * @Author zp
 * @Date 2023/8/27 18:21
 * @PackageName:factory.simplefactory1
 * @ClassName: SimpleFactoryTest
 * @Description:
 * @Version 1.0
 */
public class SimpleFactoryTest {
    public static void main(String[] args) {
        CourseFactory cf=new CourseFactory();
        JavaCourse jc= (JavaCourse) cf.create(JavaCourse.class);
//        JavaCourse jc= (JavaCourse)cf.create("java");
        System.out.println( jc );

    }
}