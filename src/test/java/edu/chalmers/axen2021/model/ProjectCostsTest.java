package edu.chalmers.axen2021.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ProjectCosts class.
 * @author Sara Vardheim
 * @author Ahmad Al-Aref
 * @author Tilda Grönlund
 */

class ProjectCostsTest {
    private ProjectCosts apt = new ProjectCosts();
    /**
     * Tests the getConnectionsCost(int, int) method in ProjectCosts class.
     */
    @Test
    public void getConnectionsCostTest() {
        assertEquals(apt.getConnectionsCost(2000, 4), 8);
    }

    /**
     * Tests the getClientCost(int, int) method in ProjectCosts class.
     */
    @Test
    public void getClientCostTest() {
        assertEquals(apt.getClientCost(2000, 5), 10);
    }

    /**
     * Tests the getContractCost(int, int) method in ProjectCosts class.
     */
    @Test
    public void getContractCostTest() {
        assertEquals(apt.getContractCost(2000, 6), 12);
    }

    /**
     * Tests the getUnforseenCost(int, int, double) method in ProjectCosts class.
     */
    @Test
    public void getUnforseenCostTest() {
        assertEquals(apt.getUnforseenCost(30, 200, 0.04), 0.24);
    }

    /**
     * Tests the getFinancialCost(int, int) method in ProjectCosts class.
     */
    @Test
    public void getFinancialCostTest() {
        assertEquals(apt.getFinancialCost(2000, 6), 12);
    }
}
