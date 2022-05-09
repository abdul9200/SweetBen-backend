package com.SweetBen.SweetBen;

import com.SweetBen.SweetBen.dao.CategoryRepository;
import com.SweetBen.SweetBen.dao.ProductRepository;
import com.SweetBen.SweetBen.entities.AppRole;
import com.SweetBen.SweetBen.entities.AppUser;
import com.SweetBen.SweetBen.entities.Category;
import com.SweetBen.SweetBen.entities.Product;
import com.SweetBen.SweetBen.web.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)

public class SweetBenApplication {



	public static void main(String[] args) {

		SpringApplication.run(SweetBenApplication.class, args);
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner start(AccountService accountService,ProductRepository productRepository,CategoryRepository categoryRepository,RepositoryRestConfiguration repositoryRestConfiguration){
		return args-> {
			repositoryRestConfiguration.exposeIdsFor(Product.class, Category.class);
			accountService.addRole(new AppRole(null, "USER"));
			accountService.addRole(new AppRole(null, "ADMIN"));
			accountService.addRole(new AppRole(null, "MODERATEUR"));
			accountService.addRole(new AppRole(null, "ROOT"));
			accountService.addUser(new AppUser(null, "user1", "1234", new ArrayList<>()));
			accountService.addUser(new AppUser(null, "user2", "1234", new ArrayList<>()));

			

			categoryRepository.save(new Category(null, "Prestige", null, null, null));
			categoryRepository.save(new Category(null, "Amende", null, null, null));
			categoryRepository.save(new Category(null, "Beldi", null, null, null));
			Random rnd = new Random();
			categoryRepository.findAll().forEach(c -> {
				for (int i = 0; i < 10; i++) {
					Product p = new Product();
					p.setName(RandomString.make(18));
					p.setCurrentPrice(100 + rnd.nextInt(10000));
					p.setAvailable(rnd.nextBoolean());
					p.setPromotion(rnd.nextBoolean());
					p.setSelected(rnd.nextBoolean());
					p.setCategory(c);
					p.setPhotoName("unknown.png");
					productRepository.save(p);
				}
			});
		};
	}


}
