package org.ec2s3.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.ec2s3.enums.Status;
import org.ec2s3.model.BucketModel;
import org.ec2s3.repo.EC2ServicesRepository;
import org.ec2s3.repo.S3ServicesRepository;
import org.ec2s3.services.AWSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AWSServiceImpl implements AWSService {

    @Autowired
    S3ServicesRepository s3ServicesRepository;
    @Autowired
    EC2ServicesRepository ec2ServicesRepository;

    @Value("${region.name}")
    private String REGION;

    @Override
    public List<Long> discover(List<String> services) {
        List<Long> jobIDs = new ArrayList<>();
        for (String service : services) {
            try {
                Thread thread = new Thread(new ThreadService(service, s3ServicesRepository, ec2ServicesRepository));
                thread.start();
                jobIDs.add(thread.threadId());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return jobIDs;
    }

    public Status getStatus(int id) {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread t : threadSet) {
            if (t.threadId() == id) {
                if (t.isAlive()) {
                    return Status.IN_PROGRESS;
                }
            }
        }
        return Status.SUCCESS;
    }

    @Override
    public List<String> getServices(String service) {
        if (service.equals("EC2")) {
            return ec2ServicesRepository.getAllInstanceIDs();
        }
        if (service.equals("S3")) {
            return s3ServicesRepository.getAllBuckets();
        } else return new ArrayList<>(List.of("INVALID SERVICE NAME"));
    }

    @Override
    public List<String> getAllFilesFromBucket(String bucket) {
        List<String> files = new ArrayList<>();
        try {
            AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().build();
            ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request();
            listObjectsV2Request.setBucketName(bucket);
            ListObjectsV2Result result = amazonS3.listObjectsV2(listObjectsV2Request);
            for (S3ObjectSummary s3ObjectSummary : result.getObjectSummaries()) {
                files.add(s3ObjectSummary.getKey());
            }
            BucketModel bucketModel = s3ServicesRepository.findByBucketName(bucket);
            if (Objects.nonNull(bucketModel)) {
                bucketModel.setFiles(files);
                s3ServicesRepository.save(bucketModel);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return files;
    }

    @Override
    public int countFilesInBucket(String bucket) {
        BucketModel bucketModel = s3ServicesRepository.findByBucketName(bucket);
        if (Objects.nonNull(bucketModel) && Objects.nonNull(bucketModel.getFiles())) {
            return bucketModel.getFiles().size();
        } else {
            return -1;
        }
    }

    @Override
    public List<String> searchInBucket(String bucket, String pattern) {
        BucketModel bucketModel = s3ServicesRepository.findByBucketName(bucket);
        if (Objects.nonNull(bucketModel) && Objects.nonNull(bucketModel.getFiles())) {
            return search(bucketModel.getFiles(), pattern);
        } else {
            return Collections.emptyList();
        }
    }


    public List<String> search(List<String> files, String pattern) {
        List<String> result = new ArrayList<>();
        for (String fileName : files) {
            if (fileName.contains(pattern)) {
                result.add(fileName);
            }
        }
        return result;
    }
}
