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
    /**
     * Tests the getConnectionsCost(int, int) method in ProjectCosts class.
     */
    @Test
    public void getConnectionsCostTest() {
        assertEquals(ProjectCosts.getConnectionsCost(2000, 4), 8);
    }

    /**
     * Tests the getClientCost(int, int) method in ProjectCosts class.
     */
    @Test
    public void getClientCostTest() {
        assertEquals(ProjectCosts.getClientCost(2000, 5), 10);
    }

    /**
     * Tests the getContractCost(int, int) method in ProjectCosts class.
     */
    @Test
    public void getContractCostTest() {
        assertEquals(ProjectCosts.getContractCost(2000, 6), 12);
    }

    /**
     * Tests the getUnforseenCost(int, int, double) method in ProjectCosts class.
     */
    @Test
    public void getUnforseenCostTest() {
        assertEquals(ProjectCosts.getUnforseenCost(30, 200, 0.04), 0.24);
    }

    /**
     * Tests the getFinancialCost(int, int) method in ProjectCosts class.
     */
    @Test
    public void getFinancialCostTest() {
        assertEquals(ProjectCosts.getFinantialCost(2000, 6), 12);
    }
}