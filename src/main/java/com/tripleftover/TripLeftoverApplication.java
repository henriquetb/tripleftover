package com.tripleftover;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tripleftover.repository.OfferRepository;
import com.tripleftover.repository.UserRepository;

@SpringBootApplication
public class TripLeftoverApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripLeftoverApplication.class, args);
    }
    
    /*@Bean
	public CommandLineRunner demo(OfferRepository repository, UserRepository ur) {
		return (args) -> {
			// save a couple of customers
			User u = new User("Henriquesss");
			ur.save(u);
			repository.save(new Offer("aaaaa", u));

		};
	}*/
    
}


/*@Component
class OfferCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		for (Offer o : this.offerRepository.findAll()) {
			System.out.println(o.toString());
		}
	}
	
	@Autowired OfferRepository offerRepository;
	
}*/


