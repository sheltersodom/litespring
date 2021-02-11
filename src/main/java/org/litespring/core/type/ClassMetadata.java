package org.litespring.core.type;

/**
 * @autor sheltersodom
 * @create 2021-02-10-23:19
 */
public interface ClassMetadata {
    String getClassName();

    boolean isInterface();

    boolean isAbstract();

    boolean isFinal();

    boolean hasSuperClass();

    String getSuperClassName();

    String[] getInterfaceNames();
}
