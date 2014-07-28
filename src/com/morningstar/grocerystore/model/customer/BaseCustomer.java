package com.morningstar.grocerystore.model.customer;

import com.morningstar.grocerystore.model.grocery.GroceryStore;

/**
 * This abstract class represents a customer.  Subclasses must define what type of customer it is, and how it
 * chooses what cashier it is looking for on checkout.  The customer keeps track of how many items it needs
 * left to checkout before leaving the grocery store.
 * User: Jon Aharrah
 * Date: 7/17/14
 * Time: 1:55 PM
 */
public abstract class BaseCustomer implements Comparable<BaseCustomer> {

    private int itemsLeftToCheckout;

    public abstract int getDesiredCashierRegisterNumber(GroceryStore store);

    public abstract String getCustomerType();

    public int getItemsLeftToCheckout() {
        return itemsLeftToCheckout;
    }

    public void setItemsLeftToCheckout(int itemsLeftToCheckout) {
        this.itemsLeftToCheckout = itemsLeftToCheckout;
    }

    /**
     * This method is implemented to fulfill the Comparable interface. If two or more customers arrive at the same
     * time, those with fewer items choose cashiers before those with more, and if they have the same number of
     * items then type A's choose before type B's.
     * @param otherCustomer the BaseCustomer that is compared against.
     * @return value of the instance is less, equal, or more than the object passed in.
     */
    public int compareTo(BaseCustomer otherCustomer) {

        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (this == otherCustomer) return EQUAL;

        if(this.getItemsLeftToCheckout() < otherCustomer.getItemsLeftToCheckout()) {
            return BEFORE;
        } else if (this.getItemsLeftToCheckout() > otherCustomer.getItemsLeftToCheckout()) {
            return AFTER;
        } else {
            if(this.getCustomerType().equals("A") && otherCustomer.getCustomerType().equals("B")) {
                return BEFORE;
            } else if (this.getCustomerType().equals("B") && otherCustomer.getCustomerType().equals("A")) {
                return AFTER;
            } else {
                return EQUAL;
            }
        }
    }
}
