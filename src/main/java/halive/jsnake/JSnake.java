/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.jsnake;

import halive.jsnake.config.JSnakeConfig;
import halive.jsnake.game.JSnakeGame;
import halive.jsnake.game.highscore.HighscoreEntry;
import halive.jsnake.game.highscore.HighscoreSerializer;
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

    public static final File CONFIG_FILE = new File("config.json");

    /**
     * Defines a Logger to Log Exceptions that might occur
     */
    public static final Logger logger = Logger.getLogger("JSnake");

    /**
     * Main Method:
     * Supported CommandLine Arguments:
     * -native-debug, if ths value is in the args array (at index 0) ths native Loader will not
     * Load The Natives out of a jar if they dont exist. it will just try to
     * add the "natives" folder to the java  Library Path
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        boolean devMode = false;
        boolean createEntries = false;
        if (args.length != 0) {
            for (String arg : args) {
                if (arg.equalsIgnoreCase("-native-debug")) {
                    devMode = true;
                } else if (arg.equalsIgnoreCase("-test-entries")) {
                    createEntries = true;
                }
            }
        }
        initializeLogger();
        initializeNatives(devMode);
        JSnakeConfig config = loadConfig();
        if (config == null) {
            logger.log(Level.ALL, "Closing the Application. No Config has been Loaded.");
            return;
        }
        if (createEntries) {
            generateTestEntries(config);
        }
        launchGame(config);
    }

    /**
     * Generates 100 Test Entries. for the Highscore list.
     *
     * @param config
     */
    private static void generateTestEntries(JSnakeConfig config) {
        for (int i = 0; i < 100; i++) {
            HighscoreEntry e = new HighscoreEntry("Test-" + i, (int) (Math.random() * 1000), config.getConfigSignature());
            HighscoreSerializer s = new HighscoreSerializer(e, config.getHighscoreFolder());
            s.start();
        }
    }

    /**
     * Loads the Configuration file
     */
    private static JSnakeConfig loadConfig() {
        logger.info("Loading Configuration File...");
        JSnakeConfig config = JSnakeConfig.getConfiguration(CONFIG_FILE);
        config.calculateSignature();
        File highscoreFolder = config.getHighscoreFolder();
        if (!highscoreFolder.exists()) {
            logger.info("Creating Highscore Folder: " + highscoreFolder.getName());
            highscoreFolder.mkdirs();
        }
        return config;
    }

    /**
     * Launches the game
     */
    private static void launchGame(JSnakeConfig cfg) {
        logger.info("Launching game....");
        try {
            int w = cfg.getWindowDimensions().width;
            int h = cfg.getWindowDimensions().height;
            AppGameContainer container = new AppGameContainer(new JSnakeGame(cfg), w, h, false);
            container.setVSync(true);
            container.setVerbose(true);
            container.setShowFPS(false);
            container.start();
        } catch (SlickException e) {
            logger.log(Level.ALL, "An error occured.", e);
            System.exit(-1);
        }
    }

    /**
     * Initializes the logger
     */
    private static void initializeLogger() {
        PrintStreamHandler handler = new PrintStreamHandler(System.out);
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
    }

    /**
     * Initializes the natives
     * If there is no folder Callled natives in the current Directory the nativeLoader will
     * extract all requiered natives into this directory.
     * After that the "natives" folder is added to javas library path.
     *
     * @param devMode if this is true the native loader will not extract any Natives. it will ust add the
     *                natives folder to the library path
     */
    private static void initializeNatives(boolean devMode) {
        File nativesFolder = new File("natives");
        if (!devMode) {
            logger.log(Level.INFO, "Extracting Natives...");
            try {
                ProgressMonitor mon = new ProgressMonitor(null, "Extracting natives...", "", 0, 100);
                mon.setMillisToPopup(0);
                NativeLoader loader = new NativeLoader(JSnake.class);
                loader.copyNatives(nativesFolder, mon);
                mon.setNote("Updating Library Path");
                mon.close();
            } catch (Exception e) {
                logger.log(Level.ALL, "Error loading extracting natives", e);
                System.exit(-1);
            }
        }
        logger.log(Level.INFO, "Registering natives Folder.");
        try {
            NativeLoaderUtils.addLibraryPath(nativesFolder.toString());
        } catch (Exception e) {
            logger.log(Level.ALL, "Could not Register Natives. Aborting.", e);
            System.exit(-1);
        }
    }
}
