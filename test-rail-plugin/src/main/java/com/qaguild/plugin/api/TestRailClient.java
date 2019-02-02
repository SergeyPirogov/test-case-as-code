package com.qaguild.plugin.api;

import retrofit2.http.*;
import com.qaguild.plugin.model.Project;
import com.qaguild.plugin.model.Section;
import com.qaguild.plugin.model.TestCase;

import java.util.List;

/*
 * Created by alpa on 2018-12-06
 */
public interface TestRailClient {

    @GET("/index.php%3F/api/v2/get_cases/{projectId}&suite_id={suite_id}&section_id={section_id}")
    List<TestCase> getTestCases(@Path("projectId") int projectId, @Path("suite_id") int suiteId,
                                @Path("section_id") int sectionId);

    @POST("/index.php%3F/api/v2/add_case/{sectionId}")
    TestCase addTestCase(@Path("sectionId") int sectionId, @Body TestCase testCase);

    @GET("/index.php%3F/api/v2/get_project/{projectId}")
    Project getProject(@Path("projectId") int projectId);

    @GET("/index.php%3F/api/v2/get_sections/{project_id}&suite_id={suite_id}")
    List<Section> getSections(@Path("project_id") int projectId, @Path("suite_id") int suiteId);

    @POST("/index.php%3F/api/v2/add_section/{projectId}")
    Section addSection(@Path("projectId") int projectId, @Body Section section);
}
