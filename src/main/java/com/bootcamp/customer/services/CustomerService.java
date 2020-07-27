package com.bootcamp.customer.services;

import java.util.ArrayList;
import java.util.List;

import com.bootcamp.customer.dto.AccountTypeCustomerDTO;
import com.bootcamp.customer.dto.BalanceSummaryDTO;
import com.bootcamp.customer.dto.BankAccountDTO;
import com.bootcamp.customer.dto.BankDTO;
import com.bootcamp.customer.dto.CreditDTO;
import com.bootcamp.customer.dto.CustomerAndTypeDTO;
import com.bootcamp.customer.model.Customer;
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

    Logger log = LoggerFactory.getLogger(CustomerService.class);

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

    // fallback
    public Flux<Customer> fallbackForFindAll(final Throwable t) {
        log.error("Inside Circuit Breaker fallbackForFindAll, cause {}", t.toString());
        return Flux.empty();
    }

    public Mono<Customer> save(final Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    public Mono<Customer> findById(final String customerId) {
        return customerRepository.findById(customerId);
    }

    public Mono<Void> delete(final Customer customer) {
        return customerRepository.delete(customer);
    }

    public BalanceSummaryDTO balanceSummary(final String customerId) {

        String jsonString;
        final BalanceSummaryDTO balanceSummaryDTO = new BalanceSummaryDTO();
        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // search accounts for this customerId
        jsonString = restTemplate.getForObject(accountsUri + "/account/search/" + customerId, String.class);
        JSONArray jsonArray = new JSONArray(jsonString);

        final List<BankAccountDTO> accountList = new ArrayList<BankAccountDTO>();
        for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject jsonObject = jsonArray.getJSONObject(i);
            // search account type name
            final String accountTypeId = jsonObject.getString("accountType");
            final String accountTypeName = restTemplate.getForObject(accountsUri + "/account/type/search/" + accountTypeId,
                    String.class);

            // search currency name
            final String currencyId = jsonObject.getString("currency");
            final String currencyName = restTemplate.getForObject(accountsUri + "/currency/search/" + currencyId,
                    String.class);

            final BankAccountDTO accountDTO = new BankAccountDTO(jsonObject.getString("idBankAccount"),
                    jsonObject.getDouble("availableBalance"), accountTypeName, currencyName);

            accountList.add(accountDTO);
        }

        balanceSummaryDTO.setAccounts(accountList);

        // Search credits for this idCustomer
        jsonString = restTemplate.getForObject(creditsUri + "/credit/search/" + customerId, String.class);
        jsonArray = new JSONArray(jsonString);

        final List<CreditDTO> creditList = new ArrayList<CreditDTO>();
        for (int i = 0; i < jsonArray.length(); i++) {
            final JSONObject jsonObject = jsonArray.getJSONObject(i);

            // search credit tye name
            final String creditTypeId = jsonObject.getString("creditType");
            final String creditTypeName = restTemplate.getForObject(creditsUri + "/credit/type/" + creditTypeId,
                    String.class);

            // search currency name
            final String currencyId = jsonObject.getString("idCurrency");
            final String currencyName = restTemplate.getForObject(creditsUri + "/currency/search/" + currencyId,
                    String.class);

            final CreditDTO creditDTO = new CreditDTO(jsonObject.getString("idCredit"),
                    jsonObject.getDouble("availableAmount"), creditTypeName, currencyName,
                    jsonObject.getDouble("consumedAmount"), jsonObject.getDouble("limit"));

            creditList.add(creditDTO);
        }

        balanceSummaryDTO.setCredits(creditList);

        // BalanceSummaryDTO
        balanceSummaryDTO.setIdCustomer(customerId);

        final String customerString = restTemplate.getForObject(customersUri + "/customer/name/" + customerId, String.class);
        final JSONObject customer = new JSONObject(customerString);

        balanceSummaryDTO.setCustomerName(customer.getString("name"));

        // return all info
        return balanceSummaryDTO;
    }

    public Flux<CustomerAndTypeDTO> getCustomerByIdentityNumber(final String id) {
        // get data from customers service using indentityNumber
        Flux<CustomerAndTypeDTO> customerAndType = customerRepository.findByIdentityNumber(id).flatMap(item -> {
            if (item.getType() == 1) {
                // Personal
                return personalRepository.findById(item.getObjectId()).map(type -> {
                    return new CustomerAndTypeDTO(item.getIdCustomer(), item.getName(), item.getBankId(),
                            type.getDescription(), item.getIdentityNumber());
                });

            } else if (item.getType() == 2) {
                // Business
                return businessRepository.findById(item.getObjectId()).map(type -> {
                    return new CustomerAndTypeDTO(item.getIdCustomer(), item.getName(), item.getBankId(),
                            type.getDescription(), item.getIdentityNumber());
                });
            } else if (item.getType() == 3) {
                // VIP
                return vipRepository.findById(item.getObjectId()).map(type -> {
                    return new CustomerAndTypeDTO(item.getIdCustomer(), item.getName(), item.getBankId(),
                            type.getDescription(), item.getIdentityNumber());
                });
            } else if (item.getType() == 4) {
                // PYME
                return pymeRepository.findById(item.getObjectId()).map(type -> {
                    return new CustomerAndTypeDTO(item.getIdCustomer(), item.getName(), item.getBankId(),
                            type.getDescription(), item.getIdentityNumber());
                });
            } else {
                // Corporate
                return corporateRepository.findById(item.getObjectId()).map(type -> {
                    return new CustomerAndTypeDTO(item.getIdCustomer(), item.getName(), item.getBankId(),
                            type.getDescription(), item.getIdentityNumber());
                });
            }

        });

        customerAndType = customerAndType.flatMap(customer -> {
            // get bank name
            return WebClient.create(banksUri + "/bank/" + customer.getBankId()).get().retrieve()
                    .bodyToMono(BankDTO.class).map(bank -> new CustomerAndTypeDTO(customer, bank.getName()))
                    .defaultIfEmpty(customer);
        });

        //get accounts
        customerAndType = customerAndType.flatMap(cat -> {
            return WebClient.create(accountsUri + "/account/search/" + cat.getCustomerId())
                .get()
                .retrieve()
                .bodyToFlux(AccountTypeCustomerDTO.class)
                .flatMap(account -> {
                    return WebClient.create(accountsUri + "/account/type/search/" + account.getAccountType())
                        .get()
                        .accept(MediaType.TEXT_PLAIN).retrieve().toEntity(String.class)
                        .map(item -> {
                            account.setName(item.getBody());
                            return account;
                        });
                })
                .collectList()
                .map(account -> {
                    cat.setAccounts(account);
                    return cat;
                });
        });

        return customerAndType;
    }

    //borrar luego
    public Flux<AccountTypeCustomerDTO> getAccountInfoByCustomerId(final String customerId) {
        return WebClient.create(accountsUri + "/account/search/" + customerId)
                .get()
                .retrieve()
                .bodyToFlux(AccountTypeCustomerDTO.class)
                .flatMap(atc -> {
                    System.out.println("---atc: " + atc.toString());
                    return WebClient.create(accountsUri + "/account/type/search/" + atc.getAccountType())
                            .get()
                            .accept(MediaType.TEXT_PLAIN)
                            .retrieve()
                            .toEntity(String.class)
                            .map(item -> {
                                System.out.println("---item: " + item.toString());
                                atc.setName(item.getBody());
                                return atc;
                            });
                });
    }
}