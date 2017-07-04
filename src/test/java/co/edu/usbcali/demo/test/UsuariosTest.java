package co.edu.usbcali.demo.test;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

public class UsuariosTest {
	
	private final static Logger log=LoggerFactory.getLogger(UsuariosTest.class);
	
	EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;
	
	private static Long usuCedula=30L;
	
	@Test
	public void aTest() {
		entityManagerFactory=Persistence.createEntityManagerFactory("banco-persistencia");
		entityManager=entityManagerFactory.createEntityManager();
		
			Usuarios usuarios=entityManager.find(Usuarios.class, usuCedula);
			assertNull("El usuario ya existe", usuarios);
			
			usuarios=new Usuarios();
			usuarios.setUsuNombre("Diana Bermudez");
			usuarios.setUsuCedula(usuCedula);
			usuarios.setUsuLogin("dbermudez");
			usuarios.setUsuClave("1234");
			
			TiposUsuarios tiposUsuarios = entityManager.find(TiposUsuarios.class, 10L);
			assertNotNull("El tipo de usuario no existe", tiposUsuarios);
			
			usuarios.setTiposUsuarios(tiposUsuarios);
			
			entityManager.getTransaction().begin();
			entityManager.persist(usuarios);
			entityManager.getTransaction().commit();
		
		entityManager.close();
		entityManagerFactory.close();
	}

	@Test
	public void bTest() {
		entityManagerFactory=Persistence.createEntityManagerFactory("banco-persistencia");
		entityManager=entityManagerFactory.createEntityManager();
			
			Usuarios usuarios=entityManager.find(Usuarios.class, usuCedula);
			assertNotNull("El usuario no existe",usuarios);
			
			log.info(""+usuarios.getUsuCedula());
			log.info(usuarios.getUsuNombre());
			
		
		entityManager.close();
		entityManagerFactory.close();
	}
	
	@Test
	public void cTest() {
		entityManagerFactory=Persistence.createEntityManagerFactory("banco-persistencia");
		entityManager=entityManagerFactory.createEntityManager();
		
			Usuarios usuarios=entityManager.find(Usuarios.class, usuCedula);
			assertNotNull("El usuario no existe", usuarios);
			
			usuarios.setUsuClave("123456");
			usuarios.setUsuNombre("Diana Carolina Bermudez");
			usuarios.setUsuLogin("dcbermudez");
			
			TiposUsuarios tiposUsuarios =entityManager.find(TiposUsuarios.class, 20L);
			assertNotNull("El tipo de usuario no existe", tiposUsuarios);
			
			usuarios.setTiposUsuarios(tiposUsuarios);
			
			entityManager.getTransaction().begin();
			entityManager.merge(usuarios);
			entityManager.getTransaction().commit();
		
		entityManager.close();
		entityManagerFactory.close();
	}
	
	@Test
	public void dTest() {
		entityManagerFactory=Persistence.createEntityManagerFactory("banco-persistencia");
		entityManager=entityManagerFactory.createEntityManager();
		
			Usuarios usuarios=entityManager.find(Usuarios.class, usuCedula);
			assertNotNull("El usuario no existe", usuarios);
			
			
			entityManager.getTransaction().begin();
			entityManager.remove(usuarios);
			entityManager.getTransaction().commit();
		
		entityManager.close();
		entityManagerFactory.close();
	}
	
	@Test
	public void eTest() {
		entityManagerFactory=Persistence.createEntityManagerFactory("banco-persistencia");
		entityManager=entityManagerFactory.createEntityManager();
		
		String jpql="SELECT usu FROM Usuarios usu";
		
		List<Usuarios> losUsuarios=entityManager.createQuery(jpql).getResultList();
		
		for (Usuarios usuarios : losUsuarios) {
			log.info("Cedula:"+usuarios.getUsuCedula());
			log.info("Nombre:"+usuarios.getUsuNombre());
			log.info("Login:"+usuarios.getUsuLogin());
			log.info("Tipo usuario:"+usuarios.getTiposUsuarios().getTusuNombre());
		}
		
		entityManager.close();
		entityManagerFactory.close();
	}
	
	
}








