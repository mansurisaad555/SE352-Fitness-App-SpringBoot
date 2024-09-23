package com.mealplan.project.workout;

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
public class WorkoutServiceTest {

    @Mock
    private WorkoutRepository WorkoutRepository;

    @Autowired
    @InjectMocks
    private WorkoutService WorkoutService;

    @BeforeEach
    public void setUp() {MockitoAnnotations.openMocks(this);}

    @Test
    public void testList() {
        when(WorkoutRepository.findAll()).thenReturn(Arrays.asList(Workout.builder().id(1).type(WorkoutType.NORMAL).details("Normal workout").build(), Workout.builder().id(2).type(WorkoutType.STRENGTH).details("Strength workout").build()));
        assertEquals(2, WorkoutService.list().size());
        verify(WorkoutRepository, times(1)).findAll();
    }

    @Test
    public void testGetById() {
        when(WorkoutRepository.findById(1)).thenReturn(Optional.of(Workout.builder().id(1).type(WorkoutType.NORMAL).details("Normal workout").build()));
        Workout foundWorkout = WorkoutService.getWorkoutById(1);
        assertNotNull(foundWorkout);
        verify(WorkoutRepository, times(1)).findById(1);
        assertEquals(1, foundWorkout.getId());
        assertEquals(WorkoutType.NORMAL, foundWorkout.getType());
        assertEquals("Normal workout", foundWorkout.getDetails());
    }

    @Test
    public void testSave() {
        
        when(WorkoutRepository.save(Workout.builder().id(1).type(WorkoutType.NORMAL).details("Normal workout").build())).thenReturn(Workout.builder().id(1).type(WorkoutType.NORMAL).details("Normal workout").build());
        assertNotNull(WorkoutService.save(Workout.builder().id(1).type(WorkoutType.NORMAL).details("Normal workout").build()));
        // Workout savedWorkout = WorkoutService.save(Workout.builder().id(1).type(WorkoutType.NORMAL).details("Normal workout").build());
        // assertEquals(1, savedWorkout.getId());
        // assertEquals(WorkoutType.NORMAL, savedWorkout.type);
        // assertEquals("Normal workout", savedWorkout.getDetails());
        verify(WorkoutRepository, times(1)).save(Workout.builder().id(1).type(WorkoutType.NORMAL).details("Normal workout").build());
    }

    @Test
    public void testDelete() {        
        WorkoutService.deleteWorkout(Workout.builder().id(1).type(WorkoutType.NORMAL).details("Normal workout").build());
        verify(WorkoutRepository, times(1)).delete(Workout.builder().id(1).type(WorkoutType.NORMAL).details("Normal workout").build());
    }
}
