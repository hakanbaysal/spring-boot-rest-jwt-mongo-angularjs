package com.halitkorkmaz.api.services;

import java.util.List;

import com.halitkorkmaz.api.documents.ApplicationUser;

public interface UserService {
	
	List<ApplicationUser> list();
	
	ApplicationUser getById(String id);
	
	ApplicationUser create(ApplicationUser cliente);
	
	ApplicationUser update(ApplicationUser cliente);
	
	void delete(String id);
}
