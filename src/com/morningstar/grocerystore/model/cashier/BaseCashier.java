package com.morningstar.grocerystore.model.cashier;

import com.morningstar.grocerystore.model.customer.BaseCustomer;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class represents a cashier.  Subclasses must define how many minutes it takes to process one item.
 * The cashier has a list of how many customers are in its queue.  The cashier also keeps track of how many minutes
 * it has spent checking out the item they are currently holding.
 *
 * User: Jon Aharrah
 * Date: 7/17/14
 * Time: 1:55 PM
 */
public abstract class BaseCashier {


    private int minutesSpentOnCurrentItem = 0;

    private List<BaseCustomer> customerLine = new ArrayList<BaseCustomer>();

    public abstract int getMinutesToProcessAnItem();

    public int getNumberOfCustomers() {
        return customerLine.size();
    }

    public List<BaseCustomer> getCustomerLine() {
        return customerLine;
    }

    public void addCustomerToLine(BaseCustomer customer) {
        this.customerLine.add(customer);
    }

    public int getMinutesSpentOnCurrentItem() {
        return minutesSpentOnCurrentItem;
    }

    public void setMinutesSpentOnCurrentItem(int minutesSpentOnCurrentItem) {
        this.minutesSpentOnCurrentItem = minutesSpentOnCurrentItem;
    }

    public void runMinute() {
        // if the line is empty, do nothing.
        if(this.getCustomerLine().size() == 0) {
            return;
        }

        // spend one unit of time on current item
        this.setMinutesSpentOnCurrentItem(this.getMinutesSpentOnCurrentItem()+1);

        // if the amount of time spent on the item equals the cashiers time it takes to check out an item, an item
        // is processed, and should be deducted from the customer, with the current progress reset.
        BaseCustomer customer = this.getCustomerLine().get(0);

        if(this.getMinutesSpentOnCurrentItem() == this.getMinutesToProcessAnItem()) {
            customer.setItemsLeftToCheckout(customer.getItemsLeftToCheckout()-1);
            this.setMinutesSpentOnCurrentItem(0);
        }

        // if the customer has no items left, they should be helped to the door.
        if(customer.getItemsLeftToCheckout() < 1) {
            this.getCustomerLine().remove(customer);
        }
    }
}
