package org.litespring.web.context.support;

import org.litespring.context.support.AbstractApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;
import org.litespring.web.context.WebApplicationContext;

/**
 * @autor sheltersodom
 * @create 2021-04-14-20:32
 */
public class XmlWebApplicationContext extends AbstractApplicationContext implements WebApplicationContext {

    //TODO:处理文件位置的历史遗留问题
    private static final String DEFAULT_CONFIG_LOCATION = "D:\\Code\\litespringmvc\\out\\artifacts\\litespringmvc_Web_exploded\\WEB-INF\\applicationContext.xml";

    public XmlWebApplicationContext(String configFile, ClassLoader classLoader) {
        super(configFile, classLoader);
    }

    public XmlWebApplicationContext(String configFile) {
        super(configFile);
    }

    public XmlWebApplicationContext() {
        super(DEFAULT_CONFIG_LOCATION);
    }

    @Override
    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }

}
