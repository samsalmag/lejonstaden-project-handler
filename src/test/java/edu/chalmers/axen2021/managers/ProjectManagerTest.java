package edu.chalmers.axen2021.managers;

import edu.chalmers.axen2021.model.Project;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ProjectManager class.
 * @author Sam Salek.
 */
public class ProjectManagerTest {
    private static ProjectManager projectManager = ProjectManager.getInstance();

    @Test
    public void setActiveProjectTest() {
        Project project = new Project("testActive");

        projectManager.setActiveProject(project.getName());
        assertEquals(projectManager.getActiveProject(), project);

        Project falseProject = new Project("test2");
        assertNotEquals(projectManager.getActiveProject(), falseProject);

        assertThrows(IllegalArgumentException.class, () -> projectManager.setActiveProject("notAProject"));  // project need to be in 'projects' list to become active project, should throw an Exception.
    }
}
