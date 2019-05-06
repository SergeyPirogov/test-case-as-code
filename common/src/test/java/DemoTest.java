import com.qaguild.annotations.trail.Case;
import com.qaguild.annotations.trail.Jira;

public class DemoTest {

    @Jira(value = "Jira-23", title = "This is test title for jira", cases = {
            @Case(title = "This is demo"),
            @Case(title = "This is example")
    })
    @Jira(value = "1625")
    public void name() {
        new Demo().demo();
    }
}

class Demo {

    public void demo() {

    }
}
