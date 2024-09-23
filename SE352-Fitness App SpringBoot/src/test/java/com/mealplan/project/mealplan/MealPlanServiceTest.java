package com.mealplan.project.mealplan;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.MockitoAnnotations;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class MealPlanServiceTest {

    @Mock
    private MealPlanRepository mealPlanRepository;

    @Autowired
    @InjectMocks
    private MealPlanService mealPlanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testList() {
        MealPlan mealPlan1 = MealPlan.builder().id(1).name("Plan 1").build();
        MealPlan mealPlan2 = MealPlan.builder().id(2).name("Plan 2").build();
        
        when(mealPlanRepository.findAll()).thenReturn(Arrays.asList(mealPlan1, mealPlan2));
        
        var mealPlans = mealPlanService.list();
        
        assertEquals(2, mealPlans.size());
        verify(mealPlanRepository, times(1)).findAll();
    }

    @Test
    public void testGetMealPlanById() {
        MealPlan mealPlan = MealPlan.builder().id(1).name("Plan 1").build();
        
        when(mealPlanRepository.findById(1)).thenReturn(Optional.of(mealPlan));
        
        MealPlan foundMealPlan = mealPlanService.getMealPlanById(1);
        
        assertNotNull(foundMealPlan);
        assertEquals(1, foundMealPlan.getId());
        verify(mealPlanRepository, times(1)).findById(1);
    }

    @Test
    public void testSave() {
        MealPlan mealPlan = MealPlan.builder().id(1).name("Plan 1").build();
        
        when(mealPlanRepository.save(mealPlan)).thenReturn(mealPlan);
        
        MealPlan savedMealPlan = mealPlanService.save(mealPlan);
        
        assertNotNull(savedMealPlan);
        assertEquals(1, savedMealPlan.getId());
        verify(mealPlanRepository, times(1)).save(mealPlan);
    }

    @Test
    public void testDelete() {
        MealPlan mealPlan = MealPlan.builder().id(1).name("Plan 1").build();
        
        mealPlanService.delete(mealPlan);
        
        verify(mealPlanRepository, times(1)).delete(mealPlan);
    }

    @Test
    public void testDeleteById() {
        mealPlanService.deleteById(1);
        
        verify(mealPlanRepository, times(1)).deleteById(1);
    }
}
