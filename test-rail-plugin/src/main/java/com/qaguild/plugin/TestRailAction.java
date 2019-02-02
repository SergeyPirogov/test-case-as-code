package com.qaguild.plugin;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.qaguild.plugin.model.TestCase;
import com.qaguild.plugin.util.PsiUtils;

public class TestRailAction extends AnAction {

    private Settings settings;

    @Override
    public void actionPerformed(AnActionEvent e) {
        final PsiElement element = e.getData(PlatformDataKeys.PSI_ELEMENT);
        if (element instanceof PsiMethod) {
            PsiMethod method = (PsiMethod) element;

            TestRailApiWrapper testRail = new TestRailApiWrapper(Settings.getInstance(method.getProject()));

            PsiClass testClass = (PsiClass) method.getParent();

            PsiAnnotation jiraAnnotation = method.getAnnotation(Annotations.JIRA_STORY_ANNOTATION);
            PsiAnnotation epicAnnotation = testClass.getAnnotation(Annotations.EPIC_ANNOTATION);

            String epicName = AnnotationUtil.getDeclaredStringAttributeValue(epicAnnotation, "value");

            String jiraId = AnnotationUtil.getDeclaredStringAttributeValue(jiraAnnotation, "id");
            String jiraTitle = AnnotationUtil.getDeclaredStringAttributeValue(jiraAnnotation, "title");

            String storyName = jiraId + " - " + jiraTitle;

            int sectionId = testRail.createSections(epicName, storyName);

            TestCase automatedCheck = testRail.createAutomatedCheck(sectionId, method);

            if (method.hasAnnotation(Annotations.MANUAL_CASE_ANNOTATION)) {
                testRail.createManualCheck(sectionId, method);
            }

            createCaseIdAnnotation(automatedCheck, method);

            Notifications.Bus.notify(new Notification("TestRail.Action",
                    "Export to TestTail",
                    "Finished exporting [" + method.getName() + "]",
                    NotificationType.INFORMATION));
        }
    }

    private void createCaseIdAnnotation(TestCase testCase, PsiMethod method) {
        final PsiAnnotation annotation = PsiUtils.createAnnotation(getCaseAnnotationText(testCase.getId()), method);
        final Project project = method.getProject();

        PsiAnnotation jiraAnnotation = method.getAnnotation(Annotations.JIRA_STORY_ANNOTATION);

        CommandProcessor.getInstance().executeCommand(project, () -> ApplicationManager.getApplication().runWriteAction(() -> {
            PsiUtils.addImport(method.getContainingFile(), Annotations.CASE_ID_ANNOTATION);

            method.getModifierList().addAfter(annotation, jiraAnnotation);
        }), "Insert TestRail id", null);
    }

    private String getCaseAnnotationText(int id) {
        return String.format("@%s(%s)", Annotations.CASE_ID_ANNOTATION, id);
    }
}
