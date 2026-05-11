package com.example.worklog;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkLogService {

    private final WorkLogRepository repository;

    public WorkLogService(WorkLogRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<WorkLog> findLogs(LocalDate date) {
        if (date == null) {
            return repository.findAllByOrderByWorkDateDescStartTimeDesc();
        }
        return repository.findByWorkDateOrderByStartTimeAsc(date);
    }

    @Transactional(readOnly = true)
    public WorkLog findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new WorkLogNotFoundException(id));
    }

    public WorkLog save(WorkLog workLog) {
        return repository.save(workLog);
    }

    public WorkLog update(Long id, WorkLog form) {
        WorkLog workLog = findById(id);
        workLog.setWorkDate(form.getWorkDate());
        workLog.setStartTime(form.getStartTime());
        workLog.setEndTime(form.getEndTime());
        workLog.setTaskTitle(form.getTaskTitle());
        workLog.setCategory(form.getCategory());
        workLog.setMemo(form.getMemo());
        return repository.save(workLog);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new WorkLogNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long calculateTotalMinutes(LocalDate date) {
        if (date == null) {
            return 0;
        }
        return repository.findByWorkDateOrderByStartTimeAsc(date)
                .stream()
                .mapToLong(WorkLog::getDurationMinutes)
                .sum();
    }

    public String formatMinutes(long minutes) {
        return minutes / 60 + "時間" + minutes % 60 + "分";
    }
}
