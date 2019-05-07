package com.qaguild.plugin;

import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.psi.PsiMethod;
import com.qaguild.enums.CaseState;
import com.qaguild.plugin.api.TestRailClient;
import com.qaguild.plugin.api.TestRailClientBuilder;
import com.qaguild.plugin.model.Section;
import com.qaguild.plugin.model.TestCase;
import com.qaguild.plugin.util.PsiMethodUtils;

import java.util.List;

import static com.qaguild.plugin.Annotations.CASE_ID_ANNOTATION;
import static com.qaguild.plugin.util.PsiMethodUtils.getTestCaseTitle;

public class TestRailApiWrapper {

    private final Settings settings;
    private TestRailClient testRailClient;

    public TestRailApiWrapper(Settings settings) {
        this.settings = settings;
        this.testRailClient = new TestRailClientBuilder(settings.getApiUrl(), settings.getUserName(), settings.getPassword())
                .build();
    }

    public int createSections(List<String> sections) {
        int parentSectionId = -1;
        for (int i = 0; i < sections.size(); i++) {
            if (i == 0) {
                parentSectionId = saveSection(settings.getProjectId(), settings.getSuiteId(), sections.get(0)).getId();
            } else {
                parentSectionId = saveSection(settings.getProjectId(), settings.getSuiteId(), parentSectionId, sections.get(i)).getId();
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
                .filter(it -> it.getName().contains(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such section"));
    }

    private Section createSection(int projectId, Section section) {
        return testRailClient.addSection(projectId, section);
    }

    public TestCase createAutomatedCheck(int sectionId, PsiMethod testMethod) {
        TestCase testCase = new TestCase();
        testCase.setTitle(getTestCaseTitle(testMethod.getName()));
        testCase.setCustomState(CaseState.AUTOMATED.getValue());
        testCase.setCustomAcceptanceCriteria(PsiMethodUtils.getAcceptanceCriteria(testMethod));
        testCase.setRefs(PsiMethodUtils.getJiraRef(testMethod));

        if (testMethod.hasAnnotation(CASE_ID_ANNOTATION)) {
            int id = AnnotationUtil.getLongAttributeValue(testMethod.getAnnotation(CASE_ID_ANNOTATION), "value").intValue();
            testCase.setId(id);
        }

        return saveTestCase(sectionId, testCase);
    }

    public TestCase saveTestCase(int sectionId, TestCase testCase) {
        TestCase tCase = getTestCase(sectionId, testCase.getTitle());
        if (tCase != null) {
            return tCase;
        }

        return testRailClient.addTestCase(sectionId, testCase);
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


