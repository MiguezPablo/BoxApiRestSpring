package com.example.boxCustodia.repository;

import com.example.boxCustodia.entity.Archivo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {

    Archivo findByHashSha256(String hashSha256);

    Archivo findByHashSha512(String hashSha512);
}
