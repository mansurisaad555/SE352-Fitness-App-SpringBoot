package com.mealplan.project.meal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.mealplan.project.meal.dao.Meal;
import com.mealplan.project.meal.dao.MealRepository;
import com.mealplan.project.meal.dao.MealType;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest
public class MealRepositoryTest {
  @Autowired
  private MealRepository repo;



  @Test
  public void testLombok(){
 
    Meal m1 = Meal.builder().t(MealType.NORMAL).calories(500).fat(13).sugar(12).build();

    Integer cals = m1.getCalories();
    MealType t = m1.getT();

    assertEquals(500, cals);
    assertEquals(MealType.NORMAL, t);
    
  }

  @Test
  public void testMealRepository(){
    long before = repo.count();
  
    Meal m1 = Meal.builder().t(MealType.NORMAL).calories(2001).fat(13).sugar(11).build();
    Meal m2 = Meal.builder().t(MealType.NORMAL).calories(2002).fat(23).sugar(8).build();

    repo.save(m1);
    repo.save(m2);

    long after = repo.count();

    assertEquals(before+2, after);

    repo.delete(m2);
    after = repo.count();
    assertEquals(before+1, after);

    Meal m3 = repo.findById(5).orElse(new Meal());
    m3.setT(MealType.LOW_CARB);
    repo.save(m3);

    Meal m4 = repo.findById(5).orElse(new Meal());
    assertEquals(MealType.LOW_CARB, m4.getT());
    

  }
}
