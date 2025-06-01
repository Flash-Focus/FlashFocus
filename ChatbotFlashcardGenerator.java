import java.util.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

// Fetching info from chatbot
public class ChatbotFlashcardGenerator {

    public static HashMap<String, AnswerList<String>> flashCardDeck(String topic, String apiKey) {
         try {
             String apiUrl = "https://openrouter.ai/api/v1/chat/completions";

             // Build message array
             JSONArray messagesArray = new JSONArray();

             // Optional system prompt for better replies
             JSONObject systemMessage = new JSONObject();
             systemMessage.put("role", "system");
             systemMessage.put("content",
                     "You are a helpful study assistant. Reply in clear, plain language. Im going to be asking you to make flashcards, questions and answers like a multiple choice with 4 possible answer, and I want you to keep it a simple format as possible for me to easily read it in my system. Please have **Question**, then a seperate line stating the question, and **Answer**, then a new line which has the answers, and also identifiy which is is **Correct Answer** for me. Have **Correct Answer** on a new line, and keep the correct answers seperate please, not together with the rest, and make any math related questions/answers using plain text math instead of LaTeX, for symbols that are hard to render, just use words instead of that symbol such as infinity and arrows, please make 25 (Twenty-Five) EXACT flashcards.");
             messagesArray.put(systemMessage);

             // User message
             JSONObject userMessage = new JSONObject();
             userMessage.put("role", "user");
             userMessage.put("content", topic);
             messagesArray.put(userMessage);

             // Request body
             JSONObject requestBody = new JSONObject();
             requestBody.put("model", "deepseek/deepseek-chat-v3-0324:free");
             requestBody.put("messages", messagesArray);

             // Open connection
             URL url = new URL(apiUrl);
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setRequestMethod("POST");
             conn.setRequestProperty("Content-Type", "application/json");
             conn.setRequestProperty("Authorization", "Bearer " + apiKey);
             // Optional headers — remove or replace with real URLs if needed
             // conn.setRequestProperty("HTTP-Referer", "https://flashfocus.app");
             // conn.setRequestProperty("X-Title", "FlashFocus");
             conn.setDoOutput(true);

             // Send JSON request
             try (OutputStream os = conn.getOutputStream()) {
                 byte[] input = requestBody.toString().getBytes("utf-8");
                 os.write(input, 0, input.length);
             }

             // Read response
             InputStream is = conn.getResponseCode() < 400 ? conn.getInputStream() : conn.getErrorStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
             StringBuilder response = new StringBuilder();
             String responseLine;
             while ((responseLine = br.readLine()) != null) {
                 response.append(responseLine.trim());
             }

             // Parse JSON response
             JSONObject responseJson = new JSONObject(response.toString());

             // Debugging output
             // System.out.println("\nRaw response:\n" + responseJson.toString(2));

             // Handle errors
             if (responseJson.has("error")) {
                 System.out.println("API Error: " + responseJson.getJSONObject("error").getString("message"));
                 return null;
             }

             // Print AI reply
             String reply = responseJson
                     .getJSONArray("choices")
                     .getJSONObject(0)
                     .getJSONObject("message")
                     .getString("content");

             // System.out.println("\nBot: " + reply);

             boolean isQuestion = false;
             boolean isAnswer = false;
             boolean isCorrectAnswer = false;
             String question = "";
             List<String> answers = new ArrayList<>();
             String correctAnswer = "";
             int correctAnswerIndex = 0;
             int count = 0;

             HashMap<String, AnswerList<String>> flashCards = new HashMap<>();
             String[] lines = reply.split("\\R");

             for (String line : lines) {
                 line = line.trim();

                 if (line.equals("**Question**")) {
                     isQuestion = true;
                     isAnswer = false;
                     isCorrectAnswer = false;
                     continue;
                 } else if (line.equals("**Answer**")) {
                     count = 0;
                     isQuestion = false;
                     isAnswer = true;
                     isCorrectAnswer = false;
                     continue;
                 } else if (line.equals("**Correct Answer**")) {
                     isQuestion = false;
                     isAnswer = false;
                     isCorrectAnswer = true;
                     continue;
                 } else if (isQuestion) {
                     question = line;
                     isQuestion = false;
                     continue;
                 } else if (isAnswer) {
                     if (count < 4) {
                         answers.add(line);
                         count++;
                         continue;
                     }
                 } else if (isCorrectAnswer) {
                     correctAnswer = line;
                     correctAnswerIndex = answers.indexOf(correctAnswer);
                     if (!question.isEmpty() && answers.size() == 4 && !correctAnswer.isEmpty()) {
                         flashCards.put(question,
                                 new AnswerList<String>(new ArrayList<>(answers), correctAnswerIndex));

                         question = "";
                         answers.clear();
                         correctAnswer = "";
                         correctAnswerIndex = 0;
                     }
                     isCorrectAnswer = false;
                 }

             }
             savingDeck(flashCards, topic);
             return flashCards;
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }

     public static void savingDeck(HashMap<String, AnswerList<String>> deck, String fName) {

         try {
             Writer out = new FileWriter(fName + ".txt");

             for (String s : deck.keySet()) {
                 out.write(s + "\n");

                 List<String> l = deck.get(s).getAnswers();
                 for (int i = 0; i < l.size(); i++) {
                     out.write(l.get(i) + "\n");
                 }
                 out.write("" + deck.get(s).getCorrectAnswerIndex());
                 out.write("\n");

             }
             out.close();

         } catch (IOException e) {
             return;
         }
     }

     public static HashMap<String, AnswerList<String>> loadDeck(String fName) {
         HashMap<String, AnswerList<String>> deck = new HashMap<>();
         List<String> answers = new ArrayList<>();
         int lineCount = 0;
         String question = "";
         int correctAnswerIndex = 0;

         try {
             BufferedReader reader = new BufferedReader(new FileReader(fName + ".txt"));

             String line;
             while ((line = reader.readLine()) != null) {
                 if (lineCount == 0) {
                     question = line;
                     lineCount++;
                 } else if (lineCount >= 1 && lineCount <= 4) {
                     answers.add(line);
                     lineCount++;
                 } else if (lineCount == 5) {
                     correctAnswerIndex = Integer.parseInt(line);
                     deck.put(question, new AnswerList<>(answers, correctAnswerIndex));
                     lineCount = 0;
                     question = "";
                     answers.clear();
                     correctAnswerIndex = 0;
                 }
             }
             reader.close();
             return deck;

         } catch (IOException e) {
             return null;
         }

     }
    

}


