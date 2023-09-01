package factory;

/**
 * @Author zp
 * @Date 2023/8/27 17:01
 * @PackageName:factory
 * @ClassName: JavaCource
 * @Description:
 * @Version 1.0
 */
public class JavaCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("java");
    }
}