package com.learnings.htmlfileworker.service;

import com.learnings.htmlfileworker.dao.PageRepository;
import com.learnings.htmlfileworker.model.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class URLService {

    @Autowired
    private PageRepository pageRepository;

    public Optional<PageInfo> get(String id) {
        PageInfo pageInfo = pageRepository.findByUrlId(id);
        if (pageInfo == null)  {
            return Optional.empty();
        }
        return Optional.of(pageInfo);
    }

}