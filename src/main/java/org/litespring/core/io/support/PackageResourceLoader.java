package org.litespring.core.io.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;
import org.litespring.utils.Assert;
import org.litespring.utils.ClassUtils;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @autor sheltersodom
 * @create 2021-02-10-17:00
 */
public class PackageResourceLoader {
    private static final Log logger = LogFactory.getLog(PackageResourceLoader.class);
    private final ClassLoader classLoader;

    public PackageResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public PackageResourceLoader(ClassLoader classLoader) {
        Assert.notNull(classLoader, "ResourceLoader must not be null");
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }


    public Resource[] getResources(String basePackage) {
        Assert.notNull(basePackage, "basePackage must not be null");
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader c1 = getClassLoader();
        //获取根目录
        URL url = c1.getResource(location);
        File rootDir = new File(url.getFile());

        Set<File> matchingFiles = retrieveMatchFiles(rootDir);
        Resource[] result = new Resource[matchingFiles.size()];

        int i = 0;
        for (File file : matchingFiles) {
            result[i++] = new FileSystemResource(file);
        }
        return result;
    }

    public String[] getResourcesPath(String basePackage) {
        String location = ClassUtils.convertClassNameToResourcePath(basePackage);
        ClassLoader c1 = getClassLoader();
        //获取根目录
        URL url = c1.getResource(location);
        File rootDir = new File(url.getFile());
        String path = rootDir.getAbsolutePath();
        String locationReplaced = location.replace('/', File.separatorChar);
        String parentPath = path.replace(locationReplaced, "");

        Resource[] resources = getResources(basePackage);

        String[] result = new String[resources.length];

        int i = 0;
        for (Resource resource : resources) {
            result[i++] = resource.getDescription().
                    replace(parentPath, "").
                    replace(".class", "")
                    .replace(File.separatorChar, '/');
        }
        return result;
    }

    protected Set<File> retrieveMatchFiles(File rootDir) {
        if (!rootDir.exists()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Skipping [" + rootDir.getAbsolutePath() + "] because it does not exist");
            }
            return Collections.EMPTY_SET;
        }
        if (!rootDir.isDirectory()) {
            if (logger.isWarnEnabled()) {
                logger.warn("Skipping [" + rootDir.getAbsolutePath() + "] because it does not denote a directory");
            }
            return Collections.EMPTY_SET;
        }
        if (!rootDir.canRead()) {
            if (logger.isWarnEnabled()) {
                logger.warn("Cannot search for matching files underneath directory [" + rootDir.getAbsolutePath() +
                        "] because the application is not allowed to read the directory");
            }
            return Collections.emptySet();
        }

        Set<File> result = new HashSet<>(8);
        doRetrieveMatchingFiles(rootDir, result);
        return result;

    }

    protected void doRetrieveMatchingFiles(File dir, Set<File> result) {
        File[] dirContents = dir.listFiles();
        if (dirContents == null) {
            if (logger.isWarnEnabled()) {
                logger.warn("Could not retrieve contents of directory [" + dir.getAbsolutePath() + "]");
            }
            return;
        }
        for (File content : dirContents) {
            if (content.isDirectory()) {
                if (!dir.canRead()) {
                    if (logger.isWarnEnabled()) {
                        logger.debug("Skipping subdirectory [" + dir.getAbsolutePath() +
                                "] because the application is not allowed to read the directory");
                    }
                } else {
                    doRetrieveMatchingFiles(content, result);
                }
            } else {
                result.add(content);
            }
        }
    }
}
