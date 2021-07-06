/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restapi;

import javax.annotation.PostConstruct;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Emad Osama
 */
@Repository
public class JobsDaoImplementation implements JobsDao{
    
    Dataset<Jobs_Pojo> jobsList;
    @Autowired
    SparkSession sparkSession;
    @PostConstruct
    public void init()
    {
        jobsList = sparkSession.read().option("header", "true").csv("Wuzzuf_Jobs.csv").as(Encoders.bean(Jobs_Pojo.class));
    }

    @Override
    public Dataset<Jobs_Pojo> getAllJobs() {
        return jobsList;
    }
}
