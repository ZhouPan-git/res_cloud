package factory.factorymethod2;

import factory.ICourse;
import factory.JavaCourse;

/**
 * @Author zp
 * @Date 2023/8/27 18:31
 * @PackageName:factory.factorymethod2
 * @ClassName: JavaCourseFactory
 * @Description:
 * @Version 1.0
 */
public class JavaCourseFactory implements ICourseFactory{
    @Override
    public ICourse create() {
        return new JavaCourse();
    }
}