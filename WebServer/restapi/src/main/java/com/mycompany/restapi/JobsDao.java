/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restapi;

import java.util.List;
import org.apache.spark.sql.Dataset;
import org.springframework.stereotype.Repository;



/**
 *
 * @author Emad Osama
 */
@Repository
public interface JobsDao {
    public Dataset<Jobs_Pojo> getAllJobs();
}
