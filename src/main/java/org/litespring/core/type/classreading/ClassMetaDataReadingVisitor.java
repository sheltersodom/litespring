package org.litespring.core.type.classreading;


import jdk.internal.org.objectweb.asm.Opcodes;
import org.litespring.core.type.ClassMetadata;
import org.litespring.utils.ClassUtils;
import org.objectweb.asm.ClassVisitor;

import static org.objectweb.asm.Opcodes.ASM4;


/**
 * @autor sheltersodom
 * @create 2021-02-10-18:39
 */
public class ClassMetaDataReadingVisitor extends ClassVisitor implements ClassMetadata {
    private String className;
    private boolean isInterface;
    private boolean isAbstract;
    private boolean isFinal;
    private String superClassName;
    private String[] interfaces;

    public ClassMetaDataReadingVisitor() {
        super(ASM4);
    }


    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = ClassUtils.convertResourcePathToClassName(name);
        this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);
        this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0);
        this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);
        if (superName != null) {
            this.superClassName = ClassUtils.convertResourcePathToClassName(superName);
        }
        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }

    public String getClassName() {
        return className;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public boolean hasSuperClass() {
        return this.superClassName != null;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public String[] getInterfaceNames() {
        return interfaces;
    }
}
