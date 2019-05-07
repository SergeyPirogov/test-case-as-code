package com.example.test;

import com.qaguild.annotations.trail.*;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

@Epic(value = "Login", stories = {
        @Story(id = "Jira-1", title = "User is able to login to the system")
})
public class TestLogin {

    @Test
    @Jira(id = "Jira-1")
    @CaseId(260869)
    public void userCanLoginWithValidCredentials() {
//     open(Login.page)
//         .enterUsername("demo")
//         .enterPassword("123")
//         .pressEnter()
//
//     assert(at(MainPage.class)).userName.shouldBe("demo");
    }

    @Test
    @Jira(id = "Jira-1", manual = {
            @Case(id = 260871, title = "Inputted password is masked by bullets"),
            @Case(id = 260870, title = "Masked password can not be copied")
    })
    @CaseId(260872)
    public void userCanNotLoginWithInValidCredentials() {
//     open(Login.page)
//         .enterUsername("demo")
//         .enterPassword("123")
//         .pressEnter()
//
//     assert(at(MainPage.class)).userName.shouldBe("demo");
    }

    @Test
    @Jira(id = "Jira-1", manual = {
            @Case(id = 260873, title = "this is test")
    })
    @CaseId(260874)
    public void userShouldSeeTimerAfter3WrongLoginAttempts() {
        fail("Expected: [BAD CREDENTIALS] \n Actual: [Wrong login]");
    }
}
