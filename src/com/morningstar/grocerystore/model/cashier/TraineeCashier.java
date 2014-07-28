package com.morningstar.grocerystore.model.cashier;

/**
 * TraineeCashier is a subclass of BaseCashier.  A trainee cashier can bag one item every two minutes.
 *
 * User: Jon Aharrah
 * Date: 7/17/14
 * Time: 3:44 PM
 */
public class TraineeCashier extends BaseCashier {

    public int getMinutesToProcessAnItem() {
        return 2;
    }

}
