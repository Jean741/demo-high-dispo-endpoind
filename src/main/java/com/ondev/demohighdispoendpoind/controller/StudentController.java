package com.ondev.demohighdispoendpoind.controller;

import com.ondev.demohighdispoendpoind.dto.EtudiantDto;
import com.ondev.demohighdispoendpoind.dto.GlobalApiResponse;
import com.ondev.demohighdispoendpoind.dto.StudentRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import com.ondev.demohighdispoendpoind.service.IStudentService;

/**
 * @author MJean-Claude
 */
@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    private final IStudentService studentService;

    @GetMapping
    public Mono<EtudiantDto> getStudent() {
        return studentService.getStudent();
    }

    @PostMapping("add")
    public Mono<GlobalApiResponse> handleStudentRequest(@Valid @RequestBody StudentRequestDto request) {
        return studentService.processStudentRequest(request);
    }
}
