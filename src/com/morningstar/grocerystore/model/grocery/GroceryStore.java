package com.morningstar.grocerystore.model.grocery;

import com.morningstar.grocerystore.model.cashier.BaseCashier;
import com.morningstar.grocerystore.model.cashier.ExpertCashier;
import com.morningstar.grocerystore.model.cashier.TraineeCashier;
import com.morningstar.grocerystore.model.customer.BaseCustomer;
import com.morningstar.grocerystore.model.exceptions.MinimumCashiersException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Domain object representing a grocery store.  A grocery store contains a collection of cashiers.
 * User: Jon Aharrah
 * Date: 7/17/14
 * Time: 2:45 PM
 */
public class GroceryStore {

    private List<BaseCashier> cashiers = new ArrayList<BaseCashier>();

    public List<BaseCashier> getCashiers() {
        return cashiers;
    }

    public BaseCashier getCashier(int cashierIndex) {
        return this.getCashiers().get(cashierIndex);
    }

    /**
     * Opens the number of cashiers requested by the parameter totalCashiers.  All cashiers are expert cashiers,
     * except for the last lane, which is used for the trainee cashier.
     * @param totalCashiers
     * @throws MinimumCashiersException
     */
    public void openCashiers(int totalCashiers) throws MinimumCashiersException {
        // if the total cashiers is less than one, throw MinimumCashiersException
        if(totalCashiers < 1) {
            throw new MinimumCashiersException();
        }

	int expertCashiers = totalCashiers - 1;

        while(this.getCashiers().size() < expertCashiers) {
                this.addCashier(new ExpertCashier());
        }

        // the last cashier is a trainee cashier
        this.addCashier(new TraineeCashier());
    }

    /**
     * Adds a new cashier to the list of cashiers.
     * @param cashier
     */
    public void addCashier(BaseCashier cashier) {
        this.getCashiers().add(cashier);
    }

    /**
     * When the grocery store runs one minute, all cashiers working there also run one minute.
     */
    public void runMinute() {
        // run a minute for each cashier
        for(BaseCashier cashier : cashiers) {
            cashier.runMinute();
        }
    }

    /**
     * This helper method loops through each cashier, and returns whether they are all free or not.
     * @return boolean Determines if all lanes are open, or there are still customers in line.
     */
    public boolean isAllCashiersFree() {
        for(BaseCashier cashier : cashiers) {
            if(cashier.getNumberOfCustomers() > 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * This method is called to sort the list of customers being brought in on one turn.  The grocery store then
     * assists each customer by asking which cashier they'd like, and letting them in that line.
     * @param newCustomers A List of new customers for that turn to be put in cashier lines.
     */
    public void addCustomersToCashiers(List<BaseCustomer> newCustomers) {
        // Sort the BaseCustomers based on their Comparable interface
        Collections.sort(newCustomers);

        for(BaseCustomer newCustomer: newCustomers) {
            int chooseCustomerId = newCustomer.getDesiredCashierRegisterNumber(this);
            this.getCashier(chooseCustomerId).addCustomerToLine(newCustomer);
        }

    }
}