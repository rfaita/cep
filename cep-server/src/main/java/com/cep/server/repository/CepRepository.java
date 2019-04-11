package com.cep.server.repository;

import com.cep.server.model.Cep;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CepRepository extends MongoRepository<Cep, String> {

    Cep findByCep(String cep);

    List<Cep> findAllByOrderByScoreDesc(TextCriteria criteria, Pageable page);


}
