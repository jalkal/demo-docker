package com.example.demodocker;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoDockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDockerApplication.class, args);
	}

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return (arg)->{
            customerRepository.save(new Customer("ivan", "correa"));
            customerRepository.save(new Customer("yoanni", "navarro"));
            customerRepository.save(new Customer("fede", "apollonio"));
        };
    }

}

@Entity
@Data
class Customer{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String name;
    String lastname;
    public Customer(String name, String lastname){
        this.name = name;
        this.lastname = lastname;
    }
    public Customer(){

    }
}

interface CustomerRepository extends JpaRepository<Customer, Integer>{
    Customer findByName(String name);
}

@RestController
class CustomerController{

    private final CustomerRepository customerRepository;

    CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    List<Customer> customerList(){
        return customerRepository.findAll();
    }

    @GetMapping("/customer/{name}")
    Customer customer(@PathVariable String name){
        return customerRepository.findByName(name);
    }
}
