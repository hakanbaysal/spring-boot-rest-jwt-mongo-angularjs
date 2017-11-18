package com.halitkorkmaz.api.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.halitkorkmaz.api.documents.ApplicationUser;
import com.halitkorkmaz.api.responses.Response;
import com.halitkorkmaz.api.services.UserService;
import com.halitkorkmaz.api.util.StringUtils;
import com.halitkorkmaz.api.util.validation.ValidationData;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@GetMapping
	public ResponseEntity<Response<List<ApplicationUser>>> list() {
		List<ApplicationUser> userList = this.userService.list();
		return ResponseEntity.ok(new Response<List<ApplicationUser>>(userList));
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Response<ApplicationUser>> getById(
			@PathVariable(name = "id") String id) {
		return ResponseEntity.ok(new Response<ApplicationUser>(this.userService
				.getById(id)));
	}

	@PostMapping
	public ResponseEntity<Response<ApplicationUser>> create(
			@Valid @RequestBody ApplicationUser user, BindingResult result) {

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		if (result.hasErrors()) {
			List<ValidationData> errors = new ArrayList<ValidationData>();
			result.getAllErrors().forEach(
					error -> errors.add(new ValidationData(error
							.getDefaultMessage(), error.getObjectName())));

			return ResponseEntity.badRequest().body(
					new Response<ApplicationUser>(errors));
		}

		try {
			return ResponseEntity.ok(new Response<ApplicationUser>(
					this.userService.create(user)));

		} catch (DuplicateKeyException ex) {
			List<ValidationData> errors = new ArrayList<ValidationData>();

			ValidationData valData = StringUtils.parseDuplicateErrorMessage(ex
					.getMessage());
			errors.add(valData);

			return ResponseEntity.badRequest().body(
					new Response<ApplicationUser>(errors));
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Response<ApplicationUser>> update(
			@PathVariable(name = "id") String id,
			@Valid @RequestBody ApplicationUser user, BindingResult result) {

		user.setId(id);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		if (result.hasErrors()) {
			List<ValidationData> errors = new ArrayList<ValidationData>();
			result.getAllErrors().forEach(
					error -> errors.add(new ValidationData(error
							.getDefaultMessage(), error.getObjectName())));
			return ResponseEntity.badRequest().body(
					new Response<ApplicationUser>(errors));
		}

		try {
			return ResponseEntity.ok(new Response<ApplicationUser>(
					this.userService.update(user)));

		} catch (DuplicateKeyException ex) {
			List<ValidationData> errors = new ArrayList<ValidationData>();

			ValidationData valData = StringUtils.parseDuplicateErrorMessage(ex
					.getMessage());
			errors.add(valData);

			return ResponseEntity.badRequest().body(
					new Response<ApplicationUser>(errors));
		}
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Response<Integer>> delete(
			@PathVariable(name = "id") String id) {
		this.userService.delete(id);
		return ResponseEntity.ok(new Response<Integer>(1));
	}
}
