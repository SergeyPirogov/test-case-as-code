package com.qaguild.plugin.tests;

import com.qaguild.plugin.Settings;
import com.qaguild.plugin.TestRailApiWrapper;
import org.assertj.core.api.BDDAssertions;
import org.junit.BeforeClass;
import org.junit.Test;

import static java.util.Arrays.asList;

public class TestRailApiWrapperTest {

    private static TestRailApiWrapper apiWrapper;

    @BeforeClass
    public static void setUp() {
        final Settings settings = new Settings();
        settings.setApiUrl("");
        settings.setUserName("");
        settings.setPassword("");
        settings.setProjectId(5);
        settings.setSuiteId(658);
        apiWrapper = new TestRailApiWrapper(settings);
    }

    @Test
    public void testCanCreateSections() {
        int parentSectionId = apiWrapper.createSections(asList("Login", "Jira-1: User is able to login"));

        BDDAssertions.then(parentSectionId).isGreaterThan(0);

        parentSectionId = apiWrapper.createSections(asList("Login", "Jira-2: User is able to logout"));

        BDDAssertions.then(parentSectionId).isGreaterThan(0);
    }

    @Test
    public void testCanCreateSectionsWithoutTitle() {
        int parentSectionId = apiWrapper.createSections(asList("Login", "Jira-3: User is able to login"));

        BDDAssertions.then(parentSectionId).isGreaterThan(0);

        parentSectionId = apiWrapper.createSections(asList("Login", "Jira-3"));

        BDDAssertions.then(parentSectionId).isGreaterThan(0);
    }

    @Test
    public void testCanCreateSectionsWithoutTitleFirst() {
        int parentSectionId = apiWrapper.createSections(asList("Login", "Jira-4"));

        BDDAssertions.then(parentSectionId).isGreaterThan(0);

        parentSectionId = apiWrapper.createSections(asList("Login", "Jira-4: User is able to login"));

        BDDAssertions.then(parentSectionId).isGreaterThan(0);
    }
}
