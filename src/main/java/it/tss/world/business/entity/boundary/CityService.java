/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.world.business.entity.boundary;

import it.tss.world.business.entity.City;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author tss
 */
@Path(value = "/cities")
@Stateless
public class CityService {

    @PersistenceContext
    private EntityManager em;

    public List<City> findAll() {

        return findAll(null);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<City> findAll(Comparator<City> comp) {

        List result = em.createNamedQuery("City.findAll")
                .getResultList();

        if (comp != null) {
            Collections.sort(result, comp);

        }
        return result;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public City find(@PathParam("id") Integer id) {

        return em.find(City.class, id);
    }

    @GET
    @Path("byName/{search}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<City> findByName(@PathParam("search") String search) {

        return em.createQuery("select e from City e where e.name like :search", City.class)
                .setParameter("search", "%" + search + "%")
                .getResultList();

    }

    
    @GET
    @Path("byState/{search}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<City> findByState(@PathParam("search") String search) {

        return em.createQuery("select e from City e where e.countryCode.name = :search", City.class)
                .setParameter("search",   search )
                .getResultList();

    }
    
}
