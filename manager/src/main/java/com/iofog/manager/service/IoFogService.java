package com.iofog.manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class IoFogService {

    private static String baseURL = "http://localhost:51121/api/v3";
    private static RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    private static ObjectMapper mapper = new ObjectMapper();

    /*****************************
     Controller - Manage your controller
     ****************************/

    public static String getStatus() throws JsonProcessingException {
        String url = baseURL+"/status";
        return IoFogService.request(url, new HttpHeaders(), null, HttpMethod.GET).toString();
    }

    public static String getEmailActivation() throws JsonProcessingException {
        String url = baseURL+"/email-activation";
        HttpHeaders headers = IoFogService.setHeaders(new HashMap<>());
        return IoFogService.request(url, headers, null, HttpMethod.GET).toString();
    }

    public static String getFogTypes() throws JsonProcessingException {
        String url = baseURL+"/fog-types";
        HttpHeaders headers = IoFogService.setHeaders(new HashMap<>());
        return IoFogService.request(url, headers, null, HttpMethod.GET).toString();
    }


    /*****************************
     ioFog - Manage your agents
     ****************************/

    public static String getFogList(String authorization) throws JsonProcessingException {
        String url = baseURL+"/iofog-list";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String newNode(String authorization, String ioFogNodeInfo) throws JsonProcessingException {
        String url = baseURL+"/iofog";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, ioFogNodeInfo, HttpMethod.POST);
    }

    public static String updateAgent(String authorization, String uuid, String ioFogNodeInfo) throws JsonProcessingException {
        String url = baseURL+"/iofog/"+uuid;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, ioFogNodeInfo, HttpMethod.PATCH);
    }

    public static String deleteAgent(String authorization, String uuid) throws JsonProcessingException {
        String url = baseURL+"/iofog/"+uuid;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.DELETE);
    }

    public static String getAgent(String authorization, String uuid) throws JsonProcessingException {
        String url = baseURL+"/iofog/"+uuid;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String getProvisioningKey(String authorization, String uuid) throws JsonProcessingException {
        String url = baseURL+"/iofog/"+uuid+"/provisioning-key";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String changeVersion(String authorization, String uuid, String version) throws JsonProcessingException {
        String url = baseURL+"/iofog/"+uuid+"/version/"+version;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.POST);
    }

    public static String rebootAgent(String authorization, String uuid) throws JsonProcessingException {
        String url = baseURL+"/iofog/"+uuid+"/reboot";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.POST);
    }

    public static String pruneAgent(String authorization, String uuid) throws JsonProcessingException {
        String url = baseURL+"/iofog/"+uuid+"/prune";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.POST);
    }

    public static String getHALHW(String authorization, String uuid) throws JsonProcessingException {
        String url = baseURL+"/iofog/"+uuid+"/hal/hw";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String getHALUSB(String authorization, String uuid) throws JsonProcessingException {
        String url = baseURL+"/iofog/"+uuid+"/hal/usb";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }




    /*****************************
     Catalog - Manage your catalog
     ****************************/

    public static String getCatalog(String authorization) throws JsonProcessingException {
        String url = baseURL+"/catalog/microservices";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String newCatalogItem(String authorization, String createCatalogItem) throws JsonProcessingException {
        String url = baseURL+"/catalog/microservices";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, createCatalogItem, HttpMethod.POST);
    }

    public static String getCatalogItemInfo(String authorization, String id) throws JsonProcessingException {
        String url = baseURL+"/catalog/microservices/"+id;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String updateCatalogItem(String authorization, String id, String update) throws JsonProcessingException {
        String url = baseURL+"/catalog/microservices/"+id;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, update, HttpMethod.PATCH);
    }

    public static String deleteCatalogItem(String authorization, String id) throws JsonProcessingException {
        String url = baseURL+"/catalog/microservices/"+id;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.DELETE);
    }

    /*****************************
     Registries - Manage your registries
     ****************************/

    public static String newRegistry(String authorization, String registry) throws JsonProcessingException {
        String url = baseURL+"/registries";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, registry, HttpMethod.POST);
    }

    public static String getRegistries(String authorization) throws JsonProcessingException {
        String url = baseURL+"/registries";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String deleteRegistry(String authorization, String id) throws JsonProcessingException {
        String url = baseURL+"/registries/"+id;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.DELETE);
    }

    public static String updateRegistry(String authorization, String id, String update) throws JsonProcessingException {
        String url = baseURL+"/registries/"+id;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, update, HttpMethod.PATCH);
    }


    /*****************************
     Flow - Manage your flows
     ****************************/

    public static String getFlows(String authorization) throws JsonProcessingException {
        String url = baseURL+"/flow";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String newFlow(String authorization, String info) throws JsonProcessingException {
        String url = baseURL+"/flow";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, info, HttpMethod.POST);
    }

    public static String getFlow(String authorization, String id) throws JsonProcessingException {
        String url = baseURL+"/flow/"+id;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String updateFlow(String authorization, String id, String info) throws JsonProcessingException {
        String url = baseURL+"/flow/"+id;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, info, HttpMethod.PATCH);
    }

    public static String deleteFlow(String authorization, String id) throws JsonProcessingException {
        String url = baseURL+"/flow/"+id;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.DELETE);
    }

    /*****************************
     Microservices - Manage your microservices
     ****************************/

    public static String getMicroservices(String authorization, Integer flowId) throws JsonProcessingException{
        String url = baseURL+"/microservices?flowId="+flowId;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String newMicroservice(String authorization, String info) throws JsonProcessingException{
        String url = baseURL+"/microservices";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, info, HttpMethod.POST);
    }

    public static String getMicroservice(String authorization, String uuid) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String updateMicroservice(String authorization, String uuid, String info) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, info, HttpMethod.PATCH);
    }

    public static String deleteMicroservice(String authorization, String uuid) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.DELETE);
    }

    public static String newPortMapping(String authorization, String uuid, String info) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid+"/port-mapping";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, info, HttpMethod.POST);
    }

    public static String getPortMapping(String authorization, String uuid) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid+"/port-mapping";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String deletePortMapping(String authorization, String uuid, String portMapping) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid+"/volume-mapping/"+ portMapping;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.DELETE);
    }

    public static String newVolumeMapping(String authorization, String uuid, String info) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid+"/volume-mapping";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, info, HttpMethod.POST);
    }

    public static String getVolumeMapping(String authorization, String uuid) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid+"/volume-mapping";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String deleteVolumeMapping(String authorization, String uuid, String id) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid+"/volume-mapping/"+ id;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.DELETE);
    }


    /*****************************
     Routing - Manage your routes
     ****************************/

    public static String getRoutes(String authorization) throws JsonProcessingException{
        String url = baseURL+"/routes";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String newRoute(String authorization, String body) throws JsonProcessingException{
        String url = baseURL+"/routes";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, body, HttpMethod.POST);
    }

    public static String getRoute(String authorization, String name) throws JsonProcessingException{
        String url = baseURL+"/routes/"+name;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String updateRoute(String authorization, String name, String body) throws JsonProcessingException{
        String url = baseURL+"/routes/"+name;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, body, HttpMethod.PATCH);
    }

    public static String deleteRoute(String authorization, String name) throws JsonProcessingException{
        String url = baseURL+"/routes/"+name;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.DELETE);
    }

    /*****************************
     Diagnostics - Diagnostic your microservices
     ****************************/

    public static String newMicroserviceStatus(String authorization, String uuid) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid+"image-snapshot";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.POST);
    }

    public static String getMicroserviceStatus(String authorization, String uuid) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid+"image-snapshot";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String updateMicroserviceStrace(String authorization, String uuid, String body) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid+"strace";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, body, HttpMethod.PATCH);
    }

    public static String getMicroserviceStrace(String authorization, String uuid, String format) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid+"strace?format="+format;
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String postMicroserviceStraceFTP(String authorization, String uuid, String body) throws JsonProcessingException{
        String url = baseURL+"/microservices/"+uuid+"strace";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, body, HttpMethod.PUT);
    }


    /*****************************
     Tunnel - Manage ssh tunnels
     ****************************/

    public static String updateSSHTunnel(String authorization, String uuid, String body) throws JsonProcessingException{
        String url = baseURL+"/iofog/"+uuid+"tunnel";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, body, HttpMethod.PATCH);
    }

    public static String getSSHTunnel(String authorization, String uuid) throws JsonProcessingException{
        String url = baseURL+"/iofog/"+uuid+"tunnel";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }




    /*****************************
    User - Manage your users
    ****************************/


    public static String login(String credentials) throws JsonProcessingException {
        String url = baseURL+"/user/login";
        HttpHeaders headers = IoFogService.setHeaders(new HashMap<>());
        return IoFogService.request(url, headers, credentials, HttpMethod.POST);
    }

    public static String logout(String authorization) throws JsonProcessingException {
        String url = baseURL+"/user/logout";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.POST);
    }

    public static String signup(String credentials) throws JsonProcessingException {
        String url = baseURL+"/user/signup";
        HttpHeaders headers = IoFogService.setHeaders(new HashMap<>());
        return IoFogService.request(url, headers, credentials, HttpMethod.POST);
    }

    public static String resendActivation(String email) throws JsonProcessingException {
        String url = baseURL+"/user/signup/resend-activation?email="+email;
        HttpHeaders headers = IoFogService.setHeaders(new HashMap<>());
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String userActivate(String code) throws JsonProcessingException {
        String url = baseURL+"/user/activate";
        HttpHeaders headers = IoFogService.setHeaders(new HashMap<>());
        return IoFogService.request(url, headers, code, HttpMethod.POST);
    }

    public static String getProfile(String authorization) throws JsonProcessingException {
        String url = baseURL+"/user/profile";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, null, HttpMethod.GET);
    }

    public static String updateProfile(String authorization, String body) throws JsonProcessingException {
        String url = baseURL+"/user/profile";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, body, HttpMethod.PATCH);
    }

    public static String deleteProfile(String authorization, String body) throws JsonProcessingException {
        String url = baseURL+"/user/profile";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, body, HttpMethod.DELETE);
    }

    public static String updatePassword(String authorization, String body) throws JsonProcessingException {
        String url = baseURL+"/user/password";
        Map <String, String> map = new HashMap<>();
        map.put("Authorization", authorization);
        HttpHeaders headers = IoFogService.setHeaders(map);
        return IoFogService.request(url, headers, body, HttpMethod.PATCH);
    }

    public static String deletePassword(String body) throws JsonProcessingException {
        String url = baseURL+"/user/profile";
        HttpHeaders headers = IoFogService.setHeaders(new HashMap<>());
        return IoFogService.request(url, headers, body, HttpMethod.DELETE);
    }



    /*****************************
     Utilities
     ****************************/

    private static String request(String url, HttpHeaders headers, String body, HttpMethod method) throws JsonProcessingException{
        HttpEntity<String> request;
        if (body == null){
            request = new HttpEntity(headers);
        }
        else{
            request = new HttpEntity(body, headers);
        }
        ResponseEntity<String> response = restTemplate.exchange(url, method, request, String.class);
        if (response.getBody()==null){
            return "";
        }
        else{
            return mapper.readTree(response.getBody()).toString();
        }
    }

    private static HttpHeaders setHeaders(Map<String,String> values){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        for (Map.Entry<String, String> entry : values.entrySet()) {
            headers.set(entry.getKey(), entry.getValue());
        }
        return headers;
    }



}
