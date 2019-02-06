package com.qaguild.trail;

import com.codepine.api.testrail.model.Result;
import com.codepine.api.testrail.model.Run;
import org.testng.*;
import com.qaguild.trail.annotations.Case;
import com.qaguild.trail.annotations.Manual;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestRailTestRunListener implements IMethodInterceptor, ITestListener {

    private TestRailClient testRail = new TestRailClient();

    private int projectId = testRail.getProjectByName(System.getProperty("project.name","")).getId();
    private int suiteId = testRail.getSuite(projectId, System.getProperty("suite.name", "")).getId();
    private int testRunId = 0;


    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        List<Integer> caseIds = new ArrayList<>();

        for (IMethodInstance method : methods) {
            Method testMethod = method.getMethod().getConstructorOrMethod().getMethod();

            if (testMethod.isAnnotationPresent(Manual.class)) {
                Case[] manualCases = testMethod.getAnnotation(Manual.class).value();
                for (Case manualCase : manualCases) {
                    int id = testRail.getCase(projectId, suiteId, manualCase.title()).getId();
                    caseIds.add(id);
                }
            }

            int id = testRail.getCase(projectId, suiteId, MethodNameUtils.formatTestCaseTitle(testMethod.getName())).getId();
            caseIds.add(id);
        }

        Run testRun = new Run();
        testRun.setName(formatDate(Instant.now()));
        testRun.setIncludeAll(false);
        testRun.setCaseIds(caseIds);
        testRun.setSuiteId(suiteId);

        testRunId = testRail.saveTestRun(projectId, testRun).getId();

        return methods;
    }

    private String formatDate(Instant instant){
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                .withZone(ZoneId.systemDefault());

        return DATE_TIME_FORMATTER.format(instant);
    }

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName = MethodNameUtils.formatTestCaseTitle(result.getMethod().getMethodName());
        int caseId = testRail.getCase(projectId, suiteId, methodName).getId();

        Result res = new Result();
        res.setStatusId(1);

        testRail.addResultForCase(testRunId, caseId, res);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = MethodNameUtils.formatTestCaseTitle(result.getMethod().getMethodName());
        int caseId = testRail.getCase(projectId, suiteId, methodName).getId();

        Result res = new Result();
        res.setStatusId(5);

        testRail.addResultForCase(testRunId, caseId, res);
    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
    }
}
