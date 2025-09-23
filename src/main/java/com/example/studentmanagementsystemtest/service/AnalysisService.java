
package com.example.studentmanagementsystemtest.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AnalysisService {

    @Value("${analysis.service.url:http://localhost:5001}")
    private String analysisServiceUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AnalysisService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public Map< String, Object > generateStudentReport(Long studentId, String modelType) {
        try {
            String url = analysisServiceUrl + "/api/analysis/report/" + studentId;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map< String, String > requestBody = new HashMap<>();
            requestBody.put("model_type", modelType);

            HttpEntity< Map< String, String > > requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity< String > response = restTemplate.postForEntity(url, requestEntity, String.class);

            JsonNode jsonResponse = objectMapper.readTree(response.getBody());

            Map< String, Object > result = new HashMap<>();
            result.put("success", jsonResponse.get("success").asBoolean());
            result.put("message", jsonResponse.get("message").asText());

            if (jsonResponse.has("report_path")) {
                result.put("reportPath", jsonResponse.get("report_path").asText());
            }

            return result;
        } catch (Exception e) {
            Map< String, Object > errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("message", "分析服务调用失败: " + e.getMessage());
            return errorResult;
        }
    }
}