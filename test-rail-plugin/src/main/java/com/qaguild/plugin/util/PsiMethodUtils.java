package com.qaguild.plugin.util;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.qaguild.enums.CaseState;
import com.qaguild.plugin.Annotations;
import com.qaguild.plugin.model.TestCase;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.qaguild.plugin.Annotations.*;

public class PsiMethodUtils {

    public static String getStoryName(PsiMethod method) {
        PsiAnnotation jiraAnnotation = method.getAnnotation(JIRA_STORY_ANNOTATION);

        String jiraId = AnnotationUtil.getDeclaredStringAttributeValue(jiraAnnotation, "value");
        String jiraTitle = AnnotationUtil.getDeclaredStringAttributeValue(jiraAnnotation, "title");

        if (StringUtils.isBlank(jiraTitle)) {
            return jiraId;
        }

        return jiraId + ": " + jiraTitle;
    }

    public static String getEpicName(PsiMethod method) {
        PsiClass testClass = (PsiClass) method.getParent();
        PsiAnnotation epicAnnotation = testClass.getAnnotation(EPIC_ANNOTATION);
        return AnnotationUtil.getDeclaredStringAttributeValue(epicAnnotation, "value");
    }

    public static Map<PsiAnnotation, TestCase> getManualTestCases(PsiMethod testMethod) {
        Map<PsiAnnotation, TestCase> testCases = new HashMap<>();

        String jiraRef = PsiMethodUtils.getJiraRef(testMethod);
        PsiAnnotationMemberValue[] cases = getManualCases(testMethod);

        for (PsiAnnotationMemberValue manualCase : cases) {
            PsiAnnotation caseAnnotation = (PsiAnnotation) manualCase;
            String title = AnnotationUtil.getDeclaredStringAttributeValue(caseAnnotation, "title");
            String ac = AnnotationUtil.getDeclaredStringAttributeValue(caseAnnotation, "ac");

            TestCase testCase = new TestCase();
            testCase.setTitle(getTestCaseTitle(title));
            testCase.setCustomState(CaseState.MANUAL.getValue());
            testCase.setCustomAcceptanceCriteria(ac);
            testCase.setRefs(jiraRef);

            testCases.put(caseAnnotation, testCase);
        }

        return testCases;
    }

    public static String getTestCaseTitle(String testMethodName) {
        String[] split = StringUtils.splitByCharacterTypeCamelCase(testMethodName);

        String[] name = Arrays.stream(split).map(String::toLowerCase).toArray(String[]::new);

        String[] subarray = ArrayUtils.subarray(name, 1, name.length);

        ArrayList<String> stringList = new ArrayList<>(Arrays.asList(subarray));
        stringList.add(0, StringUtils.capitalize(name[0]));

        return StringUtils.join(stringList, " ");
    }

    private static PsiAnnotationMemberValue[] getManualCases(PsiMethod testMethod) {
        PsiAnnotation annotation = testMethod.getAnnotation(JIRA_STORY_ANNOTATION);

        if (annotation == null) {
            return new PsiAnnotationMemberValue[0];
        }

        PsiArrayInitializerMemberValue value = (PsiArrayInitializerMemberValue) Objects.requireNonNull(annotation).findDeclaredAttributeValue("manual");

        if (value == null) {
            return new PsiAnnotationMemberValue[0];
        }

        return value.getInitializers();
    }

    public static String getJiraRef(PsiMethod testMethod) {
        if (testMethod.hasAnnotation(JIRA_STORY_ANNOTATION)) {
            return AnnotationUtil.getDeclaredStringAttributeValue(Objects.requireNonNull(testMethod.getAnnotation(JIRA_STORY_ANNOTATION)), "id");
        }
        return "";
    }

    public static String getAcceptanceCriteria(PsiMethod testMethod) {
        if (testMethod.hasAnnotation(AC_ANNOTATION)) {
            PsiAnnotation annotation = testMethod.getAnnotation(AC_ANNOTATION);
            return AnnotationUtil.getDeclaredStringAttributeValue(annotation, "value");
        }
        return "";
    }

    public static void createCaseAnnotation(int testCaseId, PsiMethod method, PsiAnnotation oldAnnotation) {
        final PsiAnnotation annotation = PsiUtils.createAnnotation(getCaseAnnotationText(testCaseId, oldAnnotation), method);
        final Project project = method.getProject();

        CommandProcessor.getInstance().executeCommand(project, () -> ApplicationManager.getApplication().runWriteAction(() -> {
            oldAnnotation.replace(annotation);
        }), "Insert TestRail id", null);
    }

    public static String getCaseAnnotationText(int testCaseId, PsiAnnotation caseAnnotation) {
        String title = AnnotationUtil.getDeclaredStringAttributeValue(caseAnnotation, "title");
        String ac = AnnotationUtil.getDeclaredStringAttributeValue(caseAnnotation, "ac");

        if (ac == null) {
            return String.format("@%s(id=%s, title=\"%s\")", "Case", testCaseId, title);
        }

        return String.format("@%s(id=%s, title=\"%s\", ac=\"%s\")", "Case", testCaseId, title, ac);
    }

    public static void createCaseIdAnnotation(TestCase testCase, PsiMethod method) {
        final PsiAnnotation annotation = PsiUtils.createAnnotation(getCaseAnnotationText(testCase.getId()), method);
        final Project project = method.getProject();

        PsiAnnotation jiraAnnotation = method.getAnnotation(Annotations.JIRA_STORY_ANNOTATION);

        CommandProcessor.getInstance().executeCommand(project, () -> ApplicationManager.getApplication().runWriteAction(() -> {
            PsiUtils.addImport(method.getContainingFile(), Annotations.CASE_ID_ANNOTATION);
            PsiMethodUtils.deleteOldAnnotation(method);
            method.getModifierList().addAfter(annotation, jiraAnnotation);
        }), "Insert TestRail id", null);
    }

    public static String getCaseAnnotationText(int id) {
        return String.format("@%s(%s)", Annotations.CASE_ID_ANNOTATION, id);
    }

    public static List<String> getClassSections(PsiClass psiClass) {
        PsiAnnotation annotation = psiClass.getAnnotation(EPIC_ANNOTATION);

        PsiArrayInitializerMemberValue value = (PsiArrayInitializerMemberValue) annotation.findDeclaredAttributeValue("stories");

        if (value == null) {
            return Collections.emptyList();
        }

        PsiAnnotationMemberValue[] initializers = Objects.requireNonNull(value).getInitializers();

        List<String> sections = new ArrayList<>();

        sections.add(AnnotationUtil.getDeclaredStringAttributeValue(annotation, "value"));

        for (PsiAnnotationMemberValue initializer : initializers) {
            PsiAnnotation storyAnnotation = (PsiAnnotation) initializer;

            String id = AnnotationUtil.getDeclaredStringAttributeValue(storyAnnotation, "id");
            String title = AnnotationUtil.getDeclaredStringAttributeValue(storyAnnotation, "title");

            sections.add(id + ": " + title);
        }
        return sections;
    }

    public static List<String> getMethodSections(PsiMethod psiMethod) {
        if (psiMethod.getAnnotation(JIRA_STORY_ANNOTATION) == null) {
            return Collections.emptyList();
        }

        String storyName = PsiMethodUtils.getStoryName(psiMethod);
        String epicName = PsiMethodUtils.getEpicName(psiMethod);

        return Arrays.asList(epicName, storyName);
    }

    private static void deleteOldAnnotation(PsiMethod method) {
        PsiAnnotation caseIdAnnotation = method.getAnnotation(CASE_ID_ANNOTATION);
        if (caseIdAnnotation != null) {
            caseIdAnnotation.delete();
        }
    }
}
