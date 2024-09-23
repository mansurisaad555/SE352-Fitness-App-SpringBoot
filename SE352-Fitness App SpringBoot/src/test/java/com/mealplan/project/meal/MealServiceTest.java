package com.mealplan.project.meal;


import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import org.springframework.test.annotation.DirtiesContext;

import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.mealplan.project.meal.dao.Meal;
import com.mealplan.project.meal.dao.MealType;
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest
public class MealServiceTest {
  @Autowired
  private MealService service;


  @Test
  public void testInitListSize(){
    List<Meal> list = service.list();
    assertEquals(2, list.size());
  }
 

  @Test
  public void testAddMeal(){
    long before = service.list().size();
   
    service.create(Meal.builder().t(MealType.NORMAL).calories(2001).fat(13).sugar(11).build());
    long after = service.list().size();
    assertEquals(before+1, after);
  }

  @Test 
  public void testGetandUpdateMeal(){
    Meal m1 = service.getMealById(1);
    m1.setT(MealType.LOW_CARB);
    service.create(m1);

    Meal m2 = service.getMealById(1);
    assertEquals(m1.getT(), m2.getT());
  }

  @Test
  public void testDeleteMeal(){
    long before = service.list().size();
    Meal m1 = service.getMealById(1);
    if(m1 == null){return;}
    service.delete(m1);
    long after = service.list().size();
    assertEquals(before-1, after);

  }

  @Test 
  public void testDeleteMealById(){
    long before = service.list().size();
    service.deleteById(1);
    long after = service.list().size();
    assertEquals(before-1, after);
  }

}
