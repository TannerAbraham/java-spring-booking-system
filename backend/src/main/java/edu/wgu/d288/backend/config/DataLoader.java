package edu.wgu.d288.backend.config;

import edu.wgu.d288.backend.dao.CustomerRepository;
import edu.wgu.d288.backend.dao.DivisionRepository;
import edu.wgu.d288.backend.entities.Customer;
import edu.wgu.d288.backend.entities.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;
    
    @Autowired
    public DataLoader(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Only add customers if database has 1 or fewer customers (the default one)
        if (customerRepository.count() <= 1) {
            
            // Get divisions for customers (using existing divisions from the database)
            Division newYork = divisionRepository.findById(31L).orElse(null); // New York
            Division california = divisionRepository.findById(4L).orElse(null); // California
            Division florida = divisionRepository.findById(9L).orElse(null); // Florida
            Division texas = divisionRepository.findById(42L).orElse(null); // Texas
            Division illinois = divisionRepository.findById(12L).orElse(null); // Illinois
            
            // Create 5 sample customers
            Customer customer1 = new Customer();
            customer1.setFirstName("Alice");
            customer1.setLastName("Johnson");
            customer1.setAddress("123 Main Street");
            customer1.setPostal_code("10001");
            customer1.setPhone("(212)555-1234");
            customer1.setDivision(newYork);
            
            Customer customer2 = new Customer();
            customer2.setFirstName("Bob");
            customer2.setLastName("Smith");
            customer2.setAddress("456 Ocean Avenue");
            customer2.setPostal_code("90210");
            customer2.setPhone("(310)555-5678");
            customer2.setDivision(california);
            
            Customer customer3 = new Customer();
            customer3.setFirstName("Carol");
            customer3.setLastName("Williams");
            customer3.setAddress("789 Beach Boulevard");
            customer3.setPostal_code("33101");
            customer3.setPhone("(305)555-9012");
            customer3.setDivision(florida);
            
            Customer customer4 = new Customer();
            customer4.setFirstName("David");
            customer4.setLastName("Brown");
            customer4.setAddress("321 Ranch Road");
            customer4.setPostal_code("75201");
            customer4.setPhone("(214)555-3456");
            customer4.setDivision(texas);
            
            Customer customer5 = new Customer();
            customer5.setFirstName("Emma");
            customer5.setLastName("Davis");
            customer5.setAddress("654 Lake Shore Drive");
            customer5.setPostal_code("60601");
            customer5.setPhone("(312)555-7890");
            customer5.setDivision(illinois);
            
            // Save all customers
            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
            customerRepository.save(customer4);
            customerRepository.save(customer5);
            
            System.out.println("5 sample customers have been added to the database.");
        } else {
            System.out.println("Sample customers already exist. Skipping data load.");
        }
    }
}
