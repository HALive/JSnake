package halive.jsnake;

import halive.jsnake.game.JSnakeGame;
import halive.nativeloader.NativeLoader;
import halive.nativeloader.NativeLoaderUtils;
import halive.util.PrintStreamHandler;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import javax.swing.ProgressMonitor;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class JSnake {

    public static final Logger logger = Logger.getLogger("JSnake");

    public static void main(String[] args) throws Exception {
        boolean devMode = false;
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("-native-debug")) {
                devMode = true;
            }
        }
        initializeLogger();
        initializeNatives(devMode);
        //loadConfig();
        launchGame();
    }

    private static void launchGame() {
        logger.info("Launching game....");
        try {
            AppGameContainer container = new AppGameContainer(new JSnakeGame("JSnake"), 800, 600, false);
            container.setVSync(true);
            container.setVerbose(true);
            container.start();
        } catch (SlickException e) {
            logger.log(Level.SEVERE, "An error occured.",e);
            System.exit(-1);
        }
    }

    private static void initializeLogger() {
        PrintStreamHandler handler = new PrintStreamHandler(System.out);
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
    }

    private static void initializeNatives(boolean devMode) {
        File nativesFolder = new File("natives");
        if (!devMode) {
            logger.log(Level.INFO, "Extracting Natives...");
            try {
                ProgressMonitor mon = new ProgressMonitor(null, "Extracting natives...", "", 0, 100);
                mon.setMillisToPopup(0);
                NativeLoader loader = new NativeLoader(JSnake.class, null);
                loader.copyNatives(nativesFolder, mon);
                mon.setNote("Updating Library Path");
                mon.close();
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error loading extracting natives", e);
                System.exit(-1);
            }
        }
        logger.log(Level.INFO, "Registering natives Folder.");
        try {
            NativeLoaderUtils.addLibraryPath(nativesFolder.toString());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not Register Natives. Aborting.", e);
            System.exit(-1);
        }
    }
}
