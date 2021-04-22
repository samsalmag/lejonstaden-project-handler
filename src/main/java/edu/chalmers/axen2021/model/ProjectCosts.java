package edu.chalmers.axen2021.model;

/**
 * @author Tilda Grönlund
 * @author Ahmad Al-Aref
 * @author Sara Vardheim
 */

public class ProjectCosts {

    /**
     * Calculates and returns the Connection costs.
     * @param krPerApt cost in kr per apartment.
     * @param totQuantity total quantity of apartments.
     * @return Connection costs of the project.
     */
    public double getConnectionsCost(int krPerApt, int totQuantity) {
        return (krPerApt * totQuantity) / 1000;
    }

    /**
     * Calculates and returns the Client cost
     * @param clientBoa BOA of the client.
     * @param totBoa total BOA of the project.
     * @return Client costs of the project.
     */
    public double getClientCost(int clientBoa, int totBoa) {
        return (clientBoa * totBoa) / 1000;
    }

    /**
     * Calculates and returns the Contract costs.
     * @param contractBoa BOA of the contract.
     * @param totBoa total BOA of the project.
     * @return Contract costs of the project.
     */
    public double getContractCost(int contractBoa, int totBoa) {
        return (contractBoa * totBoa) / 1000;
    }

    /**
     * Calculates and returns the Unforseen costs.
     * @param contractBoa BOA of the contract.
     * @param totBoa total BOA of the project.
     * @param contractPercent percentage of the contract.
     * @return Unforseen costs of the project.
     */
    public double getUnforseenCost(int contractBoa, int totBoa, double contractPercent) {
        return getContractCost(contractBoa, totBoa) * contractPercent;
    }

    /**
     * Calculates and returns the Finantial costs.
     * @param totBoa total BOA of the project.
     * @param krPerSqm cost in kr per square meter.
     * @return Finantial costs of the project.
     */
    public double getFinantialCost(int totBoa, int krPerSqm) {
        return (totBoa * krPerSqm) / 1000;
    }
}
