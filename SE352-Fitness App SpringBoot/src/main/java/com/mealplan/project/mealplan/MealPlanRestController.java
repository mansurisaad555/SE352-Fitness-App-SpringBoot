package com.mealplan.project.mealplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mealplans")
public class MealPlanRestController {
    @Autowired
    private MealPlanService service;

    @GetMapping
    public List<MealPlan> getAllMealPlans() {
        return service.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MealPlan> getMealPlanById(@PathVariable Integer id) {
        MealPlan mealPlan = service.getMealPlanById(id);
        if (mealPlan == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mealPlan);
    }

    @PostMapping
    public MealPlan createMealPlan(@RequestBody MealPlan mealPlan) {
        return service.save(mealPlan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MealPlan> updateMealPlan(@PathVariable Integer id, @RequestBody MealPlan mealPlanDetails) {
        MealPlan mealPlan = service.getMealPlanById(id);
        if (mealPlan == null) {
            return ResponseEntity.notFound().build();
        }
        mealPlan.setName(mealPlanDetails.getName());
        mealPlan.setMeals(mealPlanDetails.getMeals());
        final MealPlan updatedMealPlan = service.save(mealPlan);
        return ResponseEntity.ok(updatedMealPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealPlan(@PathVariable Integer id) {
        MealPlan mealPlan = service.getMealPlanById(id);
        if (mealPlan == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
