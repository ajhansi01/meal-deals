package com.mealDeals.capstone.service;

import com.mealDeals.capstone.model.MenuItem;
import com.mealDeals.capstone.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MenuItemServiceTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuItemService menuItemService;

    private MenuItem menuItem;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        menuItem = new MenuItem();
        menuItem.setName("Test Menu Item");
        menuItem.setPrice(10.0);
    }

    @Test
    public void createMenuItem_shouldReturnMenuItem() {
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItem createdMenuItem = menuItemService.createMenuItem(menuItem);

        assertNotNull(createdMenuItem);
        assertEquals("Test Menu Item", createdMenuItem.getName());
    }

    @Test
    public void updateMenuItem_shouldReturnUpdatedMenuItem() {
        Long menuItemId = 1L;
        menuItem.setName("Updated Menu Item");
        when(menuItemRepository.findById(menuItemId)).thenReturn(java.util.Optional.of(menuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItem updatedMenuItem = menuItemService.updateMenuItem(menuItemId, menuItem);

        assertNotNull(updatedMenuItem);
        assertEquals("Updated Menu Item", updatedMenuItem.getName());
    }
}
