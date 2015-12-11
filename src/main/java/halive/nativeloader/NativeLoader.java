package halive.nativeloader;

import javax.swing.ProgressMonitor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class NativeLoader {

    public static String[] VALID_ENDINGS = new String[]{"dll", "so", "dylib"};

    private JarFile jarFile;

    public NativeLoader(Class resourceClass, String[] resourceNames) throws IOException {
        String cName = resourceClass.getName();
        String resName = cName.replace('.', '/') + ".class";
        String[] path = getClass().getClassLoader().getResource(resName).getPath().replace("file:/", "").replace("%20", " ").split("!");
        if (path.length != 2) {
            throw new IOException("Invalid Resource Path or the program vas not started from a JAR");
        }
        String jarPath = path[0];
        if (!(System.getProperty("os.name").toLowerCase().contains("windows"))) {
            jarPath = "/" + jarPath;
        }
        File f = new File(jarPath);
        JarFile file = new JarFile(f);
        jarFile = file;
    }

    public ArrayList<JarEntry> getValidFiles() {
        ArrayList<JarEntry> list = new ArrayList<>();
        Enumeration e = jarFile.entries();
        while (e.hasMoreElements()) {
            JarEntry entry = (JarEntry) e.nextElement();
            if (isValidFile(entry)) {
                list.add(entry);
            }
        }
        return list;
    }

    public void copyNatives(File folder, ProgressMonitor mon) throws IOException {
        if (folder.isFile()) return;
        if (!folder.exists()) folder.mkdirs();
        ArrayList<JarEntry> validFiles = getValidFiles();
        if (mon != null) mon.setMaximum(validFiles.size());
        int pos = 0;
        for (JarEntry entry : validFiles) {
            String[] namePath = entry.getName().split("/");
            String filename = namePath[namePath.length - 1];
            File nativeFile = new File(folder.getPath() + "/" + filename);
            if (mon != null) {
                mon.setProgress(pos);
                mon.setNote("Extracting " + filename);
            }
            if (!nativeFile.exists()) {
                InputStream in = jarFile.getInputStream(entry);
                FileOutputStream out = new FileOutputStream(nativeFile);
                int data = 0;
                while ((data = in.read()) != -1) {
                    out.write(data);
                }
                out.close();
                in.close();
            }
            pos++;
        }
    }

    public void copyNatives(File folder) throws IOException {
        copyNatives(folder, null);
    }

    public boolean isValidFile(JarEntry entry) {
        for (String s : VALID_ENDINGS) {
            if (entry.getName().toLowerCase().endsWith("." + s)) {
                return true;
            }
        }
        return false;
    }
}
