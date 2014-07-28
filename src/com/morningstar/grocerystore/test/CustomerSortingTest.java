package com.morningstar.grocerystore.test;

import com.morningstar.grocerystore.GroceryStoreSimulator;
import com.morningstar.grocerystore.model.customer.BaseCustomer;
import com.morningstar.grocerystore.model.customer.CustomerTypeA;
import com.morningstar.grocerystore.model.customer.CustomerTypeB;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Unit test case for testing sorting in BaseCustomer
 * User: Jon Aharrah
 * Date: 7/12/14
 * Time: 11:29 AM
 */
public class CustomerSortingTest extends TestCase {

    @Test
    public void testSortingByCustomerType() {
        // only testing sorting by type, so keeping quantity same.
        try {
            List<BaseCustomer> customers = new ArrayList<BaseCustomer>();

            this.addCustomerToList(customers, "A", 1);
            this.addCustomerToList(customers, "B", 1);
            this.addCustomerToList(customers, "A", 1);
            this.addCustomerToList(customers, "B", 1);
            this.addCustomerToList(customers, "A", 1);
            this.addCustomerToList(customers, "B", 1);

            Collections.sort(customers);

            for(int i = 0; i < 3; i++) {
                BaseCustomer customer = customers.get(i);
                assertEquals(customer.getCustomerType(), "A");
            }
            for(int i = 3; i < 6; i++) {
                BaseCustomer customer = customers.get(i);
                assertEquals(customer.getCustomerType(), "B");
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingByItemCount() {
        // only testing sorting by item count, so keeping type same.
        try {
            List<BaseCustomer> customers = new ArrayList<BaseCustomer>();

            this.addCustomerToList(customers, "A", 4);
            this.addCustomerToList(customers, "A", 2);
            this.addCustomerToList(customers, "A", 1);
            this.addCustomerToList(customers, "A", 5);
            this.addCustomerToList(customers, "A", 6);
            this.addCustomerToList(customers, "A", 3);

            Collections.sort(customers);

            for(int i = 0; i < 6; i++) {
                BaseCustomer customer = customers.get(i);
                assertEquals(customer.getItemsLeftToCheckout(), (i+1));
            }

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSortingByTypeAndItemCount() {
        try {
            List<BaseCustomer> customers = new ArrayList<BaseCustomer>();

            this.addCustomerToList(customers, "B", 6);
            this.addCustomerToList(customers, "B", 4);
            this.addCustomerToList(customers, "B", 2);
            this.addCustomerToList(customers, "A", 6);
            this.addCustomerToList(customers, "A", 4);
            this.addCustomerToList(customers, "A", 2);

            Collections.sort(customers);

            // customers should be sorted by item count (least first), then type (A first)
            for(int i = 0; i < 6; i++) {
                BaseCustomer customer = customers.get(i);
                if(i == 0 || i == 1) {
                    assertEquals(customer.getItemsLeftToCheckout(), 2);
                } else if (i == 2 || i == 3) {
                    assertEquals(customer.getItemsLeftToCheckout(), 4);
                } else {
                    assertEquals(customer.getItemsLeftToCheckout(), 6);
                }

                // odds should be B (i = 1,3,5), evens A (i = 0,2,4)
                if(((i+6) % 2) == 1) {
                    assertEquals(customer.getCustomerType(), "B");
                } else {
                    assertEquals(customer.getCustomerType(), "A");
                }
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    public void addCustomerToList(List<BaseCustomer> customers, String type, int itemCount) {
        BaseCustomer customer = null;
        if(type.equals("A")) {
            customer = new CustomerTypeA();
        } else {
            customer = new CustomerTypeB();
        }

        customer.setItemsLeftToCheckout(itemCount);

        customers.add(customer);
    }
}
