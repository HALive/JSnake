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

/**
 * The Native Loader Extracts nativeFiles like .dll or .so from a jar file
 * and copies them into a external natives Folder.
 * <p>
 * The Status of the loading Prograss can be displayed using a ProgressMonition
 * <p>
 * Currently the Programm cannot Handle Folders wit Letters like ö, ä or ü
 */
public class NativeLoader {

    /**
     * Stores a Static list of valid File Endings.
     * It Contians the following file endings:
     * - .dll
     * - .so
     * - .dylib
     */
    public static String[] VALID_ENDINGS = new String[]{"dll", "so", "dylib"};

    private JarFile jarFile;

    /**
     * Creates a new NativeLoader
     *
     * @param resourceClass The Jar File that contains this Class will be used as a Jar File to extract natives from
     * @throws IOException
     */
    public NativeLoader(Class resourceClass) throws IOException {
        String cName = resourceClass.getName();
        String resName = cName.replace('.', '/') + ".class";
        String[] path = getClass().getClassLoader().getResource(resName).getPath().replace("file:/", "").replace("%20", " ").split("!");
        if (path.length != 2) {
            throw new IOException("Invalid Resource Path or the program vas not started from a JAR");
        }
        loadFromString(path[0]);
    }

    /**
     * Creates a new NativeLoader from a Jar File Given as a File Object
     *
     * @param nativesFile
     * @throws IOException
     */
    public NativeLoader(File nativesFile) throws IOException {
        loadFromString(nativesFile.getPath());
    }

    /**
     * Creates a new NativeLoader using the given string as the Filepath
     *
     * @param filePath
     * @throws IOException
     */
    public NativeLoader(String filePath) throws IOException {
        loadFromString(filePath);
    }

    private void loadFromString(String path) throws IOException {
        String jarPath = path;
        if (!(System.getProperty("os.name").toLowerCase().contains("windows"))) {
            jarPath = "/" + jarPath;
        }
        File f = new File(jarPath);
        JarFile file = new JarFile(f);
        jarFile = file;
    }

    /**
     * Returns a list of Valid JarFileEntries that
     *
     * @return
     */
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

    /**
     * Copies the Natives in the Previously defined JarFile into the given Folder.
     * The ProgressMonitor can be used to Monitor the Progress of the Copying operation
     *
     * @param folder The folder to Copy the natives to.
     * @param mon    the ProgressMonitor to display the current status with. is ths is null no ProgressMonitor is used
     * @throws IOException
     */
    public void copyNatives(File folder, ProgressMonitor mon) throws IOException {
        if (folder.isFile()) {
            return;
        }
        if (!folder.exists()) {
            folder.mkdirs();
        }
        ArrayList<JarEntry> validFiles = getValidFiles();
        if (mon != null) {
            mon.setMaximum(validFiles.size());
        }
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

    /**
     * Same as copyNatives(File, ProgressMonitor) ist just sets the PRogresmonitor to null
     *
     * @param folder
     * @throws IOException
     */
    public void copyNatives(File folder) throws IOException {
        copyNatives(folder, null);
    }

    /**
     * Checks if the Given JarEntry is a Valid native
     *
     * @param entry
     * @return true if the JarEntry is a Valid NAtive (has a valid File Ending.)
     */
    public boolean isValidFile(JarEntry entry) {
        for (String s : VALID_ENDINGS) {
            if (entry.getName().toLowerCase().endsWith("." + s)) {
                return true;
            }
        }
        return false;
    }
}
