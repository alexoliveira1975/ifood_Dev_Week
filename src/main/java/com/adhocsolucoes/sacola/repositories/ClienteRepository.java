package com.adhocsolucoes.sacola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adhocsolucoes.sacola.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
