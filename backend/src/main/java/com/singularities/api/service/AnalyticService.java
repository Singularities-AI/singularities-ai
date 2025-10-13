package com.singularities.api.service;

import com.singularities.api.data.repository.ChatRepository;
import com.singularities.api.data.repository.MessageRepository;
import com.singularities.api.data.repository.UserRepository;
import com.singularities.api.dto.response.AnalyticsStatsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    public AnalyticsStatsResponseDto getStatsByDateRangeStats(LocalDateTime startDate, LocalDateTime endDate) {

        long userRegisterCount = userRepository.countByCreationDateBetween(startDate, endDate);
        long userLoginCount = userRepository.countByLastLoginBetween(startDate, endDate);
        long chatsCount = chatRepository.countByLastUpdateBetween(startDate, endDate);
        long messagesCount = messageRepository.countByLastUpdateBetween(startDate, endDate);

        return new AnalyticsStatsResponseDto(
                userRegisterCount,
                userLoginCount,
                chatsCount,
                messagesCount
        );
    }
}