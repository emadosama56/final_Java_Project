/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restapi;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.expressions.Ascending;
import org.apache.spark.sql.functions;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.lower;
import static org.apache.spark.sql.functions.trim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import scala.Tuple2;

/**
 *
 * @author Emad Osama
 */
@RestController                                  //make the controller that will handle all requests
public class JopController {
    @Autowired
    private ApplicationContext applicationContext;
    private JobsDao jobsDao;
    
    @PostConstruct
    void init(){
        System.out.println("test");
        jobsDao = applicationContext.getBean(JobsDaoImplementation.class);        
    }
    @GetMapping(value = "/all")                  // mapping to url localhost:8080/all
    public List<Jobs_Pojo> getJops()   
    {  
        return jobsDao.getAllJobs().collectAsList();
    }  
    @GetMapping(value = "/country")  
    public List<Jobs_Pojo> getGiza()             // mapping to url localhost:8080/company
    {  
        return jobsDao.getAllJobs().filter("Country='Giza'").collectAsList();
    }  

    @GetMapping(value = "/structure")
    public Tuple2<String, String>[] getStructure()             // mapping to url localhost:8080/structure
    {  
        return jobsDao.getAllJobs().dtypes();
    } 
    
    @GetMapping(value = "/dropDuplicate")
    public List<Jobs_Pojo> dropDuplicate()             // mapping to url localhost:8080/dropDuplicate
    {  
        return jobsDao.getAllJobs().dropDuplicates().collectAsList();
    } 
    
    //***************************************************************************//
    //***************************************************************************//
    private Gson gson = new Gson();
    private List<JsonObject> rowToJson(Dataset<Row> s){
            return s.toJSON().collectAsList().stream().map(m->gson.fromJson(m,JsonObject.class)).collect(Collectors.toList());
    }
    
    @GetMapping(value = "/summary")
    @ResponseBody
    public ResponseEntity<List<JsonObject>> getSummary()             // mapping to url localhost:8080/summary
    {  
        Dataset<Row> s = jobsDao.getAllJobs().summary();
        return ResponseEntity.ok(rowToJson(s));
    }  
    
    @GetMapping(value = "/dropNull")
    @ResponseBody
    public ResponseEntity<List<JsonObject>> dropNull()             // mapping to url localhost:8080/dropNull
    {  
        //drop("all") , drop("any") , drop(Seq("population","type"))
        Dataset<Row> s =  jobsDao.getAllJobs().na().drop("any");
        return ResponseEntity.ok(rowToJson(s));
    } 
    
    
    @GetMapping(value = "/mostPopularJob")
    public String mostPopularJob()             // mapping to url localhost:8080/mostPopularJob
    {  
        return jobsDao.getAllJobs().groupBy("title").count().sort(col("count").desc()).alias("count").first().toString();
    } 
    
    @GetMapping(value = "/areas")
    @ResponseBody
    public ResponseEntity<List<JsonObject>> Areas()             // mapping to url localhost:8080/Areas
    {  
        Dataset<Row> s =  jobsDao.getAllJobs().groupBy("location").count().sort(col("count").desc()).alias("count");
        return ResponseEntity.ok(rowToJson(s));
    }
    
    
    @GetMapping(value = "/mostPopularArea")
    public String mostPopularArea()             // mapping to url localhost:8080/mostPopularArea
    {  
        return jobsDao.getAllJobs().groupBy("location").count().sort(col("count").desc()).alias("count").first().toString();
    }
    
    
    @GetMapping(value = "/skills")
    @ResponseBody
    public ResponseEntity<List<JsonObject>> skills()             // mapping to url localhost:8080/skills
    {  
        Dataset<Row> s =  jobsDao.getAllJobs().selectExpr("explode(split(Skills,',')) as skill").withColumn("skill", trim(lower(col("skill")))).groupBy("skill").count().alias("count").sort(col("count").desc());
        return ResponseEntity.ok(rowToJson(s));
    }
    
    
    @GetMapping(value = "/types")
    @ResponseBody
    public ResponseEntity<List<JsonObject>> types()             // mapping to url localhost:8080/types
    {  
        Dataset<Row> s =  jobsDao.getAllJobs().groupBy("type").count();
        return ResponseEntity.ok(rowToJson(s));
    }
    

    
    @GetMapping(value = "/jobsCount")
    @ResponseBody
    public ResponseEntity<List<JsonObject>> jobsCount()             // mapping to url localhost:8080/jobsCount
    { 
        Dataset<Row> s = jobsDao.getAllJobs().groupBy("Company").count().alias("count").sort(col("count").desc());
        return ResponseEntity.ok(rowToJson(s));
    } 
    
    
    @GetMapping(value = "/alljobs")
    @ResponseBody
    public ResponseEntity<List<JsonObject>> alljobs()             // mapping to url localhost:8080/alljobs
    {  
        Dataset<Row> s =  jobsDao.getAllJobs().groupBy("title").count().sort(col("count").desc()).alias("count");
        return ResponseEntity.ok(rowToJson(s));
        
    } 
    
    //*************************************************************************//
    //*************************************************************************//

    
    
    
//    @GetMapping(value = "/jobsCount")
//    public List<String> jobsCount()             // mapping to url localhost:8080/jobsCount
//    {  // 3dd kol sho8lana fy kol sherka
////        return jobsDao.getAllJobs().groupBy("Company","title").count().alias("count").sort(col("count").desc()).toJSON().collectAsList();
//        //3dd el jobs fy kol sherka
//        return jobsDao.getAllJobs().groupBy("Company").count().alias("count").sort(col("count").desc()).toJSON().collectAsList();
////        return jobsDao.getAllJobs().dropDuplicates().groupBy("Company").count().alias("count").sort(col("count").desc()).toJSON().collectAsList();
//    } 
    
    
//    @GetMapping(value = "/types")
//    public List<String> types()             // mapping to url localhost:8080/types
//    {  
//        return jobsDao.getAllJobs().groupBy("type").count().toJSON().collectAsList();
//    }
    
    
//    @GetMapping(value = "/skills")
//    public List<String> skills()             // mapping to url localhost:8080/skills
//    {  
//        return jobsDao.getAllJobs().selectExpr("explode(split(Skills,',')) as skill").withColumn("skill", trim(lower(col("skill")))).groupBy("skill").count().alias("count").sort(col("count").desc()).toJSON().collectAsList();
//    }
    
    
//    @GetMapping(value = "/all")                  // mapping to url localhost:8080/all
//    public List<Jobs_Pojo> getJops()   
//    {  
//        return jobsDao.getAllJobs().collectAsList();
//    }  
//    @GetMapping(value = "/country")  
//    public List<Jobs_Pojo> getGiza()             // mapping to url localhost:8080/company
//    {  
//        return jobsDao.getAllJobs().filter("Country='Giza'").collectAsList();
//    }  
//    
//    @GetMapping(value = "/summary")
//    public List<String> getSummary()             // mapping to url localhost:8080/summary
//    {  
//        return jobsDao.getAllJobs().summary().toJSON().collectAsList();
//    }  
//    
//    
//    @GetMapping(value = "/structure")
//    public Tuple2<String, String>[] getStructure()             // mapping to url localhost:8080/structure
//    {  
//        return jobsDao.getAllJobs().dtypes();
//    } 
//    
//    @GetMapping(value = "/dropDuplicate")
//    public List<Jobs_Pojo> dropDuplicate()             // mapping to url localhost:8080/dropDuplicate
//    {  
//        return jobsDao.getAllJobs().dropDuplicates().collectAsList();
//    } 
//    
//    @GetMapping(value = "/dropNull")
//    public List<String> dropNull()             // mapping to url localhost:8080/dropNull
//    {  
//        //drop("all") , drop("any") , drop(Seq("population","type"))
//        return jobsDao.getAllJobs().na().drop("any").toJSON().collectAsList();
//    } 
//    
//    
////    
//    
//    
//    @GetMapping(value = "/mostPopularJob")
//    public String mostPopularJob()             // mapping to url localhost:8080/mostPopularJob
//    {  
//        return jobsDao.getAllJobs().groupBy("title").count().sort(col("count").desc()).alias("count").first().toString();
//    } 
//    
//    
//    @GetMapping(value = "/mostPopularArea")
//    public String mostPopularArea()             // mapping to url localhost:8080/mostPopularArea
//    {  
//        return jobsDao.getAllJobs().groupBy("location").count().sort(col("count").desc()).alias("count").first().toString();
//    }
    
}

