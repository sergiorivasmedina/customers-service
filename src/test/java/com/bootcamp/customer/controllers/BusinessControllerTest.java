package com.bootcamp.customer.controllers;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import com.bootcamp.customer.model.Business;
import com.bootcamp.customer.services.BusinessService;

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
@WebFluxTest(controllers = BusinessController.class)
public class BusinessControllerTest {
    @MockBean
    BusinessService service;

    @Autowired
    private WebTestClient webclient;
    
    @Test
    public void getAllBusinessCustmers() {
        Business business = new Business("1", "Business", "2035648751");

        List<Business> list = new ArrayList<Business>();
        list.add(business);
         
        Flux<Business> businessFlux = Flux.fromIterable(list);

        Mockito
            .when(service.findAll())
            .thenReturn(businessFlux);

        webclient.get()
            .uri("/business")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Business.class);

            Mockito.verify(service, times(1)).findAll();
    }

    @Test
    public void newBusiness() {
        Business business = new Business("1", "Business", "2035648751");

        Mockito
            .when(service.save(business))
            .thenReturn(Mono.just(business));
        
        webclient.post()
            .uri("/business/new")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(business))
            .exchange()
            .expectStatus().isOk()
            .expectBody(Business.class);
    }

    @Test
    public void deleteAccountType() {
        Business business = new Business("1", "Business", "2035648751");

        Mockito
            .when(service.findById("1"))
            .thenReturn(Mono.just(business));

        Mono<Void> voidReturn  = Mono.empty();
        Mockito
            .when(service.delete(business))
            .thenReturn(voidReturn);

	    webclient.delete()
                .uri("/business/{businessId}", business.getIdBusiness())
                .exchange()
                .expectStatus().isOk();
    }
}