package utils;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URL;

public class ProjectUtil {

    /**
     * @param path relative path to src\test\resources
     * @return
     */
    @Nullable
    public static File getFileFromClassPath(String path) {
        URL resource = ProjectUtil.class.getClassLoader().getResource(path);
        if (resource != null) {

            File file = new File(resource.getFile());
            return file;
        }
        return null;
    }
}
