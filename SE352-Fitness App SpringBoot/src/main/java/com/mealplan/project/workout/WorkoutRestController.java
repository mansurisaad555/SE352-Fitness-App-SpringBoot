package com.mealplan.project.workout;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("workouts")
public class WorkoutRestController {

    @Autowired
    private final WorkoutService workoutService;
    
    public WorkoutRestController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public List<Workout> getAllWorkouts() {
        return workoutService.list();
    }

    @GetMapping("/{id}")
    public Workout getWorkoutById(@PathVariable int id) {
        return workoutService.getWorkoutById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkout(@PathVariable int id) {
        workoutService.deleteWorkout(getWorkoutById(id));
    }
}