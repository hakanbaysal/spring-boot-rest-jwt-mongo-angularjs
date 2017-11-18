package com.halitkorkmaz.api.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.halitkorkmaz.api.documents.ApplicationUser;

public interface ApplicationUserRepository extends MongoRepository<ApplicationUser, String> {
	ApplicationUser findByUsername(String username);
}
