package org.litespring.core.io;

import org.litespring.utils.Assert;

import java.io.*;

/**
 * @autor sheltersodom
 * @create 2021-02-05-17:38
 */
public class FileSystemResource implements Resource {
    private String path;
    private File file;

    public FileSystemResource(String path) {
        Assert.notNull(path, "path must not be null");
        this.file = new File(path);
        this.path = path;
    }

    public FileSystemResource(File file) {
        Assert.notNull(file, "file must not be null");
        this.file = file;
        this.path = file.getPath();
    }


    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public String getDescription() {
        return this.file.getAbsolutePath();
    }
}
