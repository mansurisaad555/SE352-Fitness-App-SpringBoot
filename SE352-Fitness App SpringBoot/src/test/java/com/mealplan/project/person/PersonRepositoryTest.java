package com.mealplan.project.person;
import com.mealplan.project.mealplan.MealPlan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.mealplan.project.meal.dao.Meal;
import com.mealplan.project.meal.dao.MealRepository;
import com.mealplan.project.meal.dao.MealType;
import com.mealplan.project.mealplan.MealPlanRepository;
import com.mealplan.project.person.dao.Gender;
import com.mealplan.project.person.dao.Person;
import com.mealplan.project.person.dao.PersonRepository;


@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest
public class PersonRepositoryTest {
  @Autowired
  
  private PersonRepository personRepo;
  @Autowired
  
  private MealRepository mealRepo;
  @Autowired
  

  private MealPlanRepository mpRepo;
  
  @BeforeEach
  public void setup(){
    
  }

 
  @Test
  public void testLombok(){

    Person u1 = new Person();
    u1.setName("Tester");
    u1.setAge(18);
    
    String name = u1.getName();
    Integer age = u1.getAge();

    assertEquals("Tester", name);
    assertEquals(18, age);
  }

  @Test
  public void testPersonRepository(){
    List<Person> persons = new ArrayList<>();

    Person u1 = new Person();
    Person u2 = new Person();
  
    Meal m1 = mealRepo.findById(1).orElse(new Meal());
    if (m1.getId() == null){
      m1.setId(1);
      mealRepo.save(m1);
    }
    long before = personRepo.count();
    u1.setId(1);
    u1.setName("Harry");
    u1.setAge(29);
    u1.setGender(Gender.M);
    u1.setHeight(180.82);
    u1.setWeight(185.00);
    u1.getMeals().add(m1);
    personRepo.save(u1);
    persons.add(u1);
    u2.setId(2);
    u2.setName("Sally");
    u2.setAge(27);
    u2.setGender(Gender.F);
    u2.setHeight(150.4);
    u2.setWeight(125.00);
    u2.getMeals().add(m1);
    personRepo.save(u2);
    persons.add(u2);


    long size = personRepo.count();
    assertEquals(2+before, size);

    personRepo.delete(u2);
    assertEquals(persons.size()+before-1, personRepo.count());

    Person u3 = personRepo.findById(1).orElse(new Person());
    u3.setWeight(200.00);
    personRepo.save(u3);

    Person u4 = personRepo.findById(1).orElse(new Person());
    assertEquals("Harry", u4.getName());
    assertEquals(200.00, u4.getWeight());

  }
  @Test
  public void testMealAndPersonRepository(){

    Meal m1 = mealRepo.findById(1).orElse(new Meal());
    if (m1.getId() == null){
      m1.setId(1);
      mealRepo.save(m1);
    }
    Person p1 = Person.builder().name("Harry").age(29).gender(Gender.M).meals(new ArrayList<>()).build();
    long before = p1.getMeals().size();
    p1.getMeals().add(m1);
    
    personRepo.save(p1);

    Person p2 = personRepo.findById(1).orElseThrow();
    assertEquals(before+1, p2.getMeals().size());

    Meal m2 = Meal.builder().id(3).t(MealType.PROTEIN_HEAVY).build();
    mealRepo.save(m2);
    assertEquals(3, mealRepo.count());

    p2.getMeals().add(m2);
    personRepo.save(p2);
    Person p3 = personRepo.findById(1).orElse(new Person());
    assertEquals(before+2, p3.getMeals().size());

    p3.getMeals().remove(1);
    personRepo.save(p3);
    Person p4 = personRepo.findById(1).orElse(new Person());
    assertEquals(before+1, p4.getMeals().size());
    long b4 = personRepo.count();
    personRepo.deleteById(1);
    assertEquals(b4-1, personRepo.count());

    assertEquals(3, mealRepo.count());
  }

  @Test
  public void testMealAndMealPlanAndPersonRepository(){

    Person p1 = Person.builder().name("Harry").age(29).gender(Gender.M).meals(new ArrayList<>()).mealPlans(new ArrayList<>()).build();
    personRepo.save(p1);

    MealPlan mp1 = new MealPlan();
    mp1.setMeals(new ArrayList<>());

    MealPlan mp2 = new MealPlan();
    mp2.setMeals(new ArrayList<>());

    Meal m1 = mealRepo.findById(1).orElse(new Meal());
    Meal m2 = mealRepo.findById(2).orElse(new Meal());
    if (m1.getId() == null){
      m1.setId(1);
      mealRepo.save(m1);
    }
    if (m2.getId() == null){
      m2.setId(2);
      mealRepo.save(m2);
    }
    mp1.getMeals().add(m1);
    mp1.getMeals().add(m2);
    mpRepo.save(mp1);
    mp2.getMeals().add(m1);
    mp2.getMeals().add(m2);
    mpRepo.save(mp2);
    assertEquals(2, mpRepo.count());
    long before = personRepo.count();
    Person p2 = personRepo.findById(1).orElse(new Person());
    p2.getMeals().add(m1);
    p2.getMeals().add(m2);
    p2.getMealPlans().add(mp1);
    p2.getMealPlans().add(mp2);
    personRepo.save(p2);
    Person p3 = personRepo.findById(1).orElse(new Person());
    assertEquals(before, personRepo.count());
    assertEquals(2, p3.getMeals().size());
    assertEquals(2, p3.getMealPlans().size());

    MealPlan mp3 = p3.getMealPlans().get(1);
    mp3.getMeals().remove(1);
    mpRepo.save(mp3);
    personRepo.save(p3); 

    Person p4 = personRepo.findById(1).orElse(new Person());
    MealPlan mp4  = p4.getMealPlans().get(1);
    assertEquals(1, mp4.getMeals().size());

    p4.getMealPlans().remove(1);
    personRepo.save(p4);

    Person p5 = personRepo.findById(1).orElse(new Person());
    assertEquals(1, p5.getMealPlans().size());



  }
 
  
}
