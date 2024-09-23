package com.mealplan.project.mealplan;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealPlanService {
    @Autowired
    private MealPlanRepository repo;

    static final Logger log = LoggerFactory.getLogger(MealPlanService.class);

    public List<MealPlan> list() {
        log.info("Begun retrieving meal plan list...");
        try {
            List<MealPlan> listOfMealPlans = StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
            log.info("Meal plan list retrieval successful!");
            return listOfMealPlans;
        } catch (Exception e) {
            log.error("Meal plan list retrieval unsuccessful! Exception: {}", e.getMessage(), e);
            return null;
        }
    }

    public MealPlan getMealPlanById(Integer id) {
        log.info("Begun retrieving meal plan by ID: {}", id);
        try {
            MealPlan mealPlan = repo.findById(id).orElse(null);
            if (mealPlan != null) {
                log.info("Retrieved meal plan by ID: {} successfully!", id);
            } else {
                log.warn("No meal plan found with ID: {}", id);
            }
            return mealPlan;
        } catch (Exception e) {
            log.error("Meal plan by ID: {} retrieval unsuccessful! Exception: {}", id, e.getMessage(), e);
            return null;
        }
    }

    public MealPlan save(MealPlan mealPlan) {
        log.info("Begun saving meal plan with id: {}", mealPlan != null && mealPlan.getId() != null ? mealPlan.getId() : "null");
        try {
            MealPlan savedMealPlan = repo.save(mealPlan);
            log.info("Saving meal plan with id: {} successfully!", savedMealPlan.getId());
            return savedMealPlan;
        } catch (Exception e) {
            log.error("Saving meal plan with id: {} unsuccessful! Exception: {}", mealPlan != null && mealPlan.getId() != null ? mealPlan.getId() : "null", e.getMessage(), e);
            return null;
        }
    }

    public void delete(MealPlan mealPlan) {
        if (mealPlan != null) {
            log.info("Begun deleting meal plan with id: {}", mealPlan.getId());
            try {
                repo.delete(mealPlan);
                log.info("Meal plan with id: {} deleted successfully!", mealPlan.getId());
            } catch (Exception e) {
                log.error("Deletion of meal plan with id: {} unsuccessful! Exception: {}", mealPlan.getId(), e.getMessage(), e);
            }
        } else {
            log.warn("Attempted to delete a null meal plan.");
        }
    }

    public void deleteById(Integer id) {
        log.info("Begun deleting meal plan with id: {}", id);
        try {
            repo.deleteById(id);
            log.info("Meal plan with id: {} deleted successfully!", id);
        } catch (Exception e) {
            log.error("Deletion of meal plan with id: {} unsuccessful! Exception: {}", id, e.getMessage(), e);
        }
    }
}
