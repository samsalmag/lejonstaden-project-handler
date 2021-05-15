package edu.chalmers.axen2021.model;

import edu.chalmers.axen2021.model.managers.ProjectManager;
import edu.chalmers.axen2021.model.projectdata.ApartmentItem;
import edu.chalmers.axen2021.model.projectdata.CostItem;
import edu.chalmers.axen2021.model.projectdata.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Project class.
 * @author Sam Salek.
 */
public class ProjectTest {
    private static final ProjectManager projectManager = ProjectManager.getInstance();

    @BeforeEach
    public void reset() {
        projectManager.getProjects().clear();

        projectManager.getCostItemNamesMap().clear();
        for (Category category : Category.values()) {
            projectManager.getCostItemNamesMap().put(category, new ArrayList<>());
        }
    }

    @Test
    public void getActiveCostItemTest() {
        Project project = new Project("test");
        projectManager.setActiveCategory(Category.TOMTKOSTNADER);
        CostItem costItem = project.addCostItem("testCost");

        assertEquals(project.getActiveCostItem("testCost"), costItem);
        assertEquals(project.getActiveCostItem("testCostFake"), null);

        projectManager.setActiveCategory(null);
        assertThrows(NullPointerException.class, () -> project.getActiveCostItem("testCost"));
    }

    @Test
    public void addCostItemTest() {
        Project project = new Project("test");
        projectManager.setActiveCategory(Category.TOMTKOSTNADER);
        project.addCostItem("testCost");

        assertEquals(projectManager.getActiveCostItemNames().get(0), "testCost");
        assertNotEquals(projectManager.getActiveCostItemNames().get(0), "fakeTestCost");

        projectManager.setActiveCategory(null);
        assertThrows(NullPointerException.class, () -> project.addCostItem("exception"));
    }

    @Test
    public void removeCostItemTest() {
        Project project1 = new Project("test");
        projectManager.setActiveCategory(Category.ANSLUTNINGAR);
        project1.addCostItem("costItem1");
        assertEquals(project1.getAnslutningarCostItems().get(0).getName(), "costItem1");
        assertEquals(projectManager.getCostItemNamesMap().get(Category.ANSLUTNINGAR).get(0), "costItem1");

        assertThrows(IllegalArgumentException.class, () -> project1.removeCostItem(Category.TOMTKOSTNADER, "costItem1"));
        assertThrows(IllegalArgumentException.class, () -> project1.removeCostItem(Category.ANSLUTNINGAR, "costItem100"));

        project1.removeCostItem(Category.ANSLUTNINGAR, "costItem1");
        assertEquals(project1.getAnslutningarCostItems().size(), 0);
        assertEquals(projectManager.getCostItemNamesMap().get(Category.ANSLUTNINGAR).size(), 0);
    }

    @Test
    public void addApartmentItemTest() {
        Project project = new Project("test");
        assertEquals(project.getApartmentItems().size(), 0);

        ApartmentItem apartmentItem = project.addApartmentItem();
        assertEquals(project.getApartmentItems().get(0), apartmentItem);
    }

    @Test
    public void removeApartmentItemTest() {
        Project project = new Project("test");
        ApartmentItem apartmentItem = project.addApartmentItem();
        assertEquals(project.getApartmentItems().get(0), apartmentItem);

        project.removeApartmentItem(apartmentItem);
        assertEquals(project.getApartmentItems().size(), 0);

        // Doesn't exist in class anymore, should throw exception.
        assertThrows(IllegalArgumentException.class, () -> project.removeApartmentItem(apartmentItem));
    }
}
