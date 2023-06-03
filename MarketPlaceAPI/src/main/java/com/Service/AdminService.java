package com.Service;

import java.util.List;

import com.Entity.Admin;
import com.Entity.AdminDto;
import com.Entity.CurrentUserSession;
import com.Exception.AdminException;
import com.Exception.LoginException;

public interface AdminService {
	public Admin signUpAdmin(Admin admin) throws AdminException;

	public Admin updateAdminDetails(Admin admin, String key) throws AdminException,LoginException;

	public Admin deleteAdminAccount(String key) throws AdminException,LoginException;

	public Admin findByAdminId(Integer adminId) throws AdminException;

	public Admin findByUserName(String adminUserName) throws AdminException;

	public List<Admin> findAllUsers() throws AdminException;
	
    public CurrentUserSession loginAdmin(AdminDto admin) throws LoginException;
	
	public String logoutAdmin(String key) throws LoginException;
}