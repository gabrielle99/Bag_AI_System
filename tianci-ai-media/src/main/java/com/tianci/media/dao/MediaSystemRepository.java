package com.tianci.media.dao;

import com.tianci.model.media.pojo.ImagePojo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MediaSystemRepository extends MongoRepository<ImagePojo, String> {
    Page<ImagePojo> findByUserIdEquals(String userId, Pageable pageable);
}
