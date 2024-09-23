package com.mealplan.project.workout;

import java.util.List;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    private static final Logger log = LoggerFactory.getLogger(WorkoutService.class);

    public List<Workout> list() {
        log.info("Begun retrieving all workouts...");
        try {
            List<Workout> workouts = workoutRepository.findAll();
            log.info("Retrieved {} workouts successfully", workouts.size());
            for(Workout ws : workouts){log.info("Retrieved workout -> id: {} -> type: {} -> details: {}", ws.getId(), ws.type, ws.getDetails());}
            return workouts;
        } catch (Exception e) {
            log.error("Error occurred while retrieving workouts: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Workout getWorkoutById(int id) {
        log.info("Begun retrieving workout by ID: {}", id);
        try {
            Workout workout = workoutRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Workout not found"));
            log.info("Retrieved workout with ID: {} -> type: {} -> details: {}: successfully", id, workout.type, workout.getDetails());
            return workout;
        } catch (NoSuchElementException e) {
            log.warn("No workout found with ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error occurred while retrieving workout with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    public void deleteWorkout(Workout workout) {
        log.info("Begun deleting workout with ID: {} -> type: {} -> details: {}", workout.getId(), workout.type, workout.getDetails());
        try {
            workoutRepository.delete(workout);
            log.info("Deleted workout with ID: {} -> type: {} -> details: {}", workout.getId(), workout.type, workout.getDetails());
        } catch (Exception e) {
            log.error("Error occurred while deleting workout with ID {}: {}", workout.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @SuppressWarnings("null")
    public Workout save(Workout workout) {
       log.info("Begun saving workout with id: {} -> type: {} -> details: {}", workout != null && workout.getId() != null ? workout.getId() : "null", workout.getType(), workout.getDetails());
       try {
          Workout savedWorkout = workoutRepository.save(workout);
          log.info("Saving Workout with id: {} -> type: {} -> details: {}: successfully!", savedWorkout.getId(), workout.getType(), workout.getDetails());
          return savedWorkout;
       } catch (Exception e) {
          log.error("Saving Workout with id: {} unsuccessful! Exception: {}", workout != null && workout.getId() != null ? workout.getId() : "null", e.getMessage(), e);
          return null;
        }
    }
}