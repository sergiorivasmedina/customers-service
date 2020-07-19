package com.bootcamp.customer.controllers;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import com.bootcamp.customer.model.Personal;
import com.bootcamp.customer.repositories.PersonalRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PersonalController.class)
public class PersonalControllerTest {
    @MockBean
    PersonalRepository repository;

    @Autowired
    private WebTestClient webclient;
    
    @Test
    public void getAllPersonalCsutmers() {
        Personal personal = new Personal("1", "Personal", "71205568");

        List<Personal> list = new ArrayList<Personal>();
        list.add(personal);
         
        Flux<Personal> personalFlux = Flux.fromIterable(list);

        Mockito
            .when(repository.findAll())
            .thenReturn(personalFlux);

        webclient.get()
            .uri("/personal")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Personal.class);

            Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    public void newPersonal() {
        Personal personal = new Personal("1", "Personal", "71205568");

        Mockito
            .when(repository.save(personal))
            .thenReturn(Mono.just(personal));
        
        webclient.post()
            .uri("/personal/new")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(personal))
            .exchange()
            .expectStatus().isOk()
            .expectBody(Personal.class);
    }

    @Test
    public void deleteAccountType() {
        Personal personal = new Personal("1", "Personal", "71205568");

        Mockito
            .when(repository.findById("1"))
            .thenReturn(Mono.just(personal));

        Mono<Void> voidReturn  = Mono.empty();
        Mockito
            .when(repository.delete(personal))
            .thenReturn(voidReturn);

	    webclient.delete()
                .uri("/personal/{personalId}", personal.getIdPersonal())
                .exchange()
                .expectStatus().isOk();
    }
}