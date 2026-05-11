package com.example.worklog;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(WorkLogService.class)
class WorkLogServiceTest {

    @Autowired
    private WorkLogRepository repository;

    @Autowired
    private WorkLogService service;

    @Test
    void returnsDailyTotalMinutesForSelectedDate() {
        LocalDate targetDate = LocalDate.of(2026, 5, 11);
        repository.save(log(targetDate, 9, 0, 10, 30, "設計"));
        repository.save(log(targetDate, 11, 0, 12, 0, "実装"));
        repository.save(log(LocalDate.of(2026, 5, 12), 9, 0, 17, 0, "別日"));

        assertThat(service.calculateTotalMinutes(targetDate)).isEqualTo(150);
        assertThat(service.formatMinutes(150)).isEqualTo("2時間30分");
    }

    private WorkLog log(LocalDate date, int startHour, int startMinute, int endHour, int endMinute, String title) {
        WorkLog log = new WorkLog();
        log.setWorkDate(date);
        log.setStartTime(LocalTime.of(startHour, startMinute));
        log.setEndTime(LocalTime.of(endHour, endMinute));
        log.setTaskTitle(title);
        log.setCategory("開発");
        return log;
    }
}
