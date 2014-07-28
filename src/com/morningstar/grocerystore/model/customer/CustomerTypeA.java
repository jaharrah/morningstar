package com.morningstar.grocerystore.model.customer;

import com.morningstar.grocerystore.model.cashier.BaseCashier;
import com.morningstar.grocerystore.model.grocery.GroceryStore;

/**
 * CustomerTypeA is a subclass of BaseCustomer.  Customer Type A always chooses the cashier with the shortest
 * line (fewest number of customers in line)
 *
 * User: Jon Aharrah
 * Date: 7/17/14
 * Time: 3:44 PM
 */
public class CustomerTypeA extends BaseCustomer {

    public String getCustomerType() {
        return "A";
    }

    public int getDesiredCashierRegisterNumber(GroceryStore store) {
        // Customer Type A always choose the cashier with the shortest line
        int cashierLineMinimum = Integer.MAX_VALUE;
        int cashierId = Integer.MAX_VALUE;
        for (int i = 0; i < store.getCashiers().size(); i++) {
            BaseCashier cashier = store.getCashier(i);
            if(cashier.getNumberOfCustomers() < cashierLineMinimum) {
                cashierId = i;
                cashierLineMinimum = cashier.getNumberOfCustomers();
            }

            // if the line is zero, just take that one.
            if(cashierLineMinimum == 0) {
                return cashierId;
            }
        }

        return cashierId;
    }

}
