package com.moyanshushe.utils.log;

/*
 * Author: Napbad
 * Version: 1.0
 */
import org.springframework.javapoet.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Set;


public class LoggableProcessor extends AbstractProcessor implements Processor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Loggable.class.getName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element element : annotatedElements) {
                if (element instanceof TypeElement) {
                    // 假设是TypeElement，即类或接口
                    TypeElement typeElement = (TypeElement) element;

                    // 生成代理类的名称，通常是在原类名后加上"$$LogProxy"
                    String proxyClassName = typeElement.getQualifiedName() + "$$LogProxy";

                    // 使用JavaPoet等库生成代理类的源代码
                    // 示例：生成代理类的骨架
                    TypeSpec.Builder proxyClassBuilder = TypeSpec.classBuilder(proxyClassName)
                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                            .addStaticBlock(CodeBlock.builder()
                                    .addStatement("LogUtil.init($S)", typeElement.getQualifiedName())
                                    .build());

                    // 添加静态log方法
                    MethodSpec.Builder logMethodBuilder = MethodSpec.methodBuilder("log")
                            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                            .returns(void.class)
                            .addParameter(String.class, "message")
                            .addParameter(ParameterizedTypeName.get(ArrayTypeName.of(Object.class).getClass()), "args");

                    // 方法体逻辑，调用LogUtil.log
                    logMethodBuilder.addStatement("LogUtil.log(LogUtil.LogLevel.INFO, $S, args)", "$L")
                            .build();

                    proxyClassBuilder.addMethod(logMethodBuilder.build());

    //                // 最后，生成Java文件并写入磁盘
    //                JavaFile.builder(packageNameOf(typeElement), proxyClassBuilder.build())
    //                        .build()
    //                        .writeTo(processingEnv.getFiler());
    //            }
                }
            }
            return true;
        }
        return true;
    }
}