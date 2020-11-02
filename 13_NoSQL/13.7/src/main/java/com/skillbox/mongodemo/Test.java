package com.skillbox.mongodemo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lt;

public class Test {

    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient( "127.0.0.1" , 27017 );
        MongoDatabase database = mongoClient.getDatabase("local");

        MongoCollection<Document> shops = database.getCollection("Shops");
        MongoCollection<Document> products = database.getCollection("Products");

        shops.drop();
        products.drop();

        addShop("First", shops);
        addShop("Second", shops);
        addShop("Third", shops);

        System.out.println(shops.countDocuments());

        addProduct("Bread", 50, products);
        addProduct("Butter", 700, products);
        addProduct("Milk", 300, products);

        System.out.println(products.find().first().get("Price"));

        addProductIntoShop("First", "Bread", shops);
        addProductIntoShop("First", "Milk", shops);
        addProductIntoShop("First", "Butter", shops);

        System.out.println(shops.find().first().get("Name"));
        System.out.println(shops.find().first().get("Products").toString());

        statisticsOfShops(shops, products);

        Scanner scanner = new Scanner(System.in);
        boolean inLoop = true;

        while (inLoop) {
            System.out.println("\nCommands:\nADD_SHOP *(* - name of shop)\nADD_PRODUCT * **(* - name of product, ** - price)" +
                    "\nSHOW_PRODUCT * **(* - shop name, ** - product name)\nSTATISTICS\nQ (to quit)");

            String command = scanner.nextLine();

            switch (command.split(" ")[0]) {
                case ("ADD_SHOP"):
                    addShop(command.split(" ")[1], shops);
                    break;
                case ("ADD_PRODUCT"):
                    addProduct(command.split(" ")[1], Integer.parseInt(command.split(" ")[2]), products);
                    break;
                case ("SHOW_PRODUCT"):
                    addProductIntoShop(command.split(" ")[1], command.split(" ")[2], shops);
                    break;
                case ("STATISTICS"):
                    statisticsOfShops(shops, products);
                    break;
                case ("Q"):
                    inLoop = false;
            }
        }
    }

    public static void addShop(String name, MongoCollection<Document> shops)
    {
        shops.insertOne(new Document()
                .append("Name", name)
                .append("Products", new ArrayList<String>()));
        System.out.println("Shop added!");
    }

    public static void addProduct(String name,int price, MongoCollection<Document> products)
    {
        products.insertOne(new Document()
                .append("Name", name)
                .append("Price", price));
        System.out.println("Product added!");
    }

    public static void addProductIntoShop(String shopName,String productName, MongoCollection<Document> shops)
    {
        List<String> list = (ArrayList<String>) shops.find(eq("Name", shopName)).first().get("Products");

        if(list.contains(productName))
        {
            System.out.println("This product already shown in this shop.");
            return;
        }
        else
        {
            list.add(productName);
            shops.updateOne(eq("Name", shopName), new Document("$set", new Document("Products", list)));
        }

        System.out.println("Product added!");
    }

    public static void statisticsOfShops(MongoCollection<Document> shops, MongoCollection<Document> products)
    {

        System.out.println("\nStatistics:");
        shops.find().forEach((Consumer<Document>) document -> {

            List<String> list = (ArrayList<String>)document.get("Products");
            if(list.size() == 0)
            {
                System.out.println("\nNo products into shop \"" + document.get("Name") + "\" ");
                return;
            }

            List<Bson> aggs = Arrays.asList(
                    Aggregates.match(eq("Name", document.get("Name"))),
                    Aggregates.lookup("Products", "Products", "Name", "Products"),
                    Aggregates.unwind("$Products"),
                    Aggregates.replaceRoot("$Products"));

            String averagePrice = shops.aggregate(new ArrayList<Bson>() {
                {
                    addAll(aggs);
                    add(Aggregates.group("1",Accumulators.avg("Price", "$Price")));
                }
            }).first().get("Price").toString();

            String maxPrice = shops.aggregate(new ArrayList<Bson>() {
                {
                    addAll(aggs);
                    add(Aggregates.group("1",Accumulators.max("Price", "$Price")));
                }
            }).first().get("Price").toString();

            String minPrice = shops.aggregate(new ArrayList<Bson>() {
                {
                    addAll(aggs);
                    add(Aggregates.group("1",Accumulators.min("Price", "$Price")));
                }
            }).first().get("Price").toString();

            String lowerThanHundred = shops.aggregate(new ArrayList<Bson>() {
                {
                    addAll(aggs);
                    add(Aggregates.match(lt("Price", 100)));
                    add(Aggregates.group("1" ,Accumulators.sum("Count", 1)));
                }
            }).first().get("Count").toString();

            System.out.println("\nAverage price in shop \"" + document.get("Name") + "\": " + averagePrice);
            System.out.println("Max price in shop \"" + document.get("Name") + "\": " + maxPrice);
            System.out.println("Min price in shop \"" + document.get("Name") + "\": " + minPrice);
            System.out.println("Count price lower than 100 in shop \"" + document.get("Name") + "\": " + lowerThanHundred);

        });
    }
}