package org.ec2s3.controllers;

import org.ec2s3.enums.Status;
import org.ec2s3.services.AWSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class ServiceController {

    private final AWSService awsService;

    @Autowired
    public ServiceController(AWSService awsService) {
        this.awsService = awsService;
    }

    @PostMapping("/discover")
    public ResponseEntity<List<Long>> DiscoverServices(@RequestBody List<String> services) {
        List<Long> jobIDs = awsService.discover(services);
        return new ResponseEntity<>(jobIDs, HttpStatus.OK);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Status> GetJobResult(@PathVariable("id") String id) {
        Status s = awsService.getStatus(Integer.parseInt(id));
        if (s.equals(Status.NOT_FOUND)) {
            return new ResponseEntity<>(s, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(s, HttpStatus.OK);
        }
    }

    @GetMapping("/discoveryResult/{service}")
    public ResponseEntity<List<String>> GetDiscoveryResult(@PathVariable("service") String service) {
        List<String> services = awsService.getServices(service);
        if (!services.isEmpty() && Objects.equals(services.getFirst(), "INVALID SERVICE NAME")) {
            return new ResponseEntity<>(services, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @PostMapping("/discover/files/{bucket}")
    public ResponseEntity<String> GetS3BucketObjects(@PathVariable("bucket") String bucket) {
        try{
            awsService.getAllFilesFromBucket(bucket);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/discover/files/{bucket}/count")
    public ResponseEntity<Object> GetS3BucketObjectCount(@PathVariable("bucket") String BucketName) {
        int count = awsService.countFilesInBucket(BucketName);
        if(count<0){
            return new ResponseEntity<>("No Files Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> GetS3BucketObjectlike(@RequestParam String BucketName, @RequestParam String
        Pattern) {

        return new ResponseEntity<>(awsService.searchInBucket(BucketName, Pattern), HttpStatus.OK);
    }
}