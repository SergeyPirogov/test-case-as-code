package com.example.test;

import com.qaguild.trail.annotations.*;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

@Epic("Login")
public class TestLogin {

    @Test
    @JiraStory(id = "Jira-1", title = "User login with login and password")
    @CaseId(10)
    public void userCanLoginWithValidCredentials() {
//     open(Login.page)
//         .enterUsername("demo")
//         .enterPassword("123")
//         .pressEnter()
//
//     assert(at(MainPage.class)).userName.shouldBe("demo");
    }

    @Test
    @Manual({
            @Case(title = "Inputted password is masked by bullets"),
            @Case(title = "Masked password can not be copied")
    })
    @JiraStory(id = "Jira-1", title = "User login with login and password")
    @CaseId(11)
    public void userCanNotLoginWithInValidCredentials() {
//     open(Login.page)
//         .enterUsername("demo")
//         .enterPassword("123")
//         .pressEnter()
//
//     assert(at(MainPage.class)).userName.shouldBe("demo");
    }

    @Test
    @JiraStory(id = "Jira-1", title = "User login with login and password")
    public void userShouldSeeTimerAfter3WrongLoginAttempts(){
        fail("Expected: [BAD CREDENTIALS] \n Actual: [Wrong login]");
    }
}
