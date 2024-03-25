package com.devsutest.demo.repository;

import com.devsutest.demo.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonaRepo extends JpaRepository<Persona, Long> {
}
