package org.codeid.repositories;


import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.codeid.entities.Category;

@ApplicationScoped
public class CategoryRepository implements PanacheRepositoryBase<Category,Long> {
}
