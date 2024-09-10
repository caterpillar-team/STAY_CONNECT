package com.caterpillars.StayConnect.controller;

import com.caterpillars.StayConnect.model.dto.AccommodationDto;
import com.caterpillars.StayConnect.service.AccommodationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class SearchController {

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping("/search")
    public @ResponseBody ResponseEntity<List<AccommodationDto>> search(String searchText) {
        List<AccommodationDto> result = accommodationService.search(searchText);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}
