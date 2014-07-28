package com.morningstar.grocerystore.model.customer;

import com.morningstar.grocerystore.model.cashier.BaseCashier;
import com.morningstar.grocerystore.model.grocery.GroceryStore;

/**
 * CustomerTypeB is a subclass of BaseCustomer.  Customer Type B looks at the last customer in each line, and
 * always chooses to be behind the customer with the fewest number of items left to check out,
 * regardless of how many other customers are in the line or how many items they have. Customer Type B will always
 * choose an empty line before a line with any customers in it.
 *
 * User: Jon Aharrah
 * Date: 7/17/14
 * Time: 3:44 PM
 */
public class CustomerTypeB extends BaseCustomer {

    public String getCustomerType() {
        return "B";
    }

    public int getDesiredCashierRegisterNumber(GroceryStore store) {
        // Customer Type B will choose the ones with the bottom customer that has the fewest items, but will still
        // take cashiers with no customers first.
        int itemLineMinimum = Integer.MAX_VALUE;
        int cashierNumber = Integer.MAX_VALUE;
        for (int i = 0; i < store.getCashiers().size(); i++) {
            BaseCashier cashier = store.getCashier(i);
            // if the line is zero, just take that one.
            if(cashier.getNumberOfCustomers() == 0) {
                return i;
            }

            BaseCustomer lastOneInLine = cashier.getCustomerLine().get(cashier.getCustomerLine().size()-1);

            if(lastOneInLine.getItemsLeftToCheckout() < itemLineMinimum) {
                cashierNumber = i;
                itemLineMinimum = cashier.getNumberOfCustomers();
            }
        }

        return cashierNumber;
    }
}
