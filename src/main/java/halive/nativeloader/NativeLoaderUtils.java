package halive.nativeloader;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * This Class implements some Important Utility Function that might be useful in
 * Combination with the nativeLoader
 */
public class NativeLoaderUtils {

    /**
     * This Method adds a Given String to the Classpath
     *
     * @param s
     * @throws Exception
     */
    public static void addPathToClasspath(String s) throws Exception {
        File f = new File(s);
        URI u = f.toURI();
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> urlClass = URLClassLoader.class;
        Method method = urlClass.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(urlClassLoader, u.toURL());
    }

    /**
     * Adds the Given LibraryPath, thsi is USeful to add the NativesFolder
     * Created by the NativeLoader to the LibraryPath during Runtime.
     *
     * @param pathToAdd
     * @throws Exception
     */
    public static void addLibraryPath(String pathToAdd) throws Exception {
        final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
        usrPathsField.setAccessible(true);
        final String[] paths = (String[]) usrPathsField.get(null);
        for (String path : paths) {
            if (path.equals(pathToAdd)) {
                return;
            }
        }
        final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
        newPaths[newPaths.length - 1] = pathToAdd;
        usrPathsField.set(null, newPaths);
    }

    /**
     * This Recursively Removes the GIven folder
     *
     * @param folder the Folder to delete
     */
    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) { //some JVMs return null for empty dirs
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
}
