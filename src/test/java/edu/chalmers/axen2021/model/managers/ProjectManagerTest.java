package edu.chalmers.axen2021.model.managers;

import edu.chalmers.axen2021.model.Category;
import edu.chalmers.axen2021.model.Project;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ProjectManager class.
 * @author Sam Salek.
 */
public class ProjectManagerTest {
    private static ProjectManager projectManager = ProjectManager.getInstance();
    private static SaveManager saveManager = SaveManager.getInstance();

    @BeforeAll
    public static void resetAll() {
        ReflectionTestUtils.setField(saveManager, "directory", "src/test/saveDirectory");
    }

    @BeforeEach
    public void reset() {
        saveManager.verifyDirectory();

        projectManager.getProjects().clear();
        projectManager.getCostItemNamesMap().clear();

        // Init map in project manager.
        for (Category category : Category.values()) {
            projectManager.getCostItemNamesMap().put(category, new ArrayList<>());
        }
    }

    @AfterEach
    public void afterReset() {
        saveManager.removeDirectory();
    }

    @Test
    public void setActiveProjectTest() {
        Project project = new Project("testActive");

        projectManager.setActiveProject(project.getName());
        assertEquals(projectManager.getActiveProject(), project);

        Project falseProject = new Project("test2");
        assertNotEquals(projectManager.getActiveProject(), falseProject);

        assertThrows(IllegalArgumentException.class, () -> projectManager.setActiveProject("notAProject"));  // project need to be in 'projects' list to become active project, should throw an Exception.
    }

    @Test
    public void removeProjectTest() {
        Project project = new Project("test");
        assertEquals(projectManager.getProjects().size(), 1);
        saveManager.saveProject(project);

        projectManager.getProjects().clear();
        assertEquals(projectManager.getProjects().size(), 0);

        projectManager.loadProjects();
        assertEquals(projectManager.getProjects().size(), 1);
        assertEquals(projectManager.getProjects().get(0).getName(), "test");
    }
}
