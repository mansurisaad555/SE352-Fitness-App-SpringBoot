package com.mealplan.project.person;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.mealplan.project.meal.MealService;
import com.mealplan.project.meal.dao.Meal;
import com.mealplan.project.person.dao.Person;
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
@SpringBootTest
public class PersonServiceTest {
  
  @Autowired
  private PersonService personService;

  @Test
  public void testAddPerson(){

    Person p1 = new Person();
    p1.setName("Harry");
    p1.setAge(29);
    Person p2 = new Person();
    p2.setName("Sally");
    p2.setAge(25);
    Person p3 = new Person();
    p3.setName("Jimmy");
    p3.setAge(65);

    long sizeB4 = personService.list().size();

    personService.create(p1);
    personService.create(p2);
    personService.create(p3);

    assertEquals(sizeB4+3, personService.list().size());
  }

  @Test
  public void testGetAndUpdatePerson(){
    Person p1 = new Person();
    p1.setId(1);
    p1.setName("Harry");
    p1.setAge(29);
  
    personService.create(p1);

    Person p2 = personService.getPersonById(1);
    p2.setAge(45);
    personService.create(p2);

    Person p3 = personService.getPersonById(1);

    assertEquals("Harry", p3.getName());
    assertEquals(45, p3.getAge());
  }

  
  @Test
  public void testDeletePerson(){
    Person p1 = new Person();
    p1.setName("Harry");
    p1.setAge(29);
    Person p2 = new Person();
    p2.setName("Sally");
    p2.setAge(25);
    Person p3 = new Person();
    p3.setName("Jimmy");
    p3.setAge(65);

    long sizeB4 = personService.list().size();

    personService.create(p1);
    personService.create(p2);
    personService.create(p3);

    assertEquals(sizeB4+3, personService.list().size());

    personService.delete(p3);

    assertEquals(sizeB4+2, personService.list().size());;

  }

  @Test
  public void testDeletePersonById(){
    Person p1 = new Person();
    p1.setName("Harry");
    p1.setAge(29);
    Person p2 = new Person();
    p2.setName("Sally");
    p2.setAge(25);
    Person p3 = new Person();
    p3.setName("Jimmy");
    p3.setAge(65);

    long sizeB4 = personService.list().size();

    personService.create(p1);
    personService.create(p2);
    personService.create(p3);

    assertEquals(sizeB4+3, personService.list().size());

    personService.deleteById(p3.getId());

    assertEquals(sizeB4+2, personService.list().size());;

  }
  
}
