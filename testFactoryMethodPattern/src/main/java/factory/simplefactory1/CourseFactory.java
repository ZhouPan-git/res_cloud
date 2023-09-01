package factory.simplefactory1;

import factory.ICourse;

/**
 * @Author zp
 * @Date 2023/8/27 18:20
 * @PackageName:factory.simplefactory1
 * @ClassName: CourseFactory
 * @Description:
 * @Version 1.0
 */
public class CourseFactory {
//    public ICourse create(String name){
//        if("java".equals(name)){
//            return new JavaCourse();
//        }else if("python".equals(name)){
//            return new PythonCourse();
//        }else {
//        return null;
//        }
//    }

    public  ICourse create(String className) {
        try {
            if (className != null && !"".equals(className)) {
                return (ICourse) Class.forName(className).newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ICourse create(Class<? extends ICourse> clazz) {
        try {
            if (clazz != null) {
                return clazz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}