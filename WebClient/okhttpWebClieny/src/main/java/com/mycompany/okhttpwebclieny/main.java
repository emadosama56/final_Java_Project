/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.okhttpwebclieny;

import charttitanicapp.XChartExamples;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.*;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.stage.Stage;
import javax.swing.JFrame;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

/**
 *
 * @author Emad Osama
 */
public class main {
    public static OkHttpClient client = new OkHttpClient();
    public static Gson gson = new Gson();
    
    
    
    public static void main(String[] args) throws IOException  {
        
//        Bargraph("Company", "count", "http://localhost:8080/jobsCount");
        PieChart("CompanyName:",10,true,"http://localhost:8080//jobsCount");
        Bargraph("title", "count", "http://localhost:8080/alljobs");
        Bargraph("location", "count", "http://localhost:8080/areas");
        
//        Type listType = new TypeToken<ArrayList<JsonObject>>(){}.getType();
//        Request request = new Request.Builder()
//            .url("http://localhost:8080/jobsCount")
//            .build();
//
//        try{
//           Response response = client.newCall(request).execute();
//           String res = response.body().string();
////           System.out.println(response.body().string());
////
//           List<JsonObject> Jobs = gson.fromJson(res, listType);
//            graphCompaniesCount(Jobs);
////           Jobs.stream().forEach(f->System.out.println(f.toString()));
////            getField(Jobs);
//            
////           Jobs.stream().forEach(f -> System.out.println(f.get)));
//        } catch (IOException ex) {
//            System.out.println("Ddd");
//            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//        XChartExamples xChartExamples = new XChartExamples ();
        
        
    }
    
    
//    public static void graphCompaniesCount(List<JsonObject> Jobs) {
//        List<String> companyList = Jobs.stream().map(m -> m.get("Company").getAsString()).collect(Collectors.toList());
//        List<Long> countList = Jobs.stream().map(m -> m.get("count").getAsLong()).collect(Collectors.toList());
////        System.out.println(countList);
//        CategoryChart chart = new CategoryChartBuilder ().width (1024).height (768).title ("Histogram").xAxisTitle ("Company").yAxisTitle ("Count").build ();
//        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
//        chart.getStyler ().setHasAnnotations (true);
//        chart.getStyler ().setStacked (true);
//        chart.addSeries ("Companies Count", companyList, countList);
//        new SwingWrapper (chart).displayChart ();
//    }
//    
//    public static void graphJobsCount(List<JsonObject> Jobs) {
//        List<String> companyList = Jobs.stream().map(m -> m.get("Company").getAsString()).collect(Collectors.toList());
//        List<Long> countList = Jobs.stream().map(m -> m.get("count").getAsLong()).collect(Collectors.toList());
////        System.out.println(countList);
//        CategoryChart chart = new CategoryChartBuilder ().width (1024).height (768).title ("Histogram").xAxisTitle ("Company").yAxisTitle ("Count").build ();
//        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
//        chart.getStyler ().setHasAnnotations (true);
//        chart.getStyler ().setStacked (true);
//        chart.addSeries ("Companies Count", companyList, countList);
//        new SwingWrapper (chart).displayChart ();
//    }
    
    
//    public static String getField(JsonObject json, String errorsIndex, String fieldIndex) {
//    JsonObject errorJson = json.getAsJsonArray("errors").getAsJsonObject();
//    String value = errorJson.getAsJsonArray("fields").getAsString();
//    System.out.println(value);
//    System.out.println(errorJson);
//    return value;
//}
    
//    public static void getField(List<JsonObject> jsonlist) {
//    for(int i=0; i<jsonlist.size(); i++)
//    {
//        System.out.println(jsonlist.get(i));
//    }
    
    
    public static void Bargraph(String XaxisName , String YaxisName , String url) throws IOException
    {
        Type listType = new TypeToken<ArrayList<JsonObject>>(){}.getType();
        Request request = new Request.Builder()
            .url(url)
            .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string();
        List<JsonObject> Jobs = gson.fromJson(res, listType);
        XChartExamples xChartExamples = new XChartExamples ();
        List<String> companyList = Jobs.stream().map(m -> m.get(XaxisName).getAsString()).limit(8).collect(Collectors.toList());
        List<Long> countList = Jobs.stream().map(m -> m.get(YaxisName).getAsLong()).limit(8).collect(Collectors.toList());
        CategoryChart chart = new CategoryChartBuilder ().width (1024).height (768).title ("Histogram").xAxisTitle (XaxisName).yAxisTitle (YaxisName).build ();
        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart.getStyler ().setHasAnnotations (true);
        chart.getStyler ().setStacked (true);
        chart.addSeries (XaxisName+" "+YaxisName, companyList, countList);
        new SwingWrapper (chart).displayChart ();
    }
    
    
    
//    public static void Piegraph(String XaxisName , String YaxisName , String url) throws IOException
//    {
//        Type listType = new TypeToken<ArrayList<JsonObject>>(){}.getType();
//        Request request = new Request.Builder()
//            .url(url)
//            .build();
//        Response response = client.newCall(request).execute();
//        String res = response.body().string();
//        List<JsonObject> Jobs = gson.fromJson(res, listType);
////        XChartExamples xChartExamples = new XChartExamples ();
//        List<String> companyList = Jobs.stream().map(m -> m.get(XaxisName).getAsString()).limit(8).collect(Collectors.toList());
//        List<Long> countList = Jobs.stream().map(m -> m.get(YaxisName).getAsLong()).limit(8).collect(Collectors.toList());
////        CategoryChart chart = new CategoryChartBuilder ().width (1024).height (768).title ("Histogram").xAxisTitle (XaxisName).yAxisTitle (YaxisName).build ();
////        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
////        chart.getStyler ().setHasAnnotations (true);
////        chart.getStyler ().setStacked (true);
////        chart.addSeries (XaxisName+" "+YaxisName, companyList, countList);
////        new SwingWrapper (chart).displayChart ();
//        
//        
//        
//
//         Map<String, Long> result =
//                Jobs.stream ().collect (
//                        Collectors.groupingBy (
//                                Jobs::getPclass, Collectors.counting ()
//                        )
//                );
//        PieChart chart = new PieChartBuilder ().width (800).height (600).title (getClass ().getSimpleName ()).build ();
//        Color[] sliceColors = new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
//        chart.getStyler ().setSeriesColors (sliceColors);
//        chart.addSeries ("First Class", result.get ("1"));
//        chart.addSeries ("Second Class", result.get ("2"));
//        chart.addSeries ("Third Class", result.get ("3"));
//        new SwingWrapper (chart).displayChart ();
//    }
    
    
    public static void PieChart(String title, int Slice_No, boolean Others, String JsonPath)
            throws MalformedURLException, IOException {
        // Connect to the URL using java's native library
        URL url = new URL(JsonPath);
        URLConnection request = url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); // from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); // Convert the input
                                                                                                // stream to a json
                                                                                                // element
        JsonArray rootobj = root.getAsJsonArray();

        List<String> Elements = rootobj.get(0).getAsJsonObject().keySet().stream().collect(Collectors.toList());

        List<String> ElementsNames = new ArrayList<>();
        List<Integer> Count = new ArrayList<>();

        for (int i = 0; i < Slice_No; i++) {
            ElementsNames.add(rootobj.get(i).getAsJsonObject().get(Elements.get(0)).getAsString());
            Count.add(rootobj.get(i).getAsJsonObject().get(Elements.get(1)).getAsInt());
        }

        PieChart pieChart = new PieChartBuilder().width(1024 * 2).height(768 * 2).title(title).build();
        // adding sets to chart
        for (int i = 0; i < Slice_No; i++) {
            pieChart.addSeries(ElementsNames.get(i), Count.get(i));
        }

        // Adding Others
        if (Others) {

            int Others_Count = 0;
            for (int i = Slice_No; i < rootobj.size(); i++) {
                Others_Count += rootobj.get(i).getAsJsonObject().get(Elements.get(1)).getAsInt();
            }
            pieChart.addSeries("Others", Others_Count);
        }

        // display chart
        JFrame frame = new SwingWrapper(pieChart).displayChart();
        javax.swing.SwingUtilities.invokeLater(() -> frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE));

    }
    
}
    
    

