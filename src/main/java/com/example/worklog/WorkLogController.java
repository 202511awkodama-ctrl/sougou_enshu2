package com.example.worklog;

import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WorkLogController {

    private final WorkLogService service;

    public WorkLogController(WorkLogService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/logs";
    }

    @GetMapping("/logs")
    public String index(
            @RequestParam(name = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Model model) {
        var logs = service.findLogs(date);
        model.addAttribute("logs", logs);
        model.addAttribute("selectedDate", date);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("logCount", logs.size());
        model.addAttribute("totalDuration", date == null ? null : service.formatMinutes(service.calculateTotalMinutes(date)));
        return "logs/index";
    }

    @GetMapping("/logs/new")
    public String newForm(Model model) {
        WorkLog workLog = new WorkLog();
        workLog.setWorkDate(LocalDate.now());
        model.addAttribute("workLog", workLog);
        model.addAttribute("formAction", "/logs");
        model.addAttribute("pageTitle", "作業ログ作成");
        return "logs/form";
    }

    @PostMapping("/logs")
    public String create(
            @Valid @ModelAttribute("workLog") WorkLog workLog,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("formAction", "/logs");
            model.addAttribute("pageTitle", "作業ログ作成");
            return "logs/form";
        }
        service.save(workLog);
        redirectAttributes.addFlashAttribute("message", "作業ログを作成しました");
        return "redirect:/logs";
    }

    @GetMapping("/logs/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("workLog", service.findById(id));
        model.addAttribute("formAction", "/logs/" + id);
        model.addAttribute("pageTitle", "作業ログ編集");
        return "logs/form";
    }

    @PostMapping("/logs/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("workLog") WorkLog workLog,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            workLog.setId(id);
            model.addAttribute("formAction", "/logs/" + id);
            model.addAttribute("pageTitle", "作業ログ編集");
            return "logs/form";
        }
        service.update(id, workLog);
        redirectAttributes.addFlashAttribute("message", "作業ログを更新しました");
        return "redirect:/logs";
    }

    @PostMapping("/logs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.delete(id);
        redirectAttributes.addFlashAttribute("message", "作業ログを削除しました");
        return "redirect:/logs";
    }

    @ExceptionHandler(WorkLogNotFoundException.class)
    public String handleNotFound(Model model) {
        model.addAttribute("errorMessage", "指定された作業ログが見つかりません");
        return "error/404";
    }
}
