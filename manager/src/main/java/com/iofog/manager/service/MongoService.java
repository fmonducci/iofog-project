package com.iofog.manager.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;

import java.net.UnknownHostException;

public class MongoService {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public MongoService() throws UnknownHostException {
        this.mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");
        this.database = mongoClient.getDatabase("test");
        this.collection = database.getCollection("collection");
    }

    public String store(JSONObject data){
        Document doc = Document.parse(data.toString());
        collection.insertOne(doc);
        return "";
    }

}
