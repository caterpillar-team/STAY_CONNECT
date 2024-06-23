package com.caterpillars.StayConnect.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caterpillars.StayConnect.model.entities.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {


}
