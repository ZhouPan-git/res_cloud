package factory;

/**
 * @Author zp
 * @Date 2023/8/27 17:02
 * @PackageName:factory
 * @ClassName: PythonCource
 * @Description:
 * @Version 1.0
 */
public class PythonCourse implements ICourse {
    @Override
    public void record() {
        System.out.println("python");
    }
}