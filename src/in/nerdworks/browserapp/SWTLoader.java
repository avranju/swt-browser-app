package in.nerdworks.browserapp;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class SWTLoader {
    final String BASE_URL = "https://github.com/avranju/swt-browser-app/blob/master/lib/swt/";
    private String swtJarUrl;
    private FileCache filesCache;
    private final String jarName;

    public SWTLoader() throws MalformedURLException {
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
        filesCache = new FileCache(new FileSource[] {
           new FileSource("swt.jar", new URL(BASE_URL + jarName))
        });
    }

    public File loadJar() throws ExecutionException {
        return filesCache.getFile("swt.jar");
    }
}
