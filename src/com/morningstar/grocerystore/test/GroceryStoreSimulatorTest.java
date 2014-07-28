package com.morningstar.grocerystore.test;

import com.morningstar.grocerystore.GroceryStoreSimulator;
import com.morningstar.grocerystore.model.customer.BaseCustomer;
import com.morningstar.grocerystore.model.customer.CustomerTypeA;
import com.morningstar.grocerystore.model.customer.CustomerTypeB;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Unit test case for methods in GroceryStoreSimulatorTest
 * User: Jon Aharrah
 * Date: 7/12/14
 * Time: 11:29 AM
 */
public class GroceryStoreSimulatorTest extends TestCase {

    private GroceryStoreSimulator simulator;

    @Before
    public void setUp() {
        simulator = new GroceryStoreSimulator();
    }

    @Test
    public void testExample1() {
        try {
            // add cashier counter to simulator
            simulator.setCashierCount(1);

            HashMap<Integer, List<BaseCustomer>> customers = new HashMap<Integer, List<BaseCustomer>>();

            // customer at second 1.
            List<BaseCustomer> timeslot1 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot1, "A", 2);
            customers.put(new Integer(1), timeslot1);

            // customer at second 2.
            List<BaseCustomer> timeslot2 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot2, "A", 1);
            customers.put(new Integer(2), timeslot2);

            // add customers to simulator
            simulator.setCustomersToAdd(customers);

            // run simulator
            assertTrue(simulator.runGroceryStore() == 7);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testExample2() {
        try {
            // add cashier counter to simulator
            simulator.setCashierCount(2);

            HashMap<Integer, List<BaseCustomer>> customers = new HashMap<Integer, List<BaseCustomer>>();

            // customer at second 1.
            List<BaseCustomer> timeslot1 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot1, "A", 5);
            customers.put(new Integer(1), timeslot1);

            // customer at second 2.
            List<BaseCustomer> timeslot2 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot2, "B", 1);
            customers.put(new Integer(2), timeslot2);

            // customer at second 3.
            List<BaseCustomer> timeslot3 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot3, "A", 5);
            customers.put(new Integer(3), timeslot3);

            // customer at second 5.
            List<BaseCustomer> timeslot5 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot5, "B", 3);
            customers.put(new Integer(5), timeslot5);

            // customer at second 8.
            List<BaseCustomer> timeslot8 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot8, "A", 2);
            customers.put(new Integer(8), timeslot8);

            // add customers to simulator
            simulator.setCustomersToAdd(customers);

            // run simulator
            assertTrue(simulator.runGroceryStore() == 13);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testExample3() {
        try {
            // add cashier counter to simulator
            simulator.setCashierCount(2);

            HashMap<Integer, List<BaseCustomer>> customers = new HashMap<Integer, List<BaseCustomer>>();

            // customer at second 1.
            List<BaseCustomer> timeslot1 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot1, "A", 2);
            this.addCustomerToList(timeslot1, "A", 2);
            customers.put(new Integer(1), timeslot1);

            // customer at second 2.
            List<BaseCustomer> timeslot2 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot2, "A", 1);
            customers.put(new Integer(2), timeslot2);

            // customer at second 3.
            List<BaseCustomer> timeslot3 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot3, "A", 2);
            customers.put(new Integer(3), timeslot3);

            // add customers to simulator
            simulator.setCustomersToAdd(customers);

            // run simulator
            assertTrue(simulator.runGroceryStore() == 6);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testExample4() {
        try {
            // add cashier counter to simulator
            simulator.setCashierCount(2);

            HashMap<Integer, List<BaseCustomer>> customers = new HashMap<Integer, List<BaseCustomer>>();

            // customer at second 1.
            List<BaseCustomer> timeslot1 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot1, "A", 2);
            this.addCustomerToList(timeslot1, "A", 3);
            customers.put(new Integer(1), timeslot1);

            // customer at second 2.
            List<BaseCustomer> timeslot2 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot2, "A", 1);
            this.addCustomerToList(timeslot2, "A", 1);
            customers.put(new Integer(2), timeslot2);

            // add customers to simulator
            simulator.setCustomersToAdd(customers);

            // run simulator
            assertTrue(simulator.runGroceryStore() == 9);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testExample5() {
        try {
            // add cashier counter to simulator
            simulator.setCashierCount(2);

            HashMap<Integer, List<BaseCustomer>> customers = new HashMap<Integer, List<BaseCustomer>>();

            // customer at second 1.
            List<BaseCustomer> timeslot1 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot1, "A", 3);
            this.addCustomerToList(timeslot1, "A", 5);
            customers.put(new Integer(1), timeslot1);

            // customer at second 3.
            List<BaseCustomer> timeslot3 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot3, "A", 1);
            customers.put(new Integer(3), timeslot3);

            // customer at second 4.
            List<BaseCustomer> timeslot4 = new ArrayList<BaseCustomer>();
            this.addCustomerToList(timeslot4, "B", 1);
            this.addCustomerToList(timeslot4, "A", 1);
            customers.put(new Integer(4), timeslot4);

            // add customers to simulator
            simulator.setCustomersToAdd(customers);

            // run simulator
            assertTrue(simulator.runGroceryStore() == 11);

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
