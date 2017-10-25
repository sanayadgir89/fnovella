package org.fnovella.project.user.controller;

import org.fnovella.project.user.model.Login;
import org.fnovella.project.user.model.LoginResponse;
import org.fnovella.project.user.repository.AppUserRepository;
import org.fnovella.project.user.repository.UserRepository;
import org.fnovella.project.utility.APIUtility;
import org.fnovella.project.utility.model.APIResponse;

import java.util.ArrayList;

import org.fnovella.project.user.model.AppUser;
import org.fnovella.project.user.model.AppUserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AppUserRepository appUserRepository;
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public LoginResponse login(@RequestBody Login user) {
		if (APIUtility.isNotNullOrEmpty(user.getEmail())) {
			if (APIUtility.isNotNullOrEmpty(user.getPassword())) {
				AppUser appUser = this.userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()); 
				if (appUser != null) {
					AppUserSession session = new AppUserSession(appUser.getId(), APIUtility.generateHash());
					this.appUserRepository.save(session);
					return new LoginResponse(appUser, session.getToken(), null);	
				}
			}
		}
		ArrayList<String> errors = new ArrayList<String>();
		errors.add("There is no user with that data.");
		return new LoginResponse(null, null, errors);
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public LoginResponse singnup(@RequestBody AppUser user, @RequestHeader("authorization") String authorization) {
		ArrayList<String> errors = user.validate();
		if (errors.size() == 0) {
			AppUser appUser = this.userRepository.findByEmail(user.getEmail());
			if (appUser == null) {
				appUser = this.userRepository.save(user);
				AppUserSession session = new AppUserSession(appUser.getId(), APIUtility.generateHash());
				this.appUserRepository.save(session);
				appUser.setPassword("");
				return new LoginResponse(appUser, session.getToken(), null);	
			}
			errors.add("Email is already in use");
		}
		return new LoginResponse(null, null, errors);
	}
	
	@RequestMapping(value = "users", method = RequestMethod.GET)
	public APIResponse getAll(@RequestHeader("authorization") String authorization, Pageable pageable) {
		return new APIResponse(this.userRepository.findAll(pageable), null);
	}
	
	@RequestMapping(value = "userDetails", method = RequestMethod.GET)
	public APIResponse userDetails(@RequestHeader("authorization") String authorization) {
		AppUser authorizedUser = APIUtility.authorizeAppUser(authorization, this.appUserRepository, this.userRepository);
		authorizedUser.setPassword("");
		return new APIResponse(authorizedUser, null);
	}
	
	@RequestMapping(value = "update/{id}", method = RequestMethod.PATCH)
	public APIResponse update(@RequestHeader("authorization") String authorization, @PathVariable("id") Integer id,
			@RequestBody AppUser user) {
		ArrayList<String> errors = new ArrayList<String>();
		AppUser authorizedUser = APIUtility.authorizeAppUser(authorization, this.appUserRepository, this.userRepository);
		if (authorizedUser != null) {
			AppUser toUpdate = this.userRepository.findOne(id);
			if (toUpdate != null) {
				toUpdate.setUpdateFields(user);
				toUpdate = this.userRepository.saveAndFlush(toUpdate);
				toUpdate.setPassword("");
				return new APIResponse(toUpdate, null);
			} else {
				errors.add("User doesn't exist");
			}
		} else {
			errors.add("Not authorizaded");
		}
		return new APIResponse(null, errors);
	}
	
	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
	public APIResponse delete(@RequestHeader("authorization") String authorization, @PathVariable("id") Integer id) {
		ArrayList<String> errors = new ArrayList<String>();
		AppUser authorizedUser = APIUtility.authorizeAppUser(authorization, this.appUserRepository, this.userRepository);
		if (authorizedUser != null) {
			AppUser appUser = this.userRepository.findOne(id);
			if (appUser != null) {
				this.appUserRepository.deleteByIdAppUser(appUser.getId());
				this.userRepository.delete(appUser);
				return new APIResponse(true, null);
			} else {
				errors.add("User doesn't exist");
			}
		}
		errors.add("Not authorizaded");
		return new APIResponse(null, errors);
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public APIResponse logout(@RequestHeader("authorization") String authorization) {
		ArrayList<String> errors = new ArrayList<String>();
		AppUser authorizedUser = APIUtility.authorizeAppUser(authorization, this.appUserRepository, this.userRepository);
		if (authorizedUser != null) {
			AppUserSession session = appUserRepository.findByToken(authorization);
			if (session != null) {
				this.appUserRepository.delete(session);
				return new APIResponse(true, null);
			}
		}
		errors.add("Not authorizaded");
		return new APIResponse(null, errors);
	}
	
}