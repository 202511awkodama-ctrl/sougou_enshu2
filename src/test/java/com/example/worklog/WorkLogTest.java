package com.example.worklog;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WorkLogTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void calculatesDurationMinutes() {
        WorkLog log = new WorkLog();
        log.setWorkDate(LocalDate.of(2026, 5, 11));
        log.setStartTime(LocalTime.of(9, 0));
        log.setEndTime(LocalTime.of(10, 30));
        log.setTaskTitle("設計");
        log.setCategory("開発");

        assertThat(log.getDurationMinutes()).isEqualTo(90);
        assertThat(log.getFormattedDuration()).isEqualTo("1時間30分");
    }

    @Test
    void rejectsEndTimeThatIsNotAfterStartTime() {
        WorkLog log = new WorkLog();
        log.setWorkDate(LocalDate.of(2026, 5, 11));
        log.setStartTime(LocalTime.of(10, 0));
        log.setEndTime(LocalTime.of(10, 0));
        log.setTaskTitle("レビュー");
        log.setCategory("開発");

        Set<ConstraintViolation<WorkLog>> violations = validator.validate(log);

        assertThat(violations)
                .anyMatch(violation -> violation.getMessage().equals("終了時刻は開始時刻より後にしてください"));
    }
}
