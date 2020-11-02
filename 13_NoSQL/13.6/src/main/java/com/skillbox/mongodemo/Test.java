package com.skillbox.mongodemo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.opencsv.CSVReader;
import org.bson.Document;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Test {
    public Test() {
    }

    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
        MongoDatabase database = mongoClient.getDatabase("local");
        MongoCollection<Document> collection = database.getCollection("Books");

        collection.drop();

        List<Document> books = new ArrayList();

        books.add((new Document())
                .append("Name", "The Hobbit")
                .append("Author", "J. R. R. Tolkien")
                .append("Year", "1937"));
        books.add((new Document())
                .append("Name", "The Lord of the Rings")
                .append("Author", "J. R. R. Tolkien").append("Part", 3)
                .append("Year", "1955"));
        books.add((new Document())
                .append("Name", "The Silmarillion")
                .append("Author", "J. R. R. Tolkien")
                .append("Year", "1977"));
        books.add((new Document())
                .append("Name", "We")
                .append("Author", "Ievgueni Ivanovitch Zamiatine")
                .append("Year", "1920"));
        books.add((new Document())
                .append("Name", "Le Petit Prince")
                .append("Author", "Antoine Marie Jean-Baptiste Roger vicomte de Saint-Exupéry")
                .append("Year", "1943"));

        collection.insertMany(books);

        collection.find().sort(new BasicDBObject("Year", 1)).limit(1).forEach((Consumer<Document>) document -> {
            System.out.println("The oldest book: " + document.get("Name") + " , " + document.get("Year") + " year");
        });

        System.out.println("Books of my favourite author J. R. R. Tolkien:");
        collection.find(Filters.eq("Author", "J. R. R. Tolkien")).sort(new BasicDBObject("Year", 1)).forEach((Consumer<Document>) document -> {
            System.out.println(document.get("Name") + " " + document.get("Year"));
        });

        collection.drop();

        String csvFile = "C:\\Users\\stepa\\Desktop\\SkillBox\\Repo\\java_basics\\13_NoSQL\\13.6\\src\\main\\java\\com\\skillbox\\mongodemo\\mongo.csv";
        List<Document> students = new ArrayList();
        CSVReader reader = null;

        try {
            reader = new CSVReader(new FileReader(csvFile));

            String[] line;
            while((line = reader.readNext()) != null) {
                List<String> list = new ArrayList();
                String[] arr = line[2].split(",");
                String[] var11 = arr;
                int var12 = arr.length;

                for(int var13 = 0; var13 < var12; ++var13) {
                    String s = var11[var13];
                    list.add(s);
                }

                students.add((new Document()).append("Name", line[0]).append("Age", line[1]).append("Courses", list));
            }

            reader.close();
        } catch (IOException var15) {
            var15.printStackTrace();
        }

        collection.insertMany(students);
        System.out.println("\nTotal count of students: " + collection.countDocuments());
        System.out.println("Count of students older than 40 years: " + collection.countDocuments(Filters.gt("Age", "40")));
        System.out.println("The youngest student: " + ((Document)collection.find().sort(new BasicDBObject("Age", 1)).limit(1).first()).get("Name"));
        System.out.println("Courses of oldest student: " + ((Document)collection.find().sort(new BasicDBObject("Age", -1)).limit(1).first()).get("Courses").toString());
    }
}