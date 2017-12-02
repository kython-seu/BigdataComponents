package BigdataComponents;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JarUtils {
    public static List<String> getJars(String path){
        List<String> pathLists = new ArrayList<String>();
        File file = new File(path);
        String paths = file.getAbsolutePath();
        System.out.println("paths " + paths);
        File direct = new File(paths);
        File[] files = direct.listFiles();

        for(File f : files){
            System.out.println("name " + f.getPath());
            pathLists.add(f.getPath());
        }

        return pathLists;
    }

    public static void main(String[] args) {
        JarUtils.getJars("/home/kason/workspace/BigdataComponents/spark-main/target/spark-main/WEB-INF/lib");
    }
}
