package com.qaguild.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.qaguild.plugin.model.TestCase;
import com.qaguild.plugin.util.NotificationUtils;
import com.qaguild.plugin.util.PsiMethodUtils;

import java.util.Map;

public class TestRailAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final PsiElement element = e.getData(PlatformDataKeys.PSI_ELEMENT);
        if (element instanceof PsiMethod) {
            PsiMethod method = (PsiMethod) element;

            TestRailApiWrapper testRail = new TestRailApiWrapper(Settings.getInstance(method.getProject()));

            String storyName = PsiMethodUtils.getStoryName(method);
            String epicName = PsiMethodUtils.getEpicName(method);

            int sectionId = testRail.createSections(epicName, storyName);

            Map<PsiAnnotation, TestCase> testCases = PsiMethodUtils.getManualTestCases(method);

            testCases.forEach((annotation, testCase) -> {
                Integer id = testRail.saveTestCase(sectionId, testCase).getId();
                PsiMethodUtils.createCaseAnnotation(id, method, annotation);

                NotificationUtils.notify(testCase.getTitle());
            });

            TestCase automatedCheck = testRail.createAutomatedCheck(sectionId, method);
            PsiMethodUtils.createCaseIdAnnotation(automatedCheck, method);

            NotificationUtils.notify(method.getName());
        }
    }
}
