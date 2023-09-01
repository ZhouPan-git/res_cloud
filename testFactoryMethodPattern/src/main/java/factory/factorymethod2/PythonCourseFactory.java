package factory.factorymethod2;

import factory.ICourse;
import factory.PythonCourse;

/**
 * @Author zp
 * @Date 2023/8/27 18:31
 * @PackageName:factory.factorymethod2
 * @ClassName: PythonCourseFactory
 * @Description:
 * @Version 1.0
 */
public class PythonCourseFactory implements ICourseFactory{
    @Override
    public ICourse create() {
        return new PythonCourse();
    }
}