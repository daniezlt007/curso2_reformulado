package dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Dados;

public class DadosDao {
	public EntityManager getEM(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("javawebPU");
		return emf.createEntityManager();
	}
	
	public Dados salvar(Dados t)throws Exception{
		EntityManager em = getEM();
		try {
			em.getTransaction().begin();
			if(t.getId() == null){	
				em.persist(t);
			}else{
				if(!em.contains(t)){
					if(em.find(t.getClass(), t.getId()) == null){
						throw new Exception("Erro ao atualizar Objeto!");
					}
				}
				t = em.merge(t);
			}
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		return t;
	}
	
	public void remover(Class<Dados> classe, Serializable id){
		EntityManager em = getEM();
		Dados t = em.find(classe, id);
		try {
			em.getTransaction().begin();
			em.remove(t);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
		
	public Dados consultarPorId(Class<Dados> classe, Serializable id){
		EntityManager em = getEM();
		Dados t = null;
		try {
			t = em.find(classe, id);
		} finally {
			em.close();
		}
		return t;
	}
}
