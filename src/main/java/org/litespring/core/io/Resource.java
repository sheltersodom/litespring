package org.litespring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @autor sheltersodom
 * @create 2021-02-05-17:29
 */
public interface Resource {
    InputStream getInputStream() throws IOException;

    String getDescription();
}
