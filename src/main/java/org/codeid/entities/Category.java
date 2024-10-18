package org.codeid.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;


@Entity
public class Category extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @Column(name="category_id")
    public Long categoryId;

    @Column(name="category_name",length = 55,unique = true)
    @NotBlank(message="Category name not be blank")
    public String categoryName;

    @Column(name="description",length = 150)
    public String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId) && Objects.equals(categoryName, category.categoryName) && Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName, description);
    }
}
