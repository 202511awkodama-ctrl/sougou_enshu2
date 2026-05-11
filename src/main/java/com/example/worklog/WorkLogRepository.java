package com.example.worklog;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    List<WorkLog> findAllByOrderByWorkDateDescStartTimeDesc();

    List<WorkLog> findByWorkDateOrderByStartTimeAsc(LocalDate workDate);
}
