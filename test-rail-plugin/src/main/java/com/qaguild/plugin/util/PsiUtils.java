package com.qaguild.plugin.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;

import java.util.Optional;

public class PsiUtils {

    public static String getAnnotationAttribute(PsiAnnotation annotation, String  value){
        return annotation.findDeclaredAttributeValue(value).getText();
    }

    public static PsiAnnotation createAnnotation(final String annotation, final PsiElement context) {
        final PsiElementFactory factory = PsiElementFactory.SERVICE.getInstance(context.getProject());
        return factory.createAnnotationFromText(annotation, context);
    }

    public static void addImport(final PsiFile file, final String qualifiedName) {
        if (file instanceof PsiJavaFile) {
            addImport((PsiJavaFile) file, qualifiedName);
        }
    }

    public static void addImport(final PsiJavaFile file, final String qualifiedName) {
        final Project project = file.getProject();
        Optional<PsiClass> possibleClass = Optional.ofNullable(JavaPsiFacade.getInstance(project)
                .findClass(qualifiedName, GlobalSearchScope.everythingScope(project)));
        possibleClass.ifPresent(psiClass -> JavaCodeStyleManager.getInstance(project).addImport(file, psiClass));
    }

}
