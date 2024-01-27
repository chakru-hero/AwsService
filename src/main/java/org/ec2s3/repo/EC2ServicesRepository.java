package org.ec2s3.repo;

import org.ec2s3.model.InstanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EC2ServicesRepository extends JpaRepository<InstanceModel, Long> {
    String findByInstanceId(String instanceId);

    @Query("SELECT i.instanceId FROM InstanceModel i")
    List<String> getAllInstanceIDs();
}
