package com.example.test;

import com.qaguild.trail.annotations.Case;
import com.qaguild.trail.annotations.Epic;
import com.qaguild.trail.annotations.JiraStory;
import com.qaguild.trail.annotations.Manual;
import org.testng.annotations.Test;

@Epic("Login")
public class TestLogin {

    @Test
    @JiraStory(id = "Jira-1", title = "Super story name")
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
    public void userCanNotLoginWithInValidCredentials() {
//     open(Login.page)
//         .enterUsername("demo")
//         .enterPassword("123")
//         .pressEnter()
//
//     assert(at(MainPage.class)).userName.shouldBe("demo");
    }

}
