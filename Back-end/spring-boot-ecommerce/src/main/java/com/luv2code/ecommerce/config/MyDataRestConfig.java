package com.luv2code.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;

import com.luv2code.ecommerce.entity.Product;
import com.luv2code.ecommerce.entity.ProductCategory;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer{

	private EntityManager entitytiManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager theEntityManager) {
		
		entitytiManager = theEntityManager;
		
	}
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		// TODO Auto-generated method stub
		HttpMethod [] theUnsupportedActions = {HttpMethod.PUT,HttpMethod.POST,HttpMethod.DELETE};
		
		//desativar os metodos PUT,POST,DELETE da classe product
		
		config.getExposureConfiguration()
				.forDomainType(Product.class)
				.withItemExposure((metdata,httpMethods) -> httpMethods.disable(theUnsupportedActions))
				.withCollectionExposure((metdata,httpMethods) -> httpMethods.disable(theUnsupportedActions));
		
		//desativar os metodos PUT,POST,DELETE da classe ProductCategory
		
		config.getExposureConfiguration()
			.forDomainType(ProductCategory.class)
			.withItemExposure((metdata,httpMethods) -> httpMethods.disable(theUnsupportedActions))
			.withCollectionExposure((metdata,httpMethods) -> httpMethods.disable(theUnsupportedActions));
		
		// agora vamos expor o metodo 
		
		exposeIds(config);
		
		
	}

	private void exposeIds(RepositoryRestConfiguration config) {
		
		//espor a entida com seus ids
		
		//gerar uma lista com todas entidades 
		
		Set<EntityType<?>> entities = entitytiManager.getMetamodel().getEntities(); 
		
		// criar uma lista de matris desse tipo de entidade		
		List<Class> entityClasses = new ArrayList();
		
		// pegar o tipo de entidade
		
		for(EntityType tempEntityType : entities) {
			
			entityClasses.add(tempEntityType.getJavaType());
			
		}
		
		// expor o id da entidade em um array
		
		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
		
		
	}
	
	
	

}
