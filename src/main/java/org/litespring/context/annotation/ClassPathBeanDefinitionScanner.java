package org.litespring.context.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.BeanNameGenerator;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.core.io.support.PackageResourceLoader;
import org.litespring.core.type.classreading.MetadataReader;
import org.litespring.core.type.classreading.SimpleMetadataReader;
import org.litespring.stereotype.Component;
import org.litespring.utils.StringUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @autor sheltersodom
 * @create 2021-02-10-23:46
 */
public class ClassPathBeanDefinitionScanner {
    private final BeanDefinitionRegistry registry;
    private PackageResourceLoader resourceLoader = new PackageResourceLoader();
    protected final Log logger = LogFactory.getLog(getClass());
    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String packageToScan) {
        String[] basePackages = StringUtils.tokenizeToStringArray(packageToScan, ",");
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                beanDefinitions.add(candidate);
                registry.registerBeanDefinition(candidate.getID(), candidate);
            }
        }
        return beanDefinitions;
    }

    private Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        String[] resourcesPaths = this.resourceLoader.getResourcesPath(basePackage);
        for (String resourcesPath : resourcesPaths) {
            try {
                MetadataReader metadataReader = new SimpleMetadataReader(resourcesPath);

                if (metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())) {
                    ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
                    String beanName = this.beanNameGenerator.generateBeanName(sbd, this.registry);
                    sbd.setId(beanName);
                    candidates.add(sbd);
                }
            } catch (IOException e) {
                throw new BeanDefinitionStoreException("Failed to read candidate component class " + resourcesPath, e);
            }
        }
        return candidates;
    }
}
