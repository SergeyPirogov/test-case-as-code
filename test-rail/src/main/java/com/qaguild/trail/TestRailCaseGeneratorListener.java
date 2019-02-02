package com.qaguild.trail;

import com.codepine.api.testrail.model.Case;
import com.qaguild.trail.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;
import com.qaguild.trail.enums.AutomationType;
import com.qaguild.trail.enums.State;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TestRailCaseGeneratorListener implements IMethodInterceptor {

    private TestRailClient testRail = new TestRailClient();

    private int projectId = testRail.getProjectByName(System.getProperty("project.name", "")).getId();
    private int suiteId = testRail.getSuite(projectId, System.getProperty("suite.name", "")).getId();

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        for (IMethodInstance method : methods) {

            Method testMethod = method.getMethod().getConstructorOrMethod().getMethod();

            Class<?> declaringClass = testMethod.getDeclaringClass();

            if (!declaringClass.isAnnotationPresent(Epic.class)) {
                throw new RuntimeException("There is no @Epic annotation for " + declaringClass.getName());
            }

            Epic epic = declaringClass.getAnnotation(Epic.class);

            if (!testMethod.isAnnotationPresent(JiraStory.class)) {
                throw new RuntimeException("There is no @JiraStory annotation for " + testMethod.getName());
            }

            JiraStory jiraStory = testMethod.getAnnotation(JiraStory.class);

            String storyName = jiraStory.id() + " - " + jiraStory.title();

            int sectionId = createSections(epic.value(), storyName);

            createAutomatedCheck(sectionId, testMethod);

            if (testMethod.isAnnotationPresent(Manual.class)) {
                createManualCheck(sectionId, testMethod);
            }
        }

        return new ArrayList<>();
    }

    private void createManualCheck(int sectionId, Method testMethod) {
        Manual manualCheck = testMethod.getAnnotation(Manual.class);
        String jiraRef = getJiraRef(testMethod);

        for (com.qaguild.trail.annotations.Case c : manualCheck.value()) {
            Map<String, Object> customFields = new HashMap<>();
            customFields.put("automation_type", c.automationType());
            customFields.put("state", c.state().getValue());
            customFields.put("ac", c.ac());

            Case testCase = new Case();
            testCase.setTitle(c.title());
            testCase.setCustomFields(customFields);
            testCase.setId(c.id());
            testCase.setRefs(jiraRef);

            testRail.saveCase(projectId, suiteId, sectionId, testCase);
        }
    }

    private void createAutomatedCheck(int sectionId, Method testMethod) {
        Map<String, Object> customFields = new HashMap<>();
        customFields.put("automation_type", AutomationType.GUI_SELENIUM.getValue());
        customFields.put("state", State.AUTOMATED.getValue());
        customFields.put("ac", getAcceptanceCriteria(testMethod));

        Case testCase = new Case();
        testCase.setTitle(MethodNameUtils.formatTestCaseTitle(testMethod.getName()));
        testCase.setCustomFields(customFields);
        testCase.setRefs(getJiraRef(testMethod));

        if (testMethod.isAnnotationPresent(CaseId.class)) {
            int id = testMethod.getAnnotation(CaseId.class).value();
            testCase.setId(id);
        }

        testRail.saveCase(projectId, suiteId, sectionId, testCase);
    }

    private String getJiraRef(Method testMethod) {
        if (testMethod.isAnnotationPresent(JiraStory.class)) {
            return testMethod.getAnnotation(JiraStory.class).id();
        }
        return "";
    }

    private String getAcceptanceCriteria(Method testMethod) {
        if (testMethod.isAnnotationPresent(Ac.class)) {
            return testMethod.getAnnotation(Ac.class).value();
        }
        return "";
    }

    private int createSections(String... sections) {
        int parentSectionId = -1;
        for (int i = 0; i < sections.length; i++) {
            if (i == 0) {
                parentSectionId = testRail.saveSection(projectId, suiteId, sections[i]).getId();
            } else {
                parentSectionId = testRail.saveSection(projectId, suiteId, parentSectionId, sections[i]).getId();
            }
        }
        return parentSectionId;
    }
}
