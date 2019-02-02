package com.qaguild.plugin;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.psi.*;
import com.qaguild.plugin.api.TestRailClient;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import com.qaguild.plugin.api.TestRailClientBuilder;
import com.qaguild.plugin.enums.State;
import com.qaguild.plugin.model.Section;
import com.qaguild.plugin.model.TestCase;

import java.util.*;

import static com.qaguild.plugin.Annotations.AC_ANNOTATION;
import static com.qaguild.plugin.Annotations.CASE_ID_ANNOTATION;

public class TestRailApiWrapper {

    private final Settings settings;
    private TestRailClient testRailClient;

    public TestRailApiWrapper(Settings settings) {
        this.settings = settings;
        this.testRailClient = new TestRailClientBuilder(settings.getApiUrl(), settings.getUserName(), settings.getPassword())
                .build();
    }

    public int createSections(String... sections) {
        int parentSectionId = -1;
        for (int i = 0; i < sections.length; i++) {
            if (i == 0) {
                parentSectionId = saveSection(settings.getProjectId(), settings.getSuiteId(), sections[i]).getId();
            } else {
                parentSectionId = saveSection(settings.getProjectId(), settings.getSuiteId(), parentSectionId, sections[i]).getId();
            }
        }
        return parentSectionId;
    }

    private Section saveSection(int projectId, int suiteId, String name) {
        if (sectionExists(projectId, suiteId, name)) {
            return getSection(projectId, suiteId, name);
        }

        Section section = new Section();
        section.setSuiteId(suiteId);
        section.setName(name);

        return createSection(projectId, section);
    }

    private Section saveSection(int projectId, int suiteId, int parentId, String name) {
        if (sectionExists(projectId, suiteId, name)) {
            return getSection(projectId, suiteId, name);
        }

        Section section = new Section();
        section.setSuiteId(suiteId);
        section.setParentId(parentId);
        section.setName(name);

        return createSection(projectId, section);
    }

    private boolean sectionExists(int projectId, int suiteId, String name) {
        try {
            return getSection(projectId, suiteId, name) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public Section getSection(int projectId, int suiteId, String name) {
        return testRailClient.getSections(projectId, suiteId)
                .stream()
                .filter(it -> it.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such section"));
    }

    private Section createSection(int projectId, Section section) {
        return testRailClient.addSection(projectId, section);
    }

    public TestCase createAutomatedCheck(int sectionId, PsiMethod testMethod) {
        TestCase testCase = new TestCase();
        testCase.setTitle(formatTestCaseTitle(testMethod.getName()));
        testCase.setCustomState(State.AUTOMATED.getValue());
        testCase.setCustomAcceptanceCriteria(getAcceptanceCriteria(testMethod));
        testCase.setRefs(getJiraRef(testMethod));

        if (testMethod.hasAnnotation(CASE_ID_ANNOTATION)) {
            int id = AnnotationUtil.getLongAttributeValue(testMethod.getAnnotation(CASE_ID_ANNOTATION), "value").intValue();
            testCase.setId(id);
        }

        return saveTestCase(sectionId, testCase);
    }

    private TestCase saveTestCase(int sectionId, TestCase testCase) {
        TestCase tCase = getTestCase(sectionId, testCase.getTitle());
        if (tCase != null) {
            return tCase;
        }

        return testRailClient.addTestCase(sectionId, testCase);
    }


    private String formatTestCaseTitle(String testMethodName) {
        String[] split = StringUtils.splitByCharacterTypeCamelCase(testMethodName);

        String[] name = Arrays.stream(split).map(String::toLowerCase).toArray(String[]::new);

        String[] subarray = ArrayUtils.subarray(name, 1, name.length);

        ArrayList<String> stringList = new ArrayList<>(Arrays.asList(subarray));
        stringList.add(0, StringUtils.capitalize(name[0]));

        return StringUtils.join(stringList, " ");
    }

    private String getJiraRef(PsiMethod testMethod) {
        if (testMethod.hasAnnotation(Annotations.JIRA_STORY_ANNOTATION)) {
            return AnnotationUtil.getDeclaredStringAttributeValue(Objects.requireNonNull(testMethod.getAnnotation(Annotations.JIRA_STORY_ANNOTATION)), "id");
        }
        return "";
    }

    private String getAcceptanceCriteria(PsiMethod testMethod) {
        if (testMethod.hasAnnotation(AC_ANNOTATION)) {
            PsiAnnotation annotation = testMethod.getAnnotation(AC_ANNOTATION);
            return AnnotationUtil.getDeclaredStringAttributeValue(annotation, "value");
        }
        return "";
    }

    public void createManualCheck(int sectionId, PsiMethod testMethod) {
        String jiraRef = getJiraRef(testMethod);
        PsiAnnotation annotation = testMethod.getAnnotation(Annotations.MANUAL_CASE_ANNOTATION);

        PsiArrayInitializerMemberValue value = (PsiArrayInitializerMemberValue) annotation.findDeclaredAttributeValue("value");

        PsiAnnotationMemberValue[] cases = value.getInitializers();

        for (PsiAnnotationMemberValue manualCase : cases) {
            PsiAnnotation caseAnnotation = (PsiAnnotation) manualCase;
            String title = AnnotationUtil.getDeclaredStringAttributeValue(caseAnnotation, "title");
            String ac = AnnotationUtil.getDeclaredStringAttributeValue(caseAnnotation, "ac");

            TestCase testCase = new TestCase();
            testCase.setTitle(formatTestCaseTitle(title));
            testCase.setCustomState(State.READY_FOR_AUTOMATION.getValue());
            testCase.setCustomAcceptanceCriteria(ac);
            testCase.setRefs(jiraRef);

            saveTestCase(sectionId, testCase);
        }
    }

    public List<TestCase> getTestCases(int sectionId) {
        return testRailClient.getTestCases(settings.getProjectId(), settings.getSuiteId(), sectionId);
    }

    public TestCase getTestCase(int sectionId, String testCaseTitle) {
        return getTestCases(sectionId).stream()
                .filter(it -> it.getTitle()
                        .equals(testCaseTitle))
                .findFirst()
                .orElse(null);
    }
}


