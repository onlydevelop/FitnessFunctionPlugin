package com.anvil.fitness;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AnthropicClient {

    private static final String API_URL = "https://api.anthropic.com/v1/messages";
    private static final String ANTHROPIC_VERSION = "2023-06-01";
    private static final int MAX_TOKENS = 4096;

    private final HttpClient httpClient;

    public AnthropicClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public String sendMessage(String apiKey, String model, String content) {
        String requestBody = buildRequestBody(model, content);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("x-api-key", apiKey)
                .header("anthropic-version", ANTHROPIC_VERSION)
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Anthropic API error " + response.statusCode() + ": " + response.body());
            }
            return extractText(response.body());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to call Anthropic API", e);
        }
    }

    private String buildRequestBody(String model, String content) {
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", content);

        JsonArray messages = new JsonArray();
        messages.add(message);

        JsonObject body = new JsonObject();
        body.addProperty("model", model);
        body.addProperty("max_tokens", MAX_TOKENS);
        body.add("messages", messages);

        return body.toString();
    }

    private String extractText(String responseJson) {
        JsonObject response = JsonParser.parseString(responseJson).getAsJsonObject();
        return response.getAsJsonArray("content")
                .get(0).getAsJsonObject()
                .get("text").getAsString();
    }
}
