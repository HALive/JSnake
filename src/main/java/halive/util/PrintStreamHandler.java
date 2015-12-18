/*
 * Copyright (c) 2015, HALive
 * For Licence Information see Licence File.
 */

package halive.util;

import java.io.PrintStream;
import java.util.logging.ConsoleHandler;

public class PrintStreamHandler extends ConsoleHandler {

    public PrintStreamHandler(PrintStream s) {
        setOutputStream(s);
    }
}
