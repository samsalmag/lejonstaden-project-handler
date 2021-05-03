package edu.chalmers.axen2021.model;

import edu.chalmers.axen2021.managers.ProjectManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Project class.
 * @author Sam Salek.
 */
public class ProjectTest {
    private static ProjectManager projectManager = ProjectManager.getInstance();

    @Test
    public void addCostItemTest() {
        Project project = new Project("test");
        projectManager.setActiveCategory(Category.TOMTKOSTNADER);
        project.addCostItem("testCost");

        assertEquals(projectManager.getActiveCostItemNames().get(0), "testCost");
        assertNotEquals(projectManager.getActiveCostItemNames().get(0), "fakeTestCost");
    }
}
