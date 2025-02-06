package com.newapp.Webapp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newapp.Webapp.Entity.Address;

@Repository
public interface Addressrepo extends JpaRepository<Address, Long> {

}
