package com.halitkorkmaz.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.halitkorkmaz.api.documents.ApplicationUser;
import com.halitkorkmaz.api.repositories.ApplicationUserRepository;
import com.halitkorkmaz.api.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private ApplicationUserRepository userRepository;

	@Override
	public List<ApplicationUser> list() {
		return this.userRepository.findAll();
	}

	@Override
	public ApplicationUser getById(String id) {
		return this.userRepository.findOne(id);
	}

	@Override
	public ApplicationUser create(ApplicationUser cliente) {
		return this.userRepository.save(cliente);
	}

	@Override
	public ApplicationUser update(ApplicationUser cliente) {
		return this.userRepository.save(cliente);
	}

	@Override
	public void delete(String id) {
		this.userRepository.delete(id);
	}

}
