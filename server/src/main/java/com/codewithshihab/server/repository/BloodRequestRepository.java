package com.codewithshihab.server.repository;

import com.codewithshihab.server.models.BloodRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodRequestRepository extends MongoRepository<BloodRequest, String> {
}
