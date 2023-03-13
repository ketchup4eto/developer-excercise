package com.exercise.cloudruid.services.contracts;

import java.util.List;

public interface DealsService {

    /**
     * This method requires a list of names which when given sets the items whose names are on it to be eligible
     * (by changing their Enum)
     * for the Two for three deal.
     * @param itemNames the list of names of the items
     */

    void addToDealTwoForThree(List<String> itemNames);

    /**
     * This method requires a list of names which when given sets the items whose names are on it to be eligible
     * (by changing their Enum)
     * for the Buy one and get one for half price deal.
     * @param itemNames the list of names of the items
     */
    void addToDealBuyOneGetOneHalfPrice(List<String> itemNames);

    /**
     * This method requires a list of names which when given sets the items whose names are on it to be ineligible
     * (by changing their Enum)
     * for any of the deals.
     * @param itemNames the list of names of the items
     */
    void removeFromPromotion(List<String> itemNames);
}
