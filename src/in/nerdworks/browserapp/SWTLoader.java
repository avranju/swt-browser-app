package in.nerdworks.browserapp;

import java.net.URL;
import java.util.HashMap;

public class SWTLoader {
    final String BASE_URL = "https://github.com/avranju/swt-browser-app/blob/master/lib/swt/";
    private String swtJarUrl;

    public SWTLoader() {
        // swt-4.4-win32-win32-x86/swt.jar?raw=true
        String osName = System.getProperty("os.name").toLowerCase();
        boolean isWindows = osName.contains("win");
        boolean isMac = osName.contains("mac");
        boolean isLinux = osName.contains("linux");

        String jarName = "swt-4.4-" +
                (isWindows ? "win32-win32-" :
                 isMac ? "cocoa-macosx-" :
                 isLinux ? "gtk-linux-" : "");
    }
}
