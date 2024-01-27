package org.ec2s3.services;

import org.ec2s3.enums.Status;

import java.util.List;

public interface AWSService {
    List<Long> discover(List<String> services);

    Status getStatus(int id);

    List<String> getServices(String service);

    List<String> getAllFilesFromBucket(String bucket);

    int countFilesInBucket(String bucket);

    List<String> searchInBucket(String bucket, String pattern);

}
