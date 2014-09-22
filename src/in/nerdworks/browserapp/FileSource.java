package in.nerdworks.browserapp;

import java.net.URL;

public class FileSource {
    private String fileName;
    private URL url;

    public FileSource(String fileName, URL url) {
        this.fileName = fileName;
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
