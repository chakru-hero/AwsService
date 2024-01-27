package org.ec2s3.repo;

import org.ec2s3.model.BucketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface S3ServicesRepository extends JpaRepository<BucketModel, Long> {
    BucketModel findByBucketName(String bucketName);

    @Query("SELECT b.bucketName FROM BucketModel b")
    List<String> getAllBuckets();
}
