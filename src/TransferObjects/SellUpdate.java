package TransferObjects;

import java.io.Serializable;

public class SellUpdate implements Serializable {
    double budget;

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getBudget() {
        return budget;
    }
}
