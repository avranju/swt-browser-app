package in.nerdworks.browserapp;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class SWTLoader {
    private static final String BASE_URL = "https://github.com/avranju/swt-browser-app/blob/master/lib/swt/";
    private static final String BROWSER_UTIL_JAR_URL = "https://github.com/avranju/swt-browser-app/blob/master/out/artifacts/swt_browser_app_jar/browser-util.jar?raw=true";
    private static final String SWT_JAR_NAME = "swt.jar";
    private static final String BROWSER_UTIL_JAR_NAME = "browser-util.jar";
    private static FileCache filesCache;
    private static String jarName;

    static {
        // swt-4.4-win32-win32-x86/swt.jar?raw=true
        String osName = System.getProperty("os.name").toLowerCase();
        boolean isWindows = osName.contains("win");
        boolean isMac = osName.contains("mac");
        boolean isLinux = osName.contains("linux");
        boolean isx64 = System.getProperty("os.arch").toLowerCase().contains("64");

        jarName = "swt-4.4-" +
                (isWindows ? "win32-win32-" :
                        isMac ? "cocoa-macosx-" :
                                isLinux ? "gtk-linux-" : "") +
                (isx64 ? "x86_x64" : "x86") +
                "/swt.jar?raw=true";
    }

    public static File[] loadJars() throws ExecutionException, MalformedURLException {
        if(filesCache == null) {
            filesCache = new FileCache(new FileSource[] {
                    new FileSource(SWT_JAR_NAME, new URL(BASE_URL + jarName)),
                    new FileSource(BROWSER_UTIL_JAR_NAME, new URL(BROWSER_UTIL_JAR_URL))
            });
        }

        return new File[] {
                filesCache.getFile(SWT_JAR_NAME),
                filesCache.getFile(BROWSER_UTIL_JAR_NAME)
        };
    }
}
