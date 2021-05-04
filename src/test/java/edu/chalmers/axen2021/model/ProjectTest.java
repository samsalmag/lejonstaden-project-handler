package edu.chalmers.axen2021.model;

import edu.chalmers.axen2021.model.managers.ProjectManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Project class.
 * @author Sam Salek.
 */
public class ProjectTest {
    private static ProjectManager projectManager = ProjectManager.getInstance();

    @BeforeEach
    public void reset() {
        projectManager.getProjects().clear();

        projectManager.getCostItemNamesMap().clear();
        for (Category category : Category.values()) {
            projectManager.getCostItemNamesMap().put(category, new ArrayList<>());
        }
    }

    @Test
    public void addCostItemTest() {
        Project project = new Project("test");
        projectManager.setActiveCategory(Category.TOMTKOSTNADER);
        project.addCostItem("testCost");

        assertEquals(projectManager.getActiveCostItemNames().get(0), "testCost");
        assertNotEquals(projectManager.getActiveCostItemNames().get(0), "fakeTestCost");
    }

    @Test
    public void removeCostItemTest() {
        Project project1 = new Project("test");
        projectManager.setActiveCategory(Category.YIELD);
        project1.addCostItem("costItem1");
        assertEquals(project1.getYield().get(0).getName(), "costItem1");
        assertEquals(projectManager.getYield().get(0), "costItem1");

        assertThrows(IllegalArgumentException.class, () -> project1.removeCostItem(Category.TOMTKOSTNADER, "costItem1"));
        assertThrows(IllegalArgumentException.class, () -> project1.removeCostItem(Category.YIELD, "costItem100"));

        project1.removeCostItem(Category.YIELD, "costItem1");
        assertEquals(project1.getYield().size(), 0);
        assertEquals(projectManager.getYield().size(), 0);
    }
}
