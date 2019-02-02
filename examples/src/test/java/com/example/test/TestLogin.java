package com.example.test;

import com.qaguild.trail.annotations.*;
import org.testng.annotations.Test;

@Epic("Login")
public class TestLogin {

    @Test
    @JiraStory(id = "Jira-1", title = "Super story name")
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
            @Case(title = "Manual checks for login")
    })
    @JiraStory(id = "Jira-1", title = "Super story name")
    @CaseId(11)
    public void userCanNotLoginWithInValidCredentials() {
//     open(Login.page)
//         .enterUsername("demo")
//         .enterPassword("123")
//         .pressEnter()
//
//     assert(at(MainPage.class)).userName.shouldBe("demo");
    }

}
