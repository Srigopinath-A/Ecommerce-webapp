package com.newapp.Webapp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newapp.Webapp.Entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{

}
