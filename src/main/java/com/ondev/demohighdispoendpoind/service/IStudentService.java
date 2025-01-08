package com.ondev.demohighdispoendpoind.service;

import com.ondev.demohighdispoendpoind.dto.EtudiantDto;
import com.ondev.demohighdispoendpoind.dto.GlobalApiResponse;
import com.ondev.demohighdispoendpoind.dto.StudentRequestDto;
import reactor.core.publisher.Mono;

/**
 * @author MJean-Claude
 */
public interface IStudentService {
    Mono<EtudiantDto> getStudent();

    Mono<GlobalApiResponse> processStudentRequest(StudentRequestDto request);
}
