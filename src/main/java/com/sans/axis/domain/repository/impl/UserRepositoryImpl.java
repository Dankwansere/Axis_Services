package com.sans.axis.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sans.axis.commons.AxisException;
import com.sans.axis.domain.GenericControlList;
import com.sans.axis.domain.User;
import com.sans.axis.domain.repository.IGenericControlListRepository;
import com.sans.axis.domain.repository.IUserCustomRepository;
import com.sans.axis.domain.repository.IUserRepository;

@Repository
public class UserRepositoryImpl implements IUserCustomRepository {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private IUserRepository userRepository; 
	
	
	@Autowired
	private IGenericControlListRepository genericControlListRepository;
	
	@Override
	public User getUser(String username, String password) {
		User user;
		try {
			Query query = em.createQuery("SELECT u from User u WHERE username = :username AND password = :password");
			query.setParameter("username", username);
			query.setParameter("password", password);
			
			System.out.println("Query: " + query.toString());
			try {
				user = (User) query.getSingleResult();
				return user;
			} 
			catch (Exception e) {
				
				System.out.println("Unable to find user: " + e.getMessage());
				return null;
			}
		} catch (Exception ex) {
			System.out.println("Generating Query error: " + ex.getMessage());
			return null;
		}
	}
	
	
	public ArrayList<GenericControlList> getUserProjectList() {
		
		
		ArrayList<GenericControlList> genControlList = null;
		
		try {
			Query query = em.createQuery("SELECT gp from GenericControlList gp WHERE genericType = :genericType");
			query.setParameter("genericType", "timesheet");
			genControlList = (ArrayList<GenericControlList>) query.getResultList();
		} catch (Exception ex) {
			System.out.println("Query execution error: " + ex.getMessage());
		}
		
		return genControlList;
	}
	
	
	@Override
	public boolean validateUserName(String username) {
		Query query = em.createQuery("SELECT username from User u WHERE username = :username");
		query.setParameter("username", username);
		
		try {
			List<User> result = query.getResultList();
			
			if(result.size() == 0) {
				return false;
			}
			else {
				return true;
			}
		}
		catch (Exception ex) {
			System.out.println("Something went wrong searching for user: " + ex.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean validateEmail(String email) {
		Query query = em.createQuery("SELECT email from User u WHERE email = :email");
		query.setParameter("email", email);
		
		try {
			List<User> result = query.getResultList();
			
			if(result.size() == 0) {
				return false;
			} else {
				return true;
			}
		} catch(Exception ex) {
			System.out.println("Something went wrong validating email in database: " + ex.getMessage());
			return false;
		}
	}

	@Override
	public User createUser(User userDTO) throws AxisException {
		try {
			return userRepository.save(userDTO);
		}
		catch(Exception ex) {
			throw new AxisException(ex.getMessage());
		}
	}



	@Override
	public User findByUsername(String username) {
		User user;
		try {
			Query query = em.createQuery("SELECT u from User u WHERE username = :username");
			query.setParameter("username", username);
			
			System.out.println("Query: " + query.toString());
			try {
				user = (User) query.getSingleResult();
				return user;
			} 
			catch (Exception e) {
				
				System.out.println("Unable to find user: " + e.getMessage());
				return null;
			}
		} catch (Exception ex) {
			System.out.println("Generating Query error: " + ex.getMessage());
			return null;
		}
	}

}
