package com.bootcamp.customer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.bootcamp.customer.dto.BalanceSummaryDTO;
import com.bootcamp.customer.dto.BankAccountDTO;
import com.bootcamp.customer.dto.BankDTO;
import com.bootcamp.customer.dto.CreditDTO;
import com.bootcamp.customer.dto.CustomerAndTypeDTO;
import com.bootcamp.customer.model.Customer;
import com.bootcamp.customer.model.Personal;
import com.bootcamp.customer.repositories.BusinessRepository;
import com.bootcamp.customer.repositories.CorporateRepository;
import com.bootcamp.customer.repositories.CustomerRepository;
import com.bootcamp.customer.repositories.PersonalRepository;
import com.bootcamp.customer.repositories.PymeRepository;
import com.bootcamp.customer.repositories.VipRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private final String customersUri = "http://localhost:8081";
    private final String accountsUri = "http://localhost:8082";
    private final String creditsUri = "http://localhost:8083";
    private final String banksUri = "http://localhost:8084";

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PersonalRepository personalRepository;
    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private VipRepository vipRepository;
    @Autowired
    private PymeRepository pymeRepository;
    @Autowired
    private CorporateRepository corporateRepository;
    
    @CircuitBreaker(name = "service1", fallbackMethod = "fallbackForFindAll")
    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    //fallback
    public Flux<Customer> fallbackForFindAll(Throwable t) {
        logger.error("Inside Circuit Breaker fallbackForFindAll, cause {}", t.toString());
        return Flux.empty();
    }

    public Mono<Customer> save(Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    public Mono<Customer> findById(String customerId) {
        return customerRepository.findById(customerId);
    }

    public Mono<Void> delete(Customer customer) {
        return customerRepository.delete(customer);
    }

    public BalanceSummaryDTO balanceSummary(String customerId){
        
        String jsonString;
        BalanceSummaryDTO balanceSummaryDTO = new BalanceSummaryDTO();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //search accounts for this customerId
        jsonString = restTemplate.getForObject(accountsUri + "/account/search/" + customerId, String.class);
        JSONArray jsonArray = new JSONArray(jsonString);

        List<BankAccountDTO> accountList = new ArrayList<BankAccountDTO>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            //search account type name
            String accountTypeId = jsonObject.getString("accountType");
            String accountTypeName = restTemplate.getForObject(accountsUri + "/account/type/search/"+ accountTypeId, String.class);

            //search currency name
            String currencyId = jsonObject.getString("currency");
            String currencyName = restTemplate.getForObject(accountsUri + "/currency/search/"+ currencyId, String.class);

            BankAccountDTO accountDTO = new BankAccountDTO(jsonObject.getString("idBankAccount"),
                                        jsonObject.getDouble("availableBalance"), accountTypeName, currencyName);
                                
            accountList.add(accountDTO);
        }

        balanceSummaryDTO.setAccounts(accountList);

        //Search credits for this idCustomer
        jsonString = restTemplate.getForObject(creditsUri + "/credit/search/"+ customerId, String.class);
        jsonArray = new JSONArray(jsonString);

        List<CreditDTO> creditList = new ArrayList<CreditDTO>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            //search credit tye name
            String creditTypeId = jsonObject.getString("creditType");
            String creditTypeName = restTemplate.getForObject(creditsUri + "/credit/type/"+ creditTypeId, String.class);

            //search currency name
            String currencyId = jsonObject.getString("idCurrency");
            String currencyName = restTemplate.getForObject(creditsUri + "/currency/search/"+ currencyId, String.class);

            CreditDTO creditDTO = new CreditDTO(jsonObject.getString("idCredit"), jsonObject.getDouble("availableAmount"),
                        creditTypeName, currencyName, jsonObject.getDouble("consumedAmount"), jsonObject.getDouble("limit"));

            creditList.add(creditDTO);
        }

        balanceSummaryDTO.setCredits(creditList);

        //BalanceSummaryDTO
        balanceSummaryDTO.setIdCustomer(customerId);

        String customerString = restTemplate.getForObject(customersUri + "/customer/name/"+ customerId, String.class);
        JSONObject customer = new JSONObject(customerString);

        balanceSummaryDTO.setCustomerName(customer.getString("name"));

        //return all info
        return balanceSummaryDTO;
    }

    public Flux<CustomerAndTypeDTO> getCustomerByIdentityNumber(String id) {
        //get data from customers service using customerId
        Flux<CustomerAndTypeDTO> customerAndType = customerRepository.findByIdentityNumber(id)
                .flatMap(item -> {
                    if (item.getType() == 1){
                        //Personal
                        return personalRepository.findById(item.getObjectId())
                                    .map(type -> {
                                        return new CustomerAndTypeDTO(item.getIdCustomer(), item.getName(), item.getBankId(), type.getDescription(), item.getIdentityNumber());
                                    });

                    } else if (item.getType() == 2) {
                        //Business
                        return businessRepository.findById(item.getObjectId())
                                    .map(type -> {
                                        return new CustomerAndTypeDTO(item.getIdCustomer(), item.getName(), item.getBankId(), type.getDescription(), item.getIdentityNumber());
                                    });
                    } else if (item.getType() == 3) {
                        //VIP
                        return vipRepository.findById(item.getObjectId())
                                    .map(type -> {
                                        return new CustomerAndTypeDTO(item.getIdCustomer(), item.getName(), item.getBankId(), type.getDescription(), item.getIdentityNumber());
                                    });
                    } else if (item.getType() == 4) {
                        //PYME
                        return pymeRepository.findById(item.getObjectId())
                                    .map(type -> {
                                        return new CustomerAndTypeDTO(item.getIdCustomer(), item.getName(), item.getBankId(), type.getDescription(), item.getIdentityNumber());
                                    });
                    } else {
                        //Corporate
                        return corporateRepository.findById(item.getObjectId())
                                    .map(type -> {
                                        return new CustomerAndTypeDTO(item.getIdCustomer(), item.getName(), item.getBankId(), type.getDescription(), item.getIdentityNumber());
                                    });
                    }

                });

        return customerAndType.flatMap(customer -> {
            return WebClient.create(banksUri + "/bank/" + customer.getBankId())
                    .get()
                    .retrieve()
                    .bodyToMono(BankDTO.class)
                    .map(bank -> new CustomerAndTypeDTO(customer, bank.getName()))
                    .defaultIfEmpty(customer);
        });
    }
}