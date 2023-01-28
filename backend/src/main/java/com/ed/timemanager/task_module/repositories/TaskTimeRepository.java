package com.ed.timemanager.task_module.repositories;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ed.timemanager.auth_module.models.User;
import com.ed.timemanager.task_module.models.Task;
import com.ed.timemanager.task_module.models.TaskTime;

@Repository
public interface TaskTimeRepository extends CrudRepository<TaskTime, UUID> {
    
    @Query("SELECT tt FROM TaskTime tt LEFT JOIN FETCH tt.userTask ut WHERE ut.user = :user")
    List<TaskTime> findByUser(@Param("user") User user);

    @Query("SELECT tt FROM TaskTime tt LEFT JOIN FETCH tt.userTask ut WHERE ut.task = :task")
    List<TaskTime> findByTask(@Param("task") Task task);

    @Query("SELECT tt FROM TaskTime tt LEFT JOIN FETCH tt.userTask ut WHERE ut.user = :user AND (startTime < :endTime OR endTime > :startTime)")
    List<TaskTime> findByUserBetweenDates(
        @Param("user") User user,
        @Param("startTime") Instant startTime,
        @Param("endTime") Instant endTime
    );
}
