package com.halitkorkmaz.api.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.halitkorkmaz.api.documents.ApplicationUser;
import com.halitkorkmaz.api.responses.Response;
import com.halitkorkmaz.api.services.UserService;
import com.halitkorkmaz.api.util.StringUtils;
import com.halitkorkmaz.api.util.validation.ValidationData;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public AuthController(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@RequestMapping(value = "/sign-up")
	public ResponseEntity<Response<ApplicationUser>> signUp(
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

	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
}
