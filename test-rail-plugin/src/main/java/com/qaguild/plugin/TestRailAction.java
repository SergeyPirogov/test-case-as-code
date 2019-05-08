package com.qaguild.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.qaguild.plugin.model.TestCase;
import com.qaguild.plugin.util.NotificationUtils;
import com.qaguild.plugin.util.PsiMethodUtils;
import org.apache.commons.lang3.AnnotationUtils;

import java.util.List;
import java.util.Map;

public class TestRailAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final PsiElement element = e.getData(PlatformDataKeys.PSI_ELEMENT);
        if (element instanceof PsiMethod) {
            PsiMethod method = (PsiMethod) element;
            generateCases(method);
        }

        if (element instanceof PsiClass) {
            PsiMethod[] methods = ((PsiClass) element).getAllMethods();

            for (PsiMethod method : methods) {
                if (method.getAnnotation(Annotations.JIRA_STORY_ANNOTATION) != null) {
                    generateCases(method);
                }
            }
        }
    }

    private void generateCases(PsiMethod method) {
        TestRailApiWrapper testRail = new TestRailApiWrapper(Settings.getInstance(method.getProject()));

        List<String> classSections = PsiMethodUtils.getClassSections(method.getContainingClass());

        List<String> methodSections = PsiMethodUtils.getMethodSections(method);

        if(classSections.isEmpty() || methodSections.isEmpty()) {
            NotificationUtils.error("Can not find sections");
        }

        testRail.createSections(classSections);

        int sectionId = testRail.createSections(methodSections);

        Map<PsiAnnotation, TestCase> testCases = PsiMethodUtils.getManualTestCases(method);

        testCases.forEach((annotation, testCase) -> {
            Integer id = testRail.saveTestCase(sectionId, testCase).getId();
            PsiMethodUtils.createCaseAnnotation(id, method, annotation);
        });

        TestCase automatedCheck = testRail.createAutomatedCheck(sectionId, method);
        PsiMethodUtils.createCaseIdAnnotation(automatedCheck, method);

        NotificationUtils.notify(method.getName());
    }
}
