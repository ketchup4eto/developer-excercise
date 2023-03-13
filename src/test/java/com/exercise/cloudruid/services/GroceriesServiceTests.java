package com.exercise.cloudruid.services;

import com.exercise.cloudruid.helpers.CreateMockHelpers;
import com.exercise.cloudruid.models.Groceries;
import com.exercise.cloudruid.repositories.GroceriesRepository;
import com.exercise.cloudruid.utils.exceptions.GrocerieNotFoundException;
import com.exercise.cloudruid.utils.exceptions.InvalidPriceFormatException;
import com.exercise.cloudruid.utils.exceptions.ItemExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GroceriesServiceTests {

    @InjectMocks
    private GroceriesServiceImpl mockService;

    @Mock
    private GroceriesRepository mockRepository;


    @Test
    void readAll_Should_CallRepository() {
        //Arrange
        Mockito.when(mockRepository.findAll())
                .thenReturn(null);
        //Act
        mockService.getAll();
        //Assert
        Mockito.verify(mockRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findById_should_throw_Exception_When_NotValid() {
        Mockito.when(mockRepository.findById(Mockito.anyInt())).thenThrow(GrocerieNotFoundException.class);

        Assertions.assertThrows(GrocerieNotFoundException.class, () -> mockService.getById((Mockito.anyInt())));
    }

    @Test
    void findById_should_call_repo_When_Valid() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        Mockito.when(mockRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockGroceries));

        mockService.getById(mockGroceries.getId());

        Mockito.verify(mockRepository, Mockito.times(1)).findById(mockGroceries.getId());
    }

    @Test
    void findByName_Should_ReturnGroceries_When_GroceriesWithSameNameExists() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        Mockito.when(mockRepository.findByName(mockGroceries.getName())).thenReturn(Optional.of(mockGroceries));

        Assertions.assertEquals(mockGroceries, mockService.getByName(mockGroceries.getName()));
    }

    @Test
    void getByName_Should_Throw_Exception_When_Groceries_With_Name_Dont_Exist() {
        Mockito.when(mockRepository.findByName(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(GrocerieNotFoundException.class, () -> mockService.getByName(Mockito.anyString()));
    }


    @Test
    void create_Should_Throw_ItemExistsException_When_Groceries_With_Name_Exist() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        Mockito.when(mockRepository.existsByName(mockGroceries.getName())).thenReturn(true);

        Assertions.assertThrows(ItemExistsException.class, () -> mockService.create(mockGroceries));
    }

    @Test
    void create_Should_Throw_InvalidPriceFormatException_When_Groceries_Has_Negative_Or_0_As_Price() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        mockGroceries.setPrice(0);

        Assertions.assertThrows(InvalidPriceFormatException.class, () -> mockService.create(mockGroceries));
        mockGroceries.setPrice(-1);
        Assertions.assertThrows(InvalidPriceFormatException.class, () -> mockService.create(mockGroceries));
    }

    @Test
    void create_Should_Call_Repo_When_Valid() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();
        Mockito.when(mockRepository.existsByName(mockGroceries.getName())).thenReturn(false);
        mockService.create(mockGroceries);
        Mockito.verify(mockRepository, Mockito.times(1)).saveAndFlush(mockGroceries);
    }

    @Test
    void update_Should_Throw_InvalidPriceFormatException_When_Groceries_Has_Negative_Or_0_As_Price() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();

        mockGroceries.setPrice(0);

        Assertions.assertThrows(InvalidPriceFormatException.class, () -> mockService.update(mockGroceries));

        mockGroceries.setPrice(-1);

        Assertions.assertThrows(InvalidPriceFormatException.class, () -> mockService.update(mockGroceries));
    }

    @Test
    void update_Should_Call_Repo_When_Valid() {
        Groceries mockGroceries = CreateMockHelpers.mockGroceries();

        mockService.update(mockGroceries);

        Mockito.verify(mockRepository, Mockito.times(1)).saveAndFlush(mockGroceries);
    }

    @Test
    void delete_Should_Throw_GrocerieNotFoundException_If_Item_Does_Not_Exist() {
        Mockito.when(mockRepository.existsByName(Mockito.anyString())).thenReturn(false);

        Assertions.assertThrows(GrocerieNotFoundException.class,
                () -> mockService.delete(CreateMockHelpers.mockGroceries()));
    }

    @Test
    void delete_Should_Repo_When_Valid() {
        Mockito.when(mockRepository.existsByName(Mockito.anyString())).thenReturn(true);

        mockService.delete(CreateMockHelpers.mockGroceries());

        Mockito.verify(mockRepository, Mockito.times(1))
                .delete(CreateMockHelpers.mockGroceries());
    }


}
