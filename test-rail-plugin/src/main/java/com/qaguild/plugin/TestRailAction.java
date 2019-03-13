package com.qaguild.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.qaguild.plugin.model.TestCase;
import com.qaguild.plugin.util.NotificationUtils;
import com.qaguild.plugin.util.PsiMethodUtils;
import com.qaguild.plugin.util.PsiUtils;

import java.util.Map;

import static com.qaguild.plugin.Annotations.MANUAL_CASE_ANNOTATION;

public class TestRailAction extends AnAction {

    private Settings settings;

    @Override
    public void actionPerformed(AnActionEvent e) {
        final PsiElement element = e.getData(PlatformDataKeys.PSI_ELEMENT);
        if (element instanceof PsiMethod) {
            PsiMethod method = (PsiMethod) element;

            TestRailApiWrapper testRail = new TestRailApiWrapper(Settings.getInstance(method.getProject()));

            String storyName = PsiMethodUtils.getStoryName(method);
            String epicName = PsiMethodUtils.getEpicName(method);

            int sectionId = testRail.createSections(epicName, storyName);

            if (method.hasAnnotation(MANUAL_CASE_ANNOTATION)) {
                Map<PsiAnnotation, TestCase> testCases = PsiMethodUtils.getManualTestCases(method);

                testCases.forEach((key, value) -> {
                    Integer id = testRail.saveTestCase(sectionId, value).getId();
                    PsiMethodUtils.createCaseAnnotation(id, method, key);

                    NotificationUtils.notify(value.getTitle());
                });
            }

            TestCase automatedCheck = testRail.createAutomatedCheck(sectionId, method);
            PsiMethodUtils.createCaseIdAnnotation(automatedCheck, method);

            NotificationUtils.notify(method.getName());
        }
    }
}
