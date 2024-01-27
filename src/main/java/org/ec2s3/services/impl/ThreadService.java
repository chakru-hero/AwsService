package org.ec2s3.services.impl;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import org.ec2s3.model.BucketModel;
import org.ec2s3.model.InstanceModel;
import org.ec2s3.repo.EC2ServicesRepository;
import org.ec2s3.repo.S3ServicesRepository;


public class ThreadService implements Runnable {
    private final String service;
    private final S3ServicesRepository s3ServicesRepository;
    private final EC2ServicesRepository ec2ServicesRepository;

    public ThreadService(String service, S3ServicesRepository s3ServicesRepository, EC2ServicesRepository ec2ServicesRepository) {
        this.service = service;
        this.s3ServicesRepository = s3ServicesRepository;
        this.ec2ServicesRepository = ec2ServicesRepository;
    }


    @Override
    public void run() {
        System.out.println("Starting Thread: " + Thread.currentThread().threadId());
        if (service.equals("EC2")) {
            InstanceModel instanceModel = new InstanceModel();
            AmazonEC2 amazonEC2 = AmazonEC2ClientBuilder.standard().build();
            DescribeInstancesResult instancesResult = amazonEC2.describeInstances();

            for (Reservation reservation : instancesResult.getReservations()) {
                for (Instance instance : reservation.getInstances()) {
                    try {
                        instanceModel.setInstanceId(instance.getInstanceId());
                        ec2ServicesRepository.save(instanceModel);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        instanceModel.setId(null);
                    }
                }
                System.out.println("Thread " + Thread.currentThread().threadId() + " : saved EC2 Instances");
            }
        }
        if (service.equals("S3")) {
            BucketModel bucketModel = new BucketModel();
            AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().build();
            for (Bucket bucket : amazonS3.listBuckets()) {
                try {
                    bucketModel.setBucketName(bucket.getName());
                    s3ServicesRepository.save(bucketModel);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    bucketModel.setId(null);
                }
            }
            System.out.println("Thread " + Thread.currentThread().threadId() + " : saved S3 Buckets");
        }
    }

}
