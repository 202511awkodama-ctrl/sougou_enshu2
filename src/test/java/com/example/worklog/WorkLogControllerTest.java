package com.example.worklog;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class WorkLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkLogRepository repository;

    @BeforeEach
    void clearRepository() {
        repository.deleteAll();
    }

    @Test
    void rootRedirectsToLogs() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/logs"));
    }

    @Test
    void listsLogsAndDailyTotalWhenFilteredByDate() throws Exception {
        repository.save(log(LocalDate.of(2026, 5, 11), LocalTime.of(9, 0), LocalTime.of(10, 30), "画面作成"));
        repository.save(log(LocalDate.of(2026, 5, 11), LocalTime.of(11, 0), LocalTime.of(12, 0), "テスト"));

        mockMvc.perform(get("/logs").param("date", "2026-05-11"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("画面作成")))
                .andExpect(content().string(containsString("合計作業時間")))
                .andExpect(content().string(containsString("2時間30分")));
    }

    @Test
    void rejectsInvalidTimeRangeOnCreate() throws Exception {
        mockMvc.perform(post("/logs")
                        .param("workDate", "2026-05-11")
                        .param("startTime", "10:00")
                        .param("endTime", "09:00")
                        .param("taskTitle", "逆転した時刻")
                        .param("category", "開発"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("終了時刻は開始時刻より後にしてください")));
    }

    private WorkLog log(LocalDate date, LocalTime startTime, LocalTime endTime, String title) {
        WorkLog log = new WorkLog();
        log.setWorkDate(date);
        log.setStartTime(startTime);
        log.setEndTime(endTime);
        log.setTaskTitle(title);
        log.setCategory("開発");
        return log;
    }
}
