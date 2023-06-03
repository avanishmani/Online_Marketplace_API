package com.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.random.RandomGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entity.Admin;
import com.Entity.AdminDto;
import com.Entity.CurrentUserSession;
import com.Exception.AdminException;
import com.Exception.LoginException;
import com.Repository.AdminRepo;
import com.Repository.CurrentUserSessionRepo;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminRepo adminrepo;

	@Autowired
	private CurrentUserSessionRepo currentUserSessionRepo;

	@Override
	public Admin signUpAdmin(Admin admin) throws AdminException {
		Admin existingAdmin = adminrepo.findByAdminUsername(admin.getAdminUsername());

		if (existingAdmin != null) {
			throw new AdminException("Admin already exist with this username => " + admin.getAdminUsername());
		} else {
			return adminrepo.save(admin);
		}

	}

	@Override
	public Admin updateAdminDetails(Admin admin, String key) throws AdminException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("To update admin details please login first ");
		}

		if (admin.getAdminId() == loggedInUser.getUserId() && loggedInUser.getAdmin()) {
			return adminrepo.save(admin);
		} else {
			throw new AdminException(
					"Provided admin details are incorrect please check admin ID and other details and try again ");
		}
	}

	@Override
	public Admin deleteAdminAccount(String key) throws AdminException, LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("To delete admin account login first ");
		} else if (loggedInUser.getAdmin()) {
			Optional<Admin> opt = adminrepo.findById(loggedInUser.getUserId());
			Admin existingAdmin = opt.get();
			adminrepo.delete(existingAdmin);
			return existingAdmin;
		} else {
			throw new AdminException("Please login first as admin to delete admin account ");
		}
	}

	@Override
	public Admin findByAdminId(Integer adminId) throws AdminException {
		Optional<Admin> opt = adminrepo.findById(adminId);

		if (opt.isEmpty()) {
			throw new AdminException("No admin found with this admin ID => " + adminId);
		} else {
			return opt.get();
		}
	}

	@Override
	public Admin findByUserName(String adminUserName) throws AdminException {
		Admin existingAdmin = adminrepo.findByAdminUsername(adminUserName);

		if (existingAdmin == null) {
			throw new AdminException("No admin found with this admin user name => " + adminUserName);
		} else {
			return existingAdmin;
		}
	}

	@Override
	public List<Admin> findAllUsers() throws AdminException {
		List<Admin> adminlist = adminrepo.findAll();
		if (adminlist.isEmpty()) {
			throw new AdminException("No admin found");
		} else {
			return adminlist;
		}
	}

	@Override
	public CurrentUserSession loginAdmin(AdminDto admin) throws LoginException {
		Admin existingUser = adminrepo.findByAdminUsername(admin.getAdminUsername());

		if (existingUser == null) {
			throw new LoginException("No admin found with this username => " + admin.getAdminUsername());
		}

		Optional<CurrentUserSession> validAdminSessionOpt = currentUserSessionRepo.findById(existingUser.getAdminId());

		if (validAdminSessionOpt.isPresent() && existingUser.getAdminPassword().equals(admin.getAdminPassword())) {
			currentUserSessionRepo.delete(validAdminSessionOpt.get());
		}

		if (existingUser.getAdminPassword().equals(admin.getAdminPassword())) {

			int targetStringLength = 6;
			RandomGenerator generator = RandomGenerator.getDefault();
			String key = generator.ints('0', 'z' + 1).filter(i -> (i <= '9' || i >= 'A') && (i <= 'Z' || i >= 'a'))
					.limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
			Boolean isAdmin = true;

			CurrentUserSession currentUserSession = new CurrentUserSession(existingUser.getAdminId(), key, isAdmin,
					LocalDateTime.now());

			currentUserSessionRepo.save(currentUserSession);

			return currentUserSession;
		} else {
			throw new LoginException("Password entered is incorrect ");
		}
	}

	@Override
	public String logoutAdmin(String key) throws LoginException {
		CurrentUserSession loggedInUser = currentUserSessionRepo.findByUniqueID(key);

		if (loggedInUser == null) {
			throw new LoginException("Invalid current user session key ");
		}
		currentUserSessionRepo.delete(loggedInUser);
		return "Logged Out !";
	}

}
