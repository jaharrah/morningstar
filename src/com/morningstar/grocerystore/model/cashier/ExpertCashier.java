package com.morningstar.grocerystore.model.cashier;

/**
 * ExpertCashier is a subclass of BaseCashier.  An expert cashier can bag one item every minute.
 *
 * User: Jon Aharrah
 * Date: 7/17/14
 * Time: 3:44 PM
 */
public class ExpertCashier extends BaseCashier {

    public int getMinutesToProcessAnItem() {
        return 1;
    }
}
