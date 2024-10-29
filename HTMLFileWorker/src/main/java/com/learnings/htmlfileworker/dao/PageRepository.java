package com.learnings.htmlfileworker.dao;

import com.learnings.htmlfileworker.model.PageInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends MongoRepository<PageInfo, String> {

    @Query("{urlId : ?0}")
    PageInfo findByUrlId(String id);
}