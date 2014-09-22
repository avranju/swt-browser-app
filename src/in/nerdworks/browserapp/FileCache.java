package in.nerdworks.browserapp;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FileCache {
    private String cacheBasePath;
    private Map<String, FileSource> fileSources;

    LoadingCache<String, byte[]> fileCache = CacheBuilder.newBuilder()
            .maximumSize(50)
            .build(new FileCacheLoader());

    public FileCache(Map<String, URL> sources) {
        // default cache base path to temp folder
        cacheBasePath = System.getProperty("java.io.tmpdir");

        // initialize file sources
        fileSources = Maps.transformEntries(sources, new Maps.EntryTransformer<String, URL, FileSource>() {
            @Override
            public FileSource transformEntry(String key, URL value) {
                return new FileSource(key, value);
            }
        });
    }

    public String getCacheBasePath() {
        return cacheBasePath;
    }

    public void setCacheBasePath(String cacheBasePath) {
        this.cacheBasePath = cacheBasePath;
    }

    public byte[] getFile(String fileName) throws ExecutionException {
        if (!fileSources.containsKey(fileName)) {
            return null;
        }

        return fileCache.get(fileName);
    }

    private class FileCacheLoader extends CacheLoader<String, byte[]> {
        @Override
        public byte[] load(String key) throws Exception {
            FileSource source = fileSources.get(key);

            // check if the file is available in the local file cache and
            // load from network if not
            File file = new File(cacheBasePath, source.getFileName());
            if (!file.exists()) {
                FileOutputStream output = new FileOutputStream(file);
                URLConnection connection = source.getUrl().openConnection();
                InputStream input = connection.getInputStream();
                ByteStreams.copy(input, output);
                input.close();
                output.close();
            }

            // load and return file contents
            FileInputStream input = new FileInputStream(file);
            byte[] data = ByteStreams.toByteArray(input);
            input.close();

            return data;
        }
    }
}
