package edu.chalmers.axen2021.model;

/**
 * @author Tilda Grönlund
 * @author Ahmad Al-Aref
 * @author Sara Vardheim
 */

public class ProjectCosts {

    public static double getConnectionsCost(int krPerApt, int totQuantity) {
        return (krPerApt * totQuantity) / 1000;
    }

    public static double getClientCost(int clientBoa, int totBoa) {
        return (clientBoa * totBoa) / 1000;
    }

    public static double getContractCost(int contractBoa, int totBoa) {
        return (contractBoa * totBoa) / 1000;
    }

    public static double getUnforseenCost(int contractBoa, int totBoa, double contractPercent) {
        return getContractCost(contractBoa, totBoa) * contractPercent;
    }

    public static double getFinantialCost(int totBoa, int krPerSqm) {
        return (totBoa * krPerSqm) / 1000;
    }
}
