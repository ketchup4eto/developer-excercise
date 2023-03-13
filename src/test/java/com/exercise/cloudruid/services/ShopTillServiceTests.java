package com.exercise.cloudruid.services;

import com.exercise.cloudruid.helpers.CreateMockHelpers;
import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.services.contracts.GroceriesService;
import com.exercise.cloudruid.utils.enums.Deals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShopTillServiceTests {

    @InjectMocks
    private ShopTillServiceImpl mockService;

    @Mock
    private GroceriesService groceriesService;

    @Test
    void viewAll_Should_Return_Empty_List_When_No_Product_Are_In_Cart() {
        Assertions.assertTrue(mockService.viewCart().isEmpty());
        mockService.emptyShoppingCart();
    }

    @Test
    void viewAll_Should_Return_NonEmpty_List_When_No_Product_Are_In_Cart() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);

        mockService.addToCart(mockGroceries.getName());

        Assertions.assertFalse(mockService.viewCart().isEmpty());
        mockService.emptyShoppingCart();
    }

    @Test
    void addToCart_Should_Add_To_Every_Sublist() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        mockGroceries.setDeal(Deals.NONE);

        Groceries mockGroceries2 = CreateMockHelpers.mockGroceries();
        mockGroceries2.setDeal(Deals.TWOFORTHREE);

        Groceries mockGroceries3 = CreateMockHelpers.mockGroceries();
        mockGroceries3.setDeal(Deals.BUYONEGETONEHALFPRICE);

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);
        mockService.addToCart(mockGroceries.getName());
        Assertions.assertTrue(mockService.viewCart().contains(mockGroceries));

        Mockito.when(groceriesService.getByName(mockGroceries2.getName())).thenReturn(mockGroceries2);
        mockService.addToCart(mockGroceries2.getName());
        Assertions.assertTrue(mockService.viewCart().contains(mockGroceries2));

        Mockito.when(groceriesService.getByName(mockGroceries3.getName())).thenReturn(mockGroceries3);
        mockService.addToCart(mockGroceries3.getName());
        Assertions.assertTrue(mockService.viewCart().contains(mockGroceries3));

        Assertions.assertEquals(3, mockService.viewCart().size());

        mockService.emptyShoppingCart();
    }

    @Test
    void removeFromCart_Should_Remove_From_Every_Sublist() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        mockGroceries.setDeal(Deals.NONE);

        Groceries mockGroceries2 = CreateMockHelpers.mockGroceries();
        mockGroceries2.setDeal(Deals.TWOFORTHREE);

        Groceries mockGroceries3 = CreateMockHelpers.mockGroceries();
        mockGroceries3.setDeal(Deals.BUYONEGETONEHALFPRICE);

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);
        mockService.addToCart(mockGroceries.getName());
        Assertions.assertTrue(mockService.viewCart().contains(mockGroceries));
        mockService.removeFromCart(mockGroceries.getName());
        Assertions.assertFalse(mockService.viewCart().contains(mockGroceries));

        Mockito.when(groceriesService.getByName(mockGroceries2.getName())).thenReturn(mockGroceries2);
        mockService.addToCart(mockGroceries2.getName());
        Assertions.assertTrue(mockService.viewCart().contains(mockGroceries2));
        mockService.removeFromCart(mockGroceries2.getName());
        Assertions.assertFalse(mockService.viewCart().contains(mockGroceries2));

        Mockito.when(groceriesService.getByName(mockGroceries3.getName())).thenReturn(mockGroceries3);
        mockService.addToCart(mockGroceries3.getName());
        Assertions.assertTrue(mockService.viewCart().contains(mockGroceries3));
        mockService.removeFromCart(mockGroceries3.getName());
        Assertions.assertFalse(mockService.viewCart().contains(mockGroceries3));

        Assertions.assertEquals(0, mockService.viewCart().size());
        mockService.emptyShoppingCart();
    }

    @Test
    void emptyShoppingCart_Should_Remove_All_Items_From_All_Sublists() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        mockGroceries.setDeal(Deals.NONE);

        Groceries mockGroceries2 = CreateMockHelpers.mockGroceries();
        mockGroceries2.setDeal(Deals.TWOFORTHREE);

        Groceries mockGroceries3 = CreateMockHelpers.mockGroceries();
        mockGroceries3.setDeal(Deals.BUYONEGETONEHALFPRICE);

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);
        mockService.addToCart(mockGroceries.getName());
        Assertions.assertTrue(mockService.viewCart().contains(mockGroceries));

        Mockito.when(groceriesService.getByName(mockGroceries2.getName())).thenReturn(mockGroceries2);
        mockService.addToCart(mockGroceries2.getName());
        Assertions.assertTrue(mockService.viewCart().contains(mockGroceries2));

        Mockito.when(groceriesService.getByName(mockGroceries3.getName())).thenReturn(mockGroceries3);
        mockService.addToCart(mockGroceries3.getName());
        Assertions.assertTrue(mockService.viewCart().contains(mockGroceries3));

        Assertions.assertEquals(3, mockService.viewCart().size());

        mockService.emptyShoppingCart();

        Assertions.assertEquals(0,mockService.viewCart().size());

        mockService.emptyShoppingCart();
    }

    @Test
    void totalCostOfCart_Should_Properly_Display_Price_When_Less_Than_100c() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        mockGroceries.setPrice(50);

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);
        mockService.addToCart(mockGroceries.getName());

        Assertions.assertEquals("50 clouds", mockService.totalCostOfCart());
        mockService.emptyShoppingCart();
    }

    @Test
    void totalCostOfCart_Should_Properly_Calculate_Price_When_Less_Than_100c() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        mockGroceries.setPrice(50);

        Groceries mockGroceries2 = CreateMockHelpers.mockGroceries();
        mockGroceries2.setPrice(25);

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);
        mockService.addToCart(mockGroceries.getName());

        Mockito.when(groceriesService.getByName(mockGroceries2.getName())).thenReturn(mockGroceries2);
        mockService.addToCart(mockGroceries2.getName());


        Assertions.assertEquals("75 clouds", mockService.totalCostOfCart());
        mockService.emptyShoppingCart();
    }

    @Test
    void totalCostOfCart_Should_Properly_Calculate_And_Display_Price_When_More_Than_100c() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        mockGroceries.setPrice(50);

        Groceries mockGroceries2 = CreateMockHelpers.mockGroceries();
        mockGroceries2.setPrice(60);

        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);
        mockService.addToCart(mockGroceries.getName());

        Mockito.when(groceriesService.getByName(mockGroceries2.getName())).thenReturn(mockGroceries2);
        mockService.addToCart(mockGroceries2.getName());


        Assertions.assertEquals("1.10 aws", mockService.totalCostOfCart());
        mockService.emptyShoppingCart();
    }

    @Test
    void totalCostOfCart_Should_Properly_Calculate_And_Display_Price_For_TWOFORTHREE_Promotion() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        mockGroceries.setDeal(Deals.TWOFORTHREE);



        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);
        mockService.addToCart(mockGroceries.getName());
        mockService.addToCart(mockGroceries.getName());
        mockService.addToCart(mockGroceries.getName());


        Assertions.assertEquals("2.00 aws", mockService.totalCostOfCart());
        mockService.emptyShoppingCart();
    }

    @Test
    void totalCostOfCart_Should_Properly_Calculate_And_Display_Price_For_BUYONEGETONEHALFPRICE_Promotion_When_Even_Number_Of_Products() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        mockGroceries.setDeal(Deals.BUYONEGETONEHALFPRICE);



        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);
        mockService.addToCart(mockGroceries.getName());
        mockService.addToCart(mockGroceries.getName());


        Assertions.assertEquals("1.50 aws", mockService.totalCostOfCart());
        mockService.emptyShoppingCart();
    }

    @Test
    void totalCostOfCart_Should_Properly_Calculate_And_Display_Price_For_BUYONEGETONEHALFPRICE_Promotion_When_Odd_Number_Of_Products() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        mockGroceries.setDeal(Deals.BUYONEGETONEHALFPRICE);



        Mockito.when(groceriesService.getByName(mockGroceries.getName())).thenReturn(mockGroceries);
        mockService.addToCart(mockGroceries.getName());
        mockService.addToCart(mockGroceries.getName());
        mockService.addToCart(mockGroceries.getName());


        Assertions.assertEquals("2.50 aws", mockService.totalCostOfCart());
        mockService.emptyShoppingCart();
    }
}
