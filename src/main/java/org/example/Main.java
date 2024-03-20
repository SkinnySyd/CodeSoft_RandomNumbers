package org.example;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int RandomGenerator(){
        int max=10, min=1;
        Random random = new Random();
        int RandomNumber = random.nextInt(max - min + 1) + min;
        return RandomNumber;}
    public static int UserGuesser(){
        System.out.println("Guess what: ");
        Scanner scanner = new Scanner(System.in);
        int userGuess = scanner.nextInt();

        return userGuess;
    }
    public static boolean playagain(){
        System.out.println("wanna play again? Y/N");
        Scanner scanner = new Scanner(System.in);
        String playagain = scanner.next();
        if(!playagain.equalsIgnoreCase("Y")){
            System.out.println("Score: "+score);
            System.out.println("Fails: "+fail);
            return false;
        }else{
            attempts=3;
            return true;
        }
    }
    static int score=0;
    static int fail=0;
    static String playerName;

    static int rdm=RandomGenerator();
    static int attempts=3;
    public static void CompareGuessAndRandom(MongoCollection<Document> collection){
//    int y;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        playerName = scanner.next();
        while(true){
            while(attempts>0) {
                int y = UserGuesser();
                if (rdm == y) {
                    System.out.println("Congratulations! Your guess is correct.");
                    score++;
                    attempts=3;
                    rdm=RandomGenerator();
                    break;
                } else if (rdm < y) {
                    System.out.println("too high, Guess again");
                    attempts--;
                } else if (rdm > y) {
                    System.out.println("too low, Guess again");
                    attempts--;
                }
            if(attempts==0){fail++; rdm=RandomGenerator();}
            }
            Boolean ty=playagain();
            System.out.println("your are "+ty);
            if(ty==false){
                Document userData = new Document("name", playerName)
                        .append("score", score)
                        .append("fails", fail);
                collection.insertOne(userData);
                break;
            }

        }

    }
    public static void main(String[] args)  {


        String Conn = "mongodb+srv://SkinnySydMongoDB:zPnlE3MuRk0FKf7V@skinnysydcluster.bwe4y9t.mongodb.net/?retryWrites=true&w=majority&appName=SkinnySydCluster";

        MongoClient mongoClient = MongoClients.create(Conn);
        MongoDatabase database = mongoClient.getDatabase("RandomGenNumbers");
        MongoCollection<Document> collection = database.getCollection("ScoreFail");


        System.out.println("Hello world!");
//        int y=UserGuesser();
        CompareGuessAndRandom(collection);
    }
}