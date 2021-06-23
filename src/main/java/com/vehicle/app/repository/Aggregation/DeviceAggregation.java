package com.vehicle.app.repository.Aggregation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
@Slf4j
public class DeviceAggregation {

    @Autowired
    private MongoTemplate mongoTemplate;

    public int totalDevices(String level){
        Aggregation aggregation=newAggregation(
                match(Criteria.where("level").is(level+"/transporter1")),
                group("level").count().as("devices"),
                project("devices")
        );

        log.info("DeviceAggregation -: "+aggregation+"------------------------------------------");
        AggregationResults<AggregationOutput> results=mongoTemplate.aggregate(aggregation,"device",AggregationOutput.class);
        log.info("DeviceAggregation -: "+results+"------------------------------------------");
        int total=results.getUniqueMappedResult().devices;
        log.info("DeviceAggregation -: "+total+"------------------------------------------");
        return total;
    }
}
