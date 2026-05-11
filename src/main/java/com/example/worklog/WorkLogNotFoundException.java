package com.example.worklog;

public class WorkLogNotFoundException extends RuntimeException {

    public WorkLogNotFoundException(Long id) {
        super("Work log not found: " + id);
    }
}
