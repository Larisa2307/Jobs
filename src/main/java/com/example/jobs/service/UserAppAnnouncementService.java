package com.example.jobs.service;

import com.example.jobs.entity.Announcement;
import com.example.jobs.entity.Company;
import com.example.jobs.entity.Job;
import com.example.jobs.entity.UserApp;
import com.example.jobs.mapper.CandidateMapper;
import com.example.jobs.mapper.JobMapper;
import com.example.jobs.model.CandidateModel;
import com.example.jobs.model.JobModel;
import com.example.jobs.repository.UserAppAnnouncementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@AllArgsConstructor
public class UserAppAnnouncementService {
    final UserAppAnnouncementRepository userAppAnnouncementRepository;

    public List<UserApp> getUserAppByAnnouncement(Announcement announcement) {
        return userAppAnnouncementRepository.findUserAppByAnnouncement(announcement);
    }

    private UserApp toCandidate(CandidateModel candidateModel) {
        UserApp userApp = CandidateMapper.toEntry(candidateModel);

        return userApp;
    }

    private CandidateModel toCandidateModel(UserApp userApp, int index, String accepted) {
        if (accepted == null) {
            accepted = "-";
        }
        return CandidateMapper.toModel(userApp, index, accepted);
    }

    public List<CandidateModel> getCandidateModelList(Announcement announcement) {
        var candidates = userAppAnnouncementRepository.findCandidatesByAnnouncement(announcement);

        var index = new AtomicInteger(1);
        return candidates.stream()
                .map(candidate -> toCandidateModel(candidate.getCandidate(), index.getAndIncrement(), candidate.getAccepted()))
                .toList();
    }
}
