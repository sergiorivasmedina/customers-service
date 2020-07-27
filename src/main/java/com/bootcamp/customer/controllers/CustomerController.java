package com.bootcamp.customer.controllers;

import com.bootcamp.customer.dto.BalanceSummaryDTO;
import com.bootcamp.customer.dto.CustomerAndTypeDTO;
import com.bootcamp.customer.dto.CustomerDTO;
import com.bootcamp.customer.model.Business;
import com.bootcamp.customer.model.Corporate;
import com.bootcamp.customer.model.Customer;
import com.bootcamp.customer.model.Personal;
import com.bootcamp.customer.model.Pyme;
import com.bootcamp.customer.model.Vip;
import com.bootcamp.customer.services.BusinessService;
import com.bootcamp.customer.services.CorporateService;
import com.bootcamp.customer.services.CustomerService;
import com.bootcamp.customer.services.PersonalService;
import com.bootcamp.customer.services.PymeService;
import com.bootcamp.customer.services.VipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PersonalService personalService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private VipService vipService;
    @Autowired
    private PymeService pymeService;
    @Autowired
    private CorporateService corporateService;

    @GetMapping(value = "/customers")
    public @ResponseBody Flux<Customer> getAllCustomers() {
        // list all data in customer collection
        return customerService.findAll();
    }

    @PostMapping(value = "/customer/new")
    public void newCustomer(@RequestBody CustomerDTO newCustomerDTO) {

        if (newCustomerDTO.getType() == 1) {
            // type=1 is Personal Customer
            Personal p = new Personal("Personal", newCustomerDTO.getIdentityNumber());
            personalService.save(p)
                    .flatMap(per -> {
                        // adding a new customer to the collection
                        Customer c = new Customer(newCustomerDTO.getName(), newCustomerDTO.getType(),per.getIdPersonal(), newCustomerDTO.getIdentityNumber(), newCustomerDTO.getBankId());
                        return customerService.save(c);
                    })
                    .subscribe();
        }
        else if(newCustomerDTO.getType() == 2) {
            //type=2 is Business Customer
            Business b = new Business();
            b.setDescription("Empresa");
            b.setRuc(newCustomerDTO.getIdentityNumber());
            businessService.save(b)
                    .flatMap(bus -> {
                        // adding a new customer to the collection
                        Customer c = new Customer(newCustomerDTO.getName(), newCustomerDTO.getType(),bus.getIdBusiness(), newCustomerDTO.getIdentityNumber(),newCustomerDTO.getBankId());
                        return customerService.save(c);
                    })
                    .subscribe();
        } else if (newCustomerDTO.getType() == 3) {
            //VIP
            Vip vip = new Vip("VIP", newCustomerDTO.getIdentityNumber());
            vipService.save(vip)
                .flatMap(vipCustomer -> {
                    Customer c = new Customer(newCustomerDTO.getName(), newCustomerDTO.getType(),vipCustomer.getIdVip(), newCustomerDTO.getIdentityNumber(), newCustomerDTO.getBankId());
                    return customerService.save(c);
                })
                .subscribe();
        } else if (newCustomerDTO.getType() == 4) {
            //PYME
            Pyme pyme = new Pyme("PYME", newCustomerDTO.getIdentityNumber());
            pymeService.save(pyme)
                .flatMap(pymeCustomer -> {
                    Customer c = new Customer(newCustomerDTO.getName(), newCustomerDTO.getType(),pymeCustomer.getIdPyme(), newCustomerDTO.getIdentityNumber(), newCustomerDTO.getBankId());
                    return customerService.save(c);
                })
                .subscribe();
        } else if (newCustomerDTO.getType() == 5) {
            //Corporate
            Corporate corporate = new Corporate("Corporación", newCustomerDTO.getIdentityNumber());
            corporateService.save(corporate)
                .flatMap(corp -> {
                    Customer c = new Customer(newCustomerDTO.getName(), newCustomerDTO.getType(),corp.getIdCorporate(), newCustomerDTO.getIdentityNumber(), newCustomerDTO.getBankId());
                    return customerService.save(c);
                })
                .subscribe();
        }
    }

    @PutMapping(value = "/customer/{customerId}")
    public Mono <ResponseEntity<Customer>> updateCustomer(@PathVariable(name = "customerId") String customerId, @RequestBody Customer customer) {
        return customerService.findById(customerId)
            .flatMap(existingCustomer -> {
                return customerService.save(customer);
            })
            .map(updateCustomer -> new ResponseEntity<>(updateCustomer, HttpStatus.OK))
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/customer/{customerId}")
    public Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable(name = "customerId") String customerId) {
        return customerService.findById(customerId)
            .flatMap(existingCustomer ->
                customerService.delete(existingCustomer)
                    .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))) 
            )
            .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //find customer by Id
    @GetMapping(value = "/customer/name/{customerId}")
    public Mono<Customer> getCustomerName(@PathVariable(name = "customerId") String customerId) {
        return customerService.findById(customerId)
                .defaultIfEmpty(new Customer("0","No se encontró cliente",0,"No se encontró identidad",null, null));
    }

    //Balance Summary
    @GetMapping(value = "/customer/{customerId}/balance/summary")
    public BalanceSummaryDTO balanaceSummary(@PathVariable(name = "customerId") String customerId) {
        return customerService.balanceSummary(customerId);
    }

    //get customer by identity number (dni / ruc)
    @GetMapping(value = "/customer/{customerIndentityNumber}")
    public Flux<CustomerAndTypeDTO> getCustomerByIdentityNumber(@PathVariable(name = "customerIndentityNumber") String id){
        return customerService.getCustomerByIdentityNumber(id);
    }
}