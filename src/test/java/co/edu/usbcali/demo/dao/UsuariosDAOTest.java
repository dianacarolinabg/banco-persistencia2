package co.edu.usbcali.demo.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.Persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Rollback(false)

public class UsuariosDAOTest {
	
	private final static Logger log=LoggerFactory.getLogger(UsuariosDAOTest.class);
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private ITiposUsuariosDAO tiposUsuariosDAO;

	private long usuCedula=1113629909L;
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void atest() {
	
		assertNotNull(usuariosDAO);
		assertNotNull(tiposUsuariosDAO);
		
		Usuarios usuarios = usuariosDAO.consultarPorId(usuCedula);
		assertNull("El usuario ya existe.", usuarios);
		
		usuarios=new Usuarios();
		usuarios.setUsuNombre("Diana Bermudez");
		usuarios.setUsuCedula(usuCedula);
		usuarios.setUsuLogin("dbermudez");
		usuarios.setUsuClave("1234");
		
		TiposUsuarios tiposUsuarios = tiposUsuariosDAO.consultarPorId(10L);
		assertNotNull("El tipo de usuario no existe.", tiposUsuarios);
		
		usuarios.setTiposUsuarios(tiposUsuarios);
		
		usuariosDAO.crear(usuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		assertNotNull(usuariosDAO);
		assertNotNull(tiposUsuariosDAO);
		
		Usuarios usuarios=usuariosDAO.consultarPorId(usuCedula);
		assertNotNull("El usuario no existe", usuarios);
		
		log.info("Nombre:"+usuarios.getUsuNombre());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void ctest() {
		assertNotNull(usuariosDAO);
		assertNotNull(tiposUsuariosDAO);
		
		Usuarios usuarios=usuariosDAO.consultarPorId(usuCedula);
		assertNotNull("El usuario no existe", usuarios);
		
		
		usuarios.setUsuClave("123456");
		usuarios.setUsuNombre("Diana Carolina Bermudez");
		usuarios.setUsuLogin("dcbermudez");
		
		TiposUsuarios tiposUsuarios =tiposUsuariosDAO.consultarPorId(20L);
		assertNotNull("El tipo de usuario no existe", tiposUsuarios);
		
		usuarios.setTiposUsuarios(tiposUsuarios);
		
		usuariosDAO.modificar(usuarios);
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public void dtest() {
		assertNotNull(usuariosDAO);
		assertNotNull(tiposUsuariosDAO);
		
		Usuarios usuarios=usuariosDAO.consultarPorId(usuCedula);
		assertNotNull("El usuario no existe", usuarios);
		
		usuariosDAO.borrar(usuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		assertNotNull(usuariosDAO);
		assertNotNull(tiposUsuariosDAO);
		
		List<Usuarios> losUsuarios=usuariosDAO.consultar();
		
		for (Usuarios usuarios : losUsuarios) {
			log.info(usuarios.getUsuNombre());
			log.info(usuarios.getUsuLogin());
		}
		
		
	}
}
