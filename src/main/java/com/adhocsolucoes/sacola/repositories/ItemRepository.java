package com.adhocsolucoes.sacola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adhocsolucoes.sacola.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
