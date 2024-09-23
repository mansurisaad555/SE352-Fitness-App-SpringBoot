package com.mealplan.project.mealplan;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.mealplan.project.meal.dao.Meal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MealPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Meal> meals;
    
}
