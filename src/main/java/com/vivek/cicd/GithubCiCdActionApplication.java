package com.vivek.cicd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GithubCiCdActionApplication {
	
	@GetMapping("/welcome")
	public String hello()
	{
		return "Hello word";
	}
	
	
	public static void main(String[] args) { SpringApplication.run(GithubCiCdActionApplication.class, args);
	}

}


//echo "# Github-Action-CI-CD" >> README.md
//git init
//git add README.md
//git commit -m "first commit"
//git branch -M main
//git remote add origin https://github.com/vivek251288/Github-Action-CI-CD.git
//git push -u origin main