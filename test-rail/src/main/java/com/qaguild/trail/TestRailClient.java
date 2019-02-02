package com.qaguild.trail;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

public class TestRailClient {

    private TestRail testRail;

    public TestRailClient() {
        this.testRail = TestRail.builder("https://qaguild.testrail.io/", "john@gmail.com", "123456").build();
    }

    public Project getProjectByName(String name) {
        return testRail.projects().list().execute().stream().filter(it -> it.getName().equals(name))
                .findFirst().orElseThrow(() -> new RuntimeException("No such project " + name));
    }

    public Section saveSection(int projectId, int suiteId, String name) {
        if (sectionExists(projectId, suiteId, name)) {
            return getSection(projectId, suiteId, name);
        }

        Section section = new Section();
        section.setSuiteId(suiteId);
        section.setName(name);

        return createSection(projectId, section);
    }

    public Section getSection(int projectId, int suiteId, String name) {
        return testRail.sections().list(projectId, suiteId)
                .execute().stream()
                .filter(it -> it.getName().equalsIgnoreCase(name))
                .findFirst().orElseThrow(() -> new RuntimeException("No such section"));
    }

    public Suite getSuite(int projectId, String name) {
        return testRail.suites().list(projectId).execute().stream().filter(it -> it.getName().equals(name))
                .findFirst().orElseThrow(() -> new RuntimeException("No such suite"));
    }

    public Section saveSection(int projectId, int suiteId, int parentId, String name) {
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

    private Section createSection(int projectId, Section section) {
        return testRail.sections().add(projectId, section).execute();
    }

    public Case saveCase(int projectId, int suiteId, int sectionId, Case testCase) {
        if (getCaseById(testCase.getId()) != null) {
            return updateCase(testCase);
        }

        if (testCaseExists(projectId, suiteId, testCase.getTitle())) {
            return getCase(projectId, suiteId, testCase.getTitle());
        }

        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        return testRail.cases().add(sectionId, testCase, customCaseFields).execute();
    }

    public Case getCase(int projectId, int suiteId, String name) {
        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        return testRail.cases()
                .list(projectId, suiteId, customCaseFields)
                .execute()
                .stream().filter(it -> it.getTitle().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such case " + name));
    }

    public Case getCaseById(int id) {
        try {
            List<CaseField> customCaseFields = testRail.caseFields().list().execute();
            return testRail.cases().get(id, customCaseFields).execute();
        } catch (Exception e) {
            return null;
        }
    }

    public Case updateCase(Case testCase) {
        List<CaseField> customCaseFields = testRail.caseFields().list().execute();
        return testRail.cases().update(testCase, customCaseFields).execute();
    }

    public boolean testCaseExists(int projectId, int suiteId, String name) {
        try {
            return getCase(projectId, suiteId, name) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public Run saveTestRun(int projectId, Run run) {
        Run testRun = getTestRun(projectId, run.getName());
        if (testRun != null) {
            return testRun;
        }

        return testRail.runs().add(projectId, run).execute();
    }

    private Run getTestRun(int projectId, String name) {
        Run run = testRail.runs().list(projectId)
                .execute()
                .stream().filter(it -> it.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        return run;
    }

    public void addResultForCase(int runId, int caseId, Result result) {
        List<ResultField> resultFields = testRail.resultFields().list().execute();

        testRail.results().addForCase(runId, caseId, result, resultFields).execute();
    }
}
