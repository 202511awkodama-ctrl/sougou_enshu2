package com.example.worklog;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleDataLoader implements CommandLineRunner {

    private final WorkLogRepository repository;

    public SampleDataLoader(WorkLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() > 0) {
            return;
        }

        LocalDate today = LocalDate.now();
        repository.save(create(today, 9, 0, 10, 30, "要件整理", "計画", "MVP範囲を確認"));
        repository.save(create(today, 10, 45, 12, 0, "CRUD実装", "開発", "作業ログの登録と更新"));
        repository.save(create(today, 13, 0, 15, 0, "画面作成", "開発", "一覧とフォームを作成"));
        repository.save(create(today.minusDays(1), 9, 30, 11, 0, "テスト追加", "品質", "ServiceとControllerの確認"));
        repository.save(create(today.minusDays(2), 14, 0, 16, 30, "レビュー対応", "改善", "表示文言を調整"));
    }

    private WorkLog create(LocalDate date, int startHour, int startMinute, int endHour, int endMinute,
            String title, String category, String memo) {
        WorkLog log = new WorkLog();
        log.setWorkDate(date);
        log.setStartTime(LocalTime.of(startHour, startMinute));
        log.setEndTime(LocalTime.of(endHour, endMinute));
        log.setTaskTitle(title);
        log.setCategory(category);
        log.setMemo(memo);
        return log;
    }
}
