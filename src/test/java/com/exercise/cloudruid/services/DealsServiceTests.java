package com.exercise.cloudruid.services;

import com.exercise.cloudruid.helpers.CreateMockHelpers;
import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.services.contracts.GroceriesService;
import com.exercise.cloudruid.utils.enums.Deals;
import com.exercise.cloudruid.utils.exceptions.ItemDealException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class DealsServiceTests {

    @InjectMocks
    private DealsServiceImpl mockService;

    @Mock
    private GroceriesService groceriesService;

    @Test
    void addToDealTwoForThree_should_throw_ItemDealException_when_enum_is_not_NONE() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();

        mockGroceries.setDeal(Deals.BUYONEGETONEHALFPRICE);

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);

        Assertions.assertThrows(ItemDealException.class,
                () -> mockService.addToDealTwoForThree(Collections.singletonList(mockGroceries.getName())));
    }

    @Test
    void addToDealTwoForThree_Should_Call_GroceriesService_When_Valid() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);

        mockService.addToDealTwoForThree(Collections.singletonList(mockGroceries.getName()));

        Mockito.verify(groceriesService, Mockito.times(1)).update(mockGroceries);
    }

    @Test
    void addToDealTwoForThree_Should_Set_Groceries_Deal_To_TWOFORTHREE() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);

        Assertions.assertEquals(Deals.NONE, mockGroceries.getDeal());

        mockService.addToDealTwoForThree(Collections.singletonList(mockGroceries.getName()));

        Assertions.assertEquals(Deals.TWOFORTHREE, mockGroceries.getDeal());
    }

    @Test
    void addToDealBuyOneGetOneHalfPrice_should_throw_ItemDealException_when_enum_is_not_NONE() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();

        mockGroceries.setDeal(Deals.TWOFORTHREE);

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);

        Assertions.assertThrows(ItemDealException.class,
                () -> mockService.addToDealBuyOneGetOneHalfPrice(Collections.singletonList(mockGroceries.getName())));
    }

    @Test
    void addToDealBuyOneGetOneHalfPrice_Should_Set_Groceries_Deal_To_BUYONEGETONEHALFPRICE() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);

        Assertions.assertEquals(Deals.NONE, mockGroceries.getDeal());

        mockService.addToDealBuyOneGetOneHalfPrice(Collections.singletonList(mockGroceries.getName()));

        Assertions.assertEquals(Deals.BUYONEGETONEHALFPRICE, mockGroceries.getDeal());
    }

    @Test
    void addToDealBuyOneGetOneHalfPrice_Should_Call_GroceriesService_When_Valid() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);

        mockService.addToDealBuyOneGetOneHalfPrice(Collections.singletonList(mockGroceries.getName()));

        Mockito.verify(groceriesService, Mockito.times(1)).update(mockGroceries);
    }

    @Test
    void removeFromPromotion_Should_Set_Groceries_Deal_To_NONE() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        mockGroceries.setDeal(Deals.BUYONEGETONEHALFPRICE);

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);

        Assertions.assertEquals(Deals.BUYONEGETONEHALFPRICE, mockGroceries.getDeal());

        mockService.removeFromPromotion(Collections.singletonList(mockGroceries.getName()));

        Assertions.assertEquals(Deals.NONE, mockGroceries.getDeal());
    }

    @Test
    void removeFromPromotion_Should_Call_GroceriesService_When_Valid() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);

        mockService.removeFromPromotion(Collections.singletonList(mockGroceries.getName()));

        Mockito.verify(groceriesService, Mockito.times(1)).update(mockGroceries);
    }
}
