package com.ondev.demohighdispoendpoind.service.impl;

import com.ondev.demohighdispoendpoind.dto.EtudiantDto;
import com.ondev.demohighdispoendpoind.dto.GlobalApiResponse;
import com.ondev.demohighdispoendpoind.dto.StudentRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import com.ondev.demohighdispoendpoind.service.IStudentService;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * @author MJean-Claude
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    @Qualifier("webClientStudent")
    private final WebClient webClient;
    @Override
    public Mono<EtudiantDto> getStudent() {
        return webClient.get()
                .uri("/e2c2256f-4c60-4541-b423-67fd56f5a782")
                .retrieve()
                .bodyToMono(EtudiantDto.class)
                .map(etudiantDto -> {
                    log.info("EtudiantDto: {}", etudiantDto);
                    return etudiantDto.toBuilder().age(15).build();
                })
                .retryWhen(Retry.backoff(4, Duration.ofSeconds(1)) // Réessayer 3 fois avec un délai exponentiel
                        .filter(throwable -> {
                            // Réessayer uniquement en cas d'erreur 429
                            if (throwable instanceof WebClientResponseException) {
                                WebClientResponseException ex = (WebClientResponseException) throwable;
                                log.info("Code HTTP de l'erreur : {} -- on va reesayer", ex.getStatusCode().value());
                                return ex.getStatusCode().value() == 429; // Code HTTP 429
                            }
                            return false;
                        })
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                            // Lancer une exception personnalisée après épuisement des réessais
                            throw new RuntimeException("Échec après " + retrySignal.totalRetries() + " tentatives");
                        })
                )
                .onErrorResume(throwable -> {
                    // Gérer les autres erreurs ou les erreurs après épuisement des réessais
                    log.error("Erreur lors de la récupération de l'étudiant : {}", throwable.getMessage());
                    return Mono.just(EtudiantDto.builder()
                            .nom("Inconnu")
                            .prenom("Inconnu")
                            .age(0)
                            .build()); // Retourner une valeur par défaut
                });
    }

    @Override
    public Mono<GlobalApiResponse> processStudentRequest(StudentRequestDto request) {
        return getStudent().flatMap(etudiantDto ->{
            String responseMessage = String.format(
                    "Traitement de la requête : id=%s, path=%s, status=%s",
                    request.getId(), request.getPath(), request.getStatus()
            );
            return Mono.just(GlobalApiResponse.builder()
                    .message(responseMessage)
                    .studentRequestDto(request)
                    .etudiantDto(etudiantDto)
                    .build());
        })
                .map(globalApiResponse -> {
                    log.info("GlobalApiResponse: {}", globalApiResponse);
                    return globalApiResponse;
                });
    }
}
