package com.profile.setupprofilewithai.repositories;

import com.profile.setupprofilewithai.domain.Transcription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories(basePackages = "com.profile.setupprofilewithai.repositories")
@Repository
public interface TranscriptionRepository extends JpaRepository<Transcription, Long> {}
