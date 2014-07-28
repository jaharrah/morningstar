package com.morningstar.grocerystore;

import com.morningstar.grocerystore.model.customer.BaseCustomer;
import com.morningstar.grocerystore.model.customer.CustomerTypeA;
import com.morningstar.grocerystore.model.customer.CustomerTypeB;
import com.morningstar.grocerystore.model.grocery.GroceryStore;
import com.morningstar.grocerystore.model.exceptions.InvalidRowFormatException;
import com.morningstar.grocerystore.model.exceptions.MinimumCashiersException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * GroceryStoreSimulator is the engine that runs how a grocery store works.  It verifies and loads the file from
 * the file path given, manages the grocery store until there are no more customers to arrive or in line, and then
 * returns the time when the last customer finishes.
 * <P/>
 * You can bypass reading in from a file by instantiating this class, populating the customersToAdd and cashierCount,
 * and then return the value from calling runGroceryStore().
 * <P/>
 * User: Jon Aharrah
 * Date: 7/17/14
 * Time: 9:46 AM
 */
public class GroceryStoreSimulator {

    private HashMap<Integer, List<BaseCustomer>> customersToAdd = new HashMap<Integer, List<BaseCustomer>>();
    private int cashierCount = 0;

    public static void main(String[] argv) {

        // Check how many arguments were passed in
        if(argv.length == 0)
        {
            System.out.println("Proper Usage is: java -jar grocery.jar <filename>");
            System.exit(1);
        }

        // read in the file values - if file not found, or invalid, return with message.
        GroceryStoreSimulator simulator = new GroceryStoreSimulator();
        String filePath = argv[0];

        try {
            // first, pull the customers from the file, and load the HashMap of Customers to add.
            simulator.load(new FileReader(filePath));
        } catch (FileNotFoundException fnfe) {
            System.out.println("The file at path [" + filePath + "] was not found.  Please specify a valid test file.");
            System.exit(1);
        } catch (IOException ioe) {
            System.out.println("There was an IO Error reading the file at path [" + filePath + ".  Please specify a valid test file.");
            System.exit(1);
        } catch (InvalidRowFormatException irfe) {
            System.out.println("There was an invalid row format exception: " + irfe.getMessage());
            System.exit(1);
        }

        // The values are now read in.  Run the simulation.
        int totalMinutes = 0;
        try {
            totalMinutes = simulator.runGroceryStore();
        } catch (MinimumCashiersException mre) {
            System.out.println("There must be at least one cashier open.  Number specified: [" + simulator.getCashierCount() + "]");
            System.exit(1);
        }


        System.out.println("Finished at: t = " + totalMinutes + " minutes.");
    }

    /**
     * Reads from an input stream, and loads the total number or cashiers and customer information
     * into memory.
     *
     * @param input The stream from which to read the data.
     * @throws IOException The exception thrown when an error occured reading in the file.
     * @throws InvalidRowFormatException The exception thrown when the customer information is in the wrong format.
     */
    public void load(Reader input) throws IOException, InvalidRowFormatException {
        BufferedReader textReader = new BufferedReader(input);
        int rowNumber = 0;

        // go through each row in the file.  The first row is the total number of cashiers, the rest are customers to be
        // added.  We will add all the customers to a HashMap, keyed off of the turn they are to be added.
        String line = null;
        while((line = textReader.readLine()) != null) {
            // if rowNumber is 0, that should be the count of cashiers.  Otherwise, we're adding customers.
            if(rowNumber == 0) {
                try {
                    this.setCashierCount(Integer.parseInt(line.trim()));
                } catch (NumberFormatException nfe) {
                    throw new InvalidRowFormatException("The first row in the file must be an integer representing " +
                            "the number of cashiers.");
                }
            } else {
                // the customer row consists of Type, turn number, and item count.
                String[] customerData = line.split(" ");
                BaseCustomer customer = null;
                if(customerData.length != 3) {
                    throw new InvalidRowFormatException("The customer row in the file must be in the format " +
                            "\"<TYPE> <TURN NUMBER> <ITEM COUNT>\".");
                }

                if(customerData[0].equals("A")) {
                    customer = new CustomerTypeA();
                } else if (customerData[0].equals("B")) {
                    customer = new CustomerTypeB();
                } else {
                    throw new InvalidRowFormatException("The customer type [" + customerData[0] +
                            "] is not a valid customer type.");
                }


                int turnNumber;
                try {
                    turnNumber = Integer.parseInt(customerData[1]);
                } catch (NumberFormatException nfe) {
                    throw new InvalidRowFormatException("The number of items [" + customerData[2] + "] is not an integer.");
                }

                try {
                    customer.setItemsLeftToCheckout(Integer.parseInt(customerData[2]));
                } catch (NumberFormatException nfe) {
                    throw new InvalidRowFormatException("The number of items [" + customerData[2] + "] is not an integer.");
                }

                // Now that the customer is instantiated with # of items, add it to the Hashmap.
                // If it is null, create a new ArrayList, otherwise add to the existing one.
                List customersForTurn = this.getCustomersToAdd().get(new Integer(turnNumber));
                if(customersForTurn == null) {
                    customersForTurn = new ArrayList<BaseCustomer>();
                }
                customersForTurn.add(customer);
                this.getCustomersToAdd().put(new Integer(turnNumber), customersForTurn);
            }

            // increment the row count
            rowNumber++;
        }
    }

    /**
     * Runs the grocery store.  Running a grocery store involves opening all the cashiers in the first minute,
     * and then continuing to run the store while there's customers still shopping, or still in line at the cashier
     * they chose to wait for.
     * @return The time it takes to process all the customers in the grocery store.
     * @throws MinimumCashiersException Thrown when the total number of cashiers available is less than one.
     */
    public int runGroceryStore() throws MinimumCashiersException {
        int turn = 0;

        // first turn is opening the grocery store, and opening up the designated number of cashiers.
        GroceryStore grocery = new GroceryStore();
        grocery.openCashiers(this.getCashierCount());
        turn++;

        // while there's customers queued to come in, or theres customers waiting at cashiers,
        // continue working
        while(this.getCustomersToAdd().size() > 0 || !grocery.isAllCashiersFree()) {
            // put new customers in cashiers for current turn, and then process cashier minute
            List newCustomers = this.getCustomersToAdd().get(new Integer(turn));
            if(newCustomers != null) {
                grocery.addCustomersToCashiers(newCustomers);
                this.getCustomersToAdd().remove(new Integer(turn));
            }
            grocery.runMinute();
            turn++;
        }

        return turn;
    }

    public HashMap<Integer, List<BaseCustomer>> getCustomersToAdd() {
        return customersToAdd;
    }

    public void setCustomersToAdd(HashMap<Integer, List<BaseCustomer>> customersToAdd) {
        this.customersToAdd = customersToAdd;
    }

    public int getCashierCount() {
        return cashierCount;
    }

    public void setCashierCount(int cashierCount) {
        this.cashierCount = cashierCount;
    }
}