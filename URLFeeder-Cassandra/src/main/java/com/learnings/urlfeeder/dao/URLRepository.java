package com.learnings.urlfeeder.dao;

import com.learnings.urlfeeder.entities.URL;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLRepository extends CassandraRepository<URL, String> {

    @AllowFiltering
    Optional<URL> findByUrl(String url);

    @AllowFiltering
    Optional<URL> findById(String id);
}