package com.singularities.api.controller;

import com.singularities.api.dto.response.AnalyticStatsResponseDto;
import com.singularities.api.service.AnalyticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/analytics")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AnalyticController {

    private final AnalyticService analyticService;

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public AnalyticStatsResponseDto getStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return analyticService.getStatsByDateRangeStats(start, end);
    }
}
