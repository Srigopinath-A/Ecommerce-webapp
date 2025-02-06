package com.newapp.Webapp.Repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newapp.Webapp.Entity.Category;


@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

}
