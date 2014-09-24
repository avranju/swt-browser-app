package in.nerdworks.browserapp;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FileCache {
    private String cacheBasePath;
    private Map<String, FileSource> fileSources;

    LoadingCache<String, File> fileCache = CacheBuilder.newBuilder()
            .maximumSize(50)
            .build(new FileCacheLoader());

    public FileCache(FileSource[] sources) {
        // default cache base path to temp folder
        cacheBasePath = System.getProperty("java.io.tmpdir");

        // initialize file sources
        fileSources = new HashMap<String, FileSource>(sources.length);
        for(FileSource source : sources) {
            fileSources.put(source.getFileName(), source);
        }
    }

    public String getCacheBasePath() {
        return cacheBasePath;
    }

    public void setCacheBasePath(String cacheBasePath) {
        this.cacheBasePath = cacheBasePath;
    }

    public File getFile(String fileName) throws ExecutionException {
        if (!fileSources.containsKey(fileName)) {
            return null;
        }

        return fileCache.get(fileName);
    }

    private class FileCacheLoader extends CacheLoader<String, File> {
        @Override
        public File load(String key) throws Exception {
            FileSource source = fileSources.get(key);

            // check if the file is available in the local file cache and
            // load from network if not
            File file = new File(cacheBasePath, source.getFileName());
            if (!file.exists()) {
                file.createNewFile();
                FileOutputStream output = new FileOutputStream(file);
                URLConnection connection = source.getUrl().openConnection();
                InputStream input = connection.getInputStream();
                ByteStreams.copy(input, output);
                input.close();
                output.close();
            }

            return file;
        }
    }
}
