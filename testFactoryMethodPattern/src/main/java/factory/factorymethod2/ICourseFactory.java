package factory.factorymethod2;

import factory.ICourse;

/**
 * @Author zp
 * @Date 2023/8/27 18:31
 * @PackageName:factory.factorymethod2
 * @ClassName: ICourseFactory
 * @Description:
 * @Version 1.0
 */
public interface ICourseFactory {
    ICourse create();
}