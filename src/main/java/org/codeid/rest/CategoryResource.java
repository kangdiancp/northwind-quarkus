package org.codeid.rest;


import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.codeid.entities.Category;
import org.codeid.repositories.CategoryRepository;
import org.slf4j.LoggerFactory;

import java.util.List;

@ApplicationScoped
@Path("/category")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CategoryResource.class);

    @Inject
    CategoryRepository repository;

    @GET
    public List<Category> findAll(){
        return repository.listAll(Sort.by("categoryName"));
    }

    @GET
    @Path("{id}")
    public Response findCategoryById(@PathParam("id") Long categoryId){
        Category entity = repository.findById(categoryId);
        if (entity == null){
            throw new WebApplicationException("Category with id "+categoryId+" not found",404);
        }

        return Response.ok(entity).status(200).build();
    }

    @POST
    @Transactional
    public Response createCategory(Category category){
        if (category.categoryId != null){
            throw new WebApplicationException("CategoryId was invalid ",400);
        }
        repository.persist(category);
        return Response.ok(category).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Category update(Long id,Category category){
        if (category.categoryName == null){
            throw new WebApplicationException("Category name was not set on request",422);
        }
        Category entity = repository.findById(id);

        if (category == null){
            throw new WebApplicationException("category with id "+" does not exit",404);
        }

        //update categoryname
        entity.categoryName = category.categoryName;

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Long id){
        Category entity = repository.findById(id);
        if (entity == null){
            throw new WebApplicationException("category with id "+" does not exit",404);
        }
        repository.delete(entity);
        return Response.status(204).build();
    }
}
