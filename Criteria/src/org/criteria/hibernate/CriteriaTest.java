package org.criteria.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.javabrains.criteria.dto.UserDetails;

public class CriteriaTest {
	
	public static void main(String[] args) {
		
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		SessionFactory sessionFactory4 = configuration.buildSessionFactory(serviceRegistry);
		//SessionFactory sessionFactory3 = configuration.buildSessionFactory();
		
		Session session = null;
		try{
			session = sessionFactory4.openSession();
			session.beginTransaction();
			for(int i=1;i<20;i++){
				UserDetails user = new UserDetails();
				user.setUserName("user"+i);
				session.save(user);
			}
			/*
			UserDetails user = (UserDetails)session.get(UserDetails.class, 1);
			System.out.println("UserName:"+user.getUserName());
			user.setUserName("Updated User");
			
			//session.update(user);
			session.delete(user);
			*/
			//javax.persistence.Query query = 
			//javax.persistence.Query q = em.createNamedQuery("HelpTopic.findById");
			//javax.persistence.Query q = em.createQuery("SELECT h FROM HelpTopic h WHERE h.id = :id");
			//javax.persistence.Query q = em.createNativeQuery("INSERT INTO user (login, password ,firstName,lastName,email)  VALUES(?,?,?,?,?)");
			
			Criteria criteria = session.createCriteria(UserDetails.class);
			/*
			criteria.add(Restrictions.like("userName","%user1%"))
					.add(Restrictions.between("userId",12,15));
			*/
			criteria.add(Restrictions.or(
					Restrictions.between("userId",2,5), 
					Restrictions.between("userId",12,15)
			));
			
	
			//criteria.add(Restrictions.gt("userId",5));
			//criteria.add(Restrictions.eq("userId",5));
	
			List<UserDetails> users = (List<UserDetails>) criteria.list();
			System.out.println("Size of List Result:"+users.size());
			for(UserDetails u : users){
				System.out.println("UserName:"+u.getUserName());
			}
			session.getTransaction().commit();		
		}
		catch(Exception e){
			session.getTransaction().rollback();
		}
		finally {
			session.close();
		}
		
	}
}

