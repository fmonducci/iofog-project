package com.iofog.manager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iofog.manager.service.IoFogService;
import com.iofog.manager.service.CTLService;
import com.iofog.manager.service.MongoService;
import com.iofog.manager.service.TrainingService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

@RestController
public class Controller {

    private MongoService mongoService = new MongoService();

    public Controller() throws UnknownHostException {
    }

    /*****************************
     MongoService
     ****************************/

    @PostMapping(value = "/store", produces = "application/json")
    public String store(@RequestBody JSONObject body) throws IOException, InterruptedException {
        return mongoService.store(body);
    }


    /*****************************
     iofogctl
     ****************************/

    @PostMapping(value = "/ctl-deploy", produces = "application/json")
    public String volumeDeployment(@RequestBody String body) throws IOException, InterruptedException {
        return CTLService.deploy(body);
    }

    @GetMapping(value = "/ctl-describe/agent/{name}", produces = "application/json")
    public String describeAgent(@PathVariable String name) throws IOException, InterruptedException {
        return CTLService.describe("agent", name);
    }

    @GetMapping(value = "/ctl-describe/agent-config/{name}", produces = "application/json")
    public String describeAgentConfig(@PathVariable String name) throws IOException, InterruptedException {
        return CTLService.describe("agent-config", name);
    }

    @GetMapping(value = "/ctl-describe/application/{name}", produces = "application/json")
    public String describeApplication(@PathVariable String name) throws IOException, InterruptedException {
        return CTLService.describe("application", name);
    }

    @GetMapping(value = "/ctl-describe/microservice/{name}", produces = "application/json")
    public String describeMicroservice(@PathVariable String name) throws IOException, InterruptedException {
        return CTLService.describe("microservice", name);
    }

    @GetMapping(value = "/ctl-describe/controller/{name}", produces = "application/json")
    public String describeController(@PathVariable String name) throws IOException, InterruptedException {
        return CTLService.describe("controller", name);
    }

    @GetMapping(value = "/ctl-describe/route/{name}", produces = "application/json")
    public String describeRoute(@PathVariable String name) throws IOException, InterruptedException {
        return CTLService.describe("route", name);
    }

    @GetMapping(value = "/ctl-describe/controlplane", produces = "application/json")
    public String describeControlPlane() throws IOException, InterruptedException {
        return CTLService.describe("controlplane", "");
    }

    /*****************************
     Training
     ****************************/

    @GetMapping(value = "/train", produces = "application/json")
    public String train() {
        return TrainingService.launch();
    }


    /**************************************************************************
     **************************************************************************
     **************************************************************************
                                    IoFogService
     **************************************************************************
     **************************************************************************
     ***************************************************************************/

    /*****************************
     Controller - Manage your controller
     ****************************/

    @GetMapping(value = "/status", produces = "application/json")
    public String status() throws JsonProcessingException {
        return IoFogService.getStatus();
    }

    @GetMapping(value = "/email-activation", produces = "application/json")
    public String emailActivation() throws JsonProcessingException {
        return IoFogService.getEmailActivation();
    }

    @GetMapping(value = "/fog-types", produces = "application/json")
    public String fogTypes() throws JsonProcessingException {
        return IoFogService.getFogTypes();
    }

    /*****************************
     ioFog - Manage your agents
     ****************************/

    @GetMapping(value = "/iofog-list", produces = "application/json")
    public String getFogList(@RequestHeader("Authorization") String authorization) throws JsonProcessingException {
        return IoFogService.getFogList(authorization);
    }

    @PostMapping(value = "/iofog", produces = "application/json")
    public String newNode(@RequestHeader("Authorization") String authorization,
                        @RequestBody String body) throws JsonProcessingException {
        return IoFogService.newNode(authorization, body);
    }

    @PatchMapping(value = "/iofog/{uuid}", produces = "application/json")
    public String updateAgent(@RequestHeader("Authorization") String authorization, @PathVariable String uuid,
                              @RequestBody String body) throws JsonProcessingException {
        return IoFogService.updateAgent(authorization, uuid, body);
    }

    @DeleteMapping(value = "/iofog/{uuid}", produces = "application/json")
    public String deleteAgent(@RequestHeader("Authorization") String authorization,
                              @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.deleteAgent(authorization, uuid);
    }

    @GetMapping(value = "/iofog/{uuid}", produces = "application/json")
    public String getAgent(@RequestHeader("Authorization") String authorization,
                           @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.getAgent(authorization, uuid);
    }

    @GetMapping(value = "/iofog/{uuid}/provisioning-key", produces = "application/json")
    public String getProvisioningKey(@RequestHeader("Authorization") String authorization,
                           @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.getProvisioningKey(authorization, uuid);
    }

    @PostMapping(value = "/iofog/{uuid}/version/{versionCommand}", produces = "application/json")
    public String changeVersion(@RequestHeader("Authorization") String authorization,
                                     @PathVariable String uuid, @PathVariable String versionCommand) throws JsonProcessingException {
        return IoFogService.changeVersion(authorization, uuid, versionCommand);
    }

    @PostMapping(value = "/iofog/{uuid}/reboot", produces = "application/json")
    public String rebootAgent(@RequestHeader("Authorization") String authorization,
                                @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.rebootAgent(authorization, uuid);
    }

    @PostMapping(value = "/iofog/{uuid}/prune", produces = "application/json")
    public String pruneAgent(@RequestHeader("Authorization") String authorization,
                              @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.pruneAgent(authorization, uuid);
    }

    @GetMapping(value = "/iofog/{uuid}/hal/hw", produces = "application/json")
    public String getHALHW(@RequestHeader("Authorization") String authorization,
                                     @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.getHALHW(authorization, uuid);
    }

    @GetMapping(value = "/iofog/{uuid}/hal/usb", produces = "application/json")
    public String getHALUSB(@RequestHeader("Authorization") String authorization,
                           @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.getHALUSB(authorization, uuid);
    }

    /*****************************
     Catalog - Manage your catalog
     ****************************/

    @GetMapping(value = "/catalog/microservices", produces = "application/json")
    public String getCatalog(@RequestHeader("Authorization") String authorization) throws JsonProcessingException {
        return IoFogService.getCatalog(authorization);
    }

    @PostMapping(value = "/catalog/microservices", produces = "application/json")
    public String newCatalogItem(@RequestHeader("Authorization") String authorization,
                            @RequestBody String body) throws JsonProcessingException {
        return IoFogService.newCatalogItem(authorization, body);
    }

    @GetMapping(value = "/catalog/microservices/{id}", produces = "application/json")
    public String getCatalogItemInfo(@RequestHeader("Authorization") String authorization,
                                     @PathVariable String id) throws JsonProcessingException {
        return IoFogService.getCatalogItemInfo(authorization, id);
    }

    @PatchMapping(value = "/catalog/microservices/{id}", produces = "application/json")
    public String updateCatalogItem(@RequestHeader("Authorization") String authorization, @PathVariable String id,
                              @RequestBody String body) throws JsonProcessingException {
        return IoFogService.updateCatalogItem(authorization, id, body);
    }

    @DeleteMapping(value = "/catalog/microservices/{id}", produces = "application/json")
    public String deleteCatalogItem(@RequestHeader("Authorization") String authorization,
                              @PathVariable String id) throws JsonProcessingException {
        return IoFogService.deleteCatalogItem(authorization, id);
    }

    /*****************************
     Registries - Manage your registries
     ****************************/

    @PostMapping(value = "/registries", produces = "application/json")
    public String newRegistry(@RequestHeader("Authorization") String authorization,
                                 @RequestBody(required=false) String body) throws JsonProcessingException {
        return IoFogService.newRegistry(authorization, body);
    }

    @GetMapping(value = "/registries", produces = "application/json")
    public String getRegistries(@RequestHeader("Authorization") String authorization) throws JsonProcessingException {
        return IoFogService.getRegistries(authorization);
    }

    @PatchMapping(value = "/registries/{id}", produces = "application/json")
    public String updateRegistry(@RequestHeader("Authorization") String authorization, @PathVariable String id,
                                    @RequestBody String body) throws JsonProcessingException {
        return IoFogService.updateRegistry(authorization, id, body);
    }

    @DeleteMapping(value = "/registries/{id}")
    public String deleteRegistry(@RequestHeader("Authorization") String authorization,
                                    @PathVariable String id) throws JsonProcessingException {
        return IoFogService.deleteRegistry(authorization, id);
    }


    /*****************************
     Flow - Manage your flows
     ****************************/

    @GetMapping(value = "/flow", produces = "application/json")
    public String getFlows(@RequestHeader("Authorization") String authorization) throws JsonProcessingException {
        return IoFogService.getFlows(authorization);
    }

    @PostMapping(value = "/flow", produces = "application/json")
    public String newFlow(@RequestHeader("Authorization") String authorization,
                          @RequestBody String info) throws JsonProcessingException {
        return IoFogService.newFlow(authorization, info);
    }

    @GetMapping(value = "/flow/{id}", produces = "application/json")
    public String getFlow(@RequestHeader("Authorization") String authorization,
                        @PathVariable String id) throws JsonProcessingException {
        return IoFogService.getFlow(authorization, id);
    }

    @PatchMapping(value = "/flow/{id}", produces = "application/json")
    public String updateFlow(@RequestHeader("Authorization") String authorization,
                          @PathVariable String id, @RequestBody String body) throws JsonProcessingException {
        return IoFogService.updateFlow(authorization, id, body);
    }

    @DeleteMapping(value = "/flow/{id}", produces = "application/json")
    public String deleteFlow(@RequestHeader("Authorization") String authorization,
                             @PathVariable String id) throws JsonProcessingException {
        return IoFogService.deleteFlow(authorization, id);
    }

    /*****************************
     Microservices - Manage your microservices
     ****************************/


    @GetMapping(value = "/microservices", produces = "application/json")
    public String getMicroservices(@RequestHeader("Authorization") String authorization,
                                @RequestParam("flowId") Integer flowId) throws JsonProcessingException {
        return IoFogService.getMicroservices(authorization, flowId);
    }

    @PostMapping(value = "/microservices", produces = "application/json")
    public String newMicroservice(@RequestHeader("Authorization") String authorization,
                                @RequestBody String body) throws JsonProcessingException {
        return IoFogService.newMicroservice(authorization, body);
    }

    @GetMapping(value = "/microservices/{uuid}", produces = "application/json")
    public String getMicroservice(@RequestHeader("Authorization") String authorization,
                                   @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.getMicroservice(authorization, uuid);
    }

    @PatchMapping(value = "/microservices/{uuid}", produces = "application/json")
    public String updateMicroservice(@RequestHeader("Authorization") String authorization,
                                  @PathVariable String uuid, @RequestBody String body) throws JsonProcessingException {
        return IoFogService.updateMicroservice(authorization, uuid, body);
    }

    @DeleteMapping(value = "/microservices/{uuid}", produces = "application/json")
    public String deleteMicroservice(@RequestHeader("Authorization") String authorization,
                                  @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.deleteMicroservice(authorization, uuid);
    }

    @PostMapping(value = "/microservice/{uuid}/port-mapping", produces = "application/json")
    public String newPortMapping(@RequestHeader("Authorization") String authorization,
                                 @PathVariable String uuid, @RequestBody String info) throws JsonProcessingException {
        return IoFogService.newPortMapping(authorization, uuid, info);
    }

    @GetMapping(value = "/microservices/{uuid}/port-mapping", produces = "application/json")
    public String getPortMapping(@RequestHeader("Authorization") String authorization,
                                     @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.getPortMapping(authorization, uuid);
    }

    @GetMapping(value = "/microservices/{uuid}/port-mapping/{internalPort}", produces = "application/json")
    public String deletePortMapping(@RequestHeader("Authorization") String authorization,
                                 @PathVariable String uuid, @PathVariable String internalPort) throws JsonProcessingException {
        return IoFogService.deletePortMapping(authorization, uuid, internalPort);
    }

    @PostMapping(value = "/microservices/{uuid}/volume-mapping", produces = "application/json")
    public String newVolumeMapping(@RequestHeader("Authorization") String authorization,
                                 @PathVariable String uuid, @RequestBody String info) throws JsonProcessingException {
        return IoFogService.newVolumeMapping(authorization, uuid, info);
    }

    @GetMapping(value = "/microservices/{uuid}/volume-mapping", produces = "application/json")
    public String getVolumeMapping(@RequestHeader("Authorization") String authorization,
                                 @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.getVolumeMapping(authorization, uuid);
    }

    @GetMapping(value = "/microservices/{uuid}/volume-mapping/{id}", produces = "application/json")
    public String deleteVolumeMapping(@RequestHeader("Authorization") String authorization,
                                    @PathVariable String uuid, @PathVariable String id) throws JsonProcessingException {
        return IoFogService.deleteVolumeMapping(authorization, uuid, id);
    }

    /*****************************
     Routing - Manage your routes
     ****************************/

    @GetMapping(value = "routes", produces = "application/json")
    public String getRoutes(@RequestHeader("Authorization") String authorization) throws JsonProcessingException {
        return IoFogService.getRoutes(authorization);
    }

    @PostMapping(value = "routes", produces = "application/json")
    public String newRoute(@RequestHeader("Authorization") String authorization,
                            @RequestBody String body) throws JsonProcessingException {
        return IoFogService.newRoute(authorization, body);
    }

    @GetMapping(value = "routes/{name}", produces = "application/json")
    public String getRoute(@RequestHeader("Authorization") String authorization,
                           @PathVariable String name) throws JsonProcessingException {
        return IoFogService.getRoute(authorization, name);
    }

    @PatchMapping(value = "routes/{name}", produces = "application/json")
    public String updateRoute(@RequestHeader("Authorization") String authorization,
                           @PathVariable String name, @RequestBody String body) throws JsonProcessingException {
        return IoFogService.updateRoute(authorization, name, body);
    }

    @DeleteMapping(value = "routes/{name}", produces = "application/json")
    public String updateRoute(@RequestHeader("Authorization") String authorization,
                              @PathVariable String name) throws JsonProcessingException {
        return IoFogService.deleteRoute(authorization, name);
    }

    /*****************************
     Diagnostics - Diagnostic your microservices
     ****************************/

    @PostMapping(value = "microservices/{uuid}/image-snapshot", produces = "application/json")
    public String newMicroserviceStatus(@RequestHeader("Authorization") String authorization,
                           @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.newMicroserviceStatus(authorization, uuid);
    }

    @GetMapping(value = "microservices/{uuid}/image-snapshot", produces = "application/json")
    public String getMicroserviceStatus(@RequestHeader("Authorization") String authorization,
                                        @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.getMicroserviceStatus(authorization, uuid);
    }

    @PatchMapping(value = "microservices/{uuid}/strace", produces = "application/json")
    public String updateMicroserviceStrace(@RequestHeader("Authorization") String authorization,
                                        @PathVariable String uuid, @RequestBody String body) throws JsonProcessingException {
        return IoFogService.updateMicroserviceStrace(authorization, uuid, body);
    }

    @GetMapping(value = "microservices/{uuid}/strace", produces = "application/json")
    public String getMicroserviceStrace(@RequestHeader("Authorization") String authorization,
                                        @PathVariable String uuid, @RequestParam("format") String format) throws JsonProcessingException {
        return IoFogService.getMicroserviceStrace(authorization, uuid, format);
    }

    @PutMapping(value = "microservices/{uuid}/strace", produces = "application/json")
    public String postMicroserviceStraceFTP(@RequestHeader("Authorization") String authorization,
                                           @PathVariable String uuid, @RequestBody String body) throws JsonProcessingException {
        return IoFogService.postMicroserviceStraceFTP(authorization, uuid, body);
    }

    /*****************************
     Tunnel - Manage ssh tunnels
     ****************************/

    @PatchMapping(value = "iofog/{uuid}/tunnel", produces = "application/json")
    public String updateSSHTunnel(@RequestHeader("Authorization") String authorization,
                                           @PathVariable String uuid, @RequestBody String body) throws JsonProcessingException {
        return IoFogService.updateSSHTunnel(authorization, uuid, body);
    }

    @GetMapping(value = "iofog/{uuid}/tunnel", produces = "application/json")
    public String getSSHTunnel(@RequestHeader("Authorization") String authorization,
                                  @PathVariable String uuid) throws JsonProcessingException {
        return IoFogService.getSSHTunnel(authorization, uuid);
    }

    /*****************************
     User - Manage your users
     ****************************/

    @PostMapping(value = "/user/login", produces = "application/json")
    public String login(@RequestBody String body) throws JsonProcessingException {
        return IoFogService.login(body);
    }

    @PostMapping(value = "/user/logout", produces = "application/json")
    public String logout(@RequestHeader("Authorization") String authorization) throws JsonProcessingException {
        return IoFogService.logout(authorization);
    }

    @PostMapping(value = "/user/signup", produces = "application/json")
    public String signup(@RequestBody String body) throws JsonProcessingException {
        return IoFogService.signup(body);
    }

    @GetMapping(value = "/user/signup/resend-activation", produces = "application/json")
    public String resendActivation(@RequestParam("email") String email) throws JsonProcessingException {
        return IoFogService.resendActivation(email);
    }

    @PostMapping(value = "/user/activate", produces = "application/json")
    public String userActivate(@RequestBody String body) throws JsonProcessingException {
        return IoFogService.userActivate(body);
    }

    @GetMapping(value = "/user/profile", produces = "application/json")
    public String getProfile(@RequestHeader("Authorization") String authorization) throws JsonProcessingException {
        return IoFogService.getProfile(authorization);
    }

    @PatchMapping(value = "/user/profile", produces = "application/json")
    public String updateProfile(@RequestHeader("Authorization") String authorization,
                                @RequestBody String body) throws JsonProcessingException {
        return IoFogService.updateProfile(authorization, body);
    }

    @DeleteMapping(value = "/user/profile", produces = "application/json")
    public String deleteProfile(@RequestHeader("Authorization") String authorization,
                                @RequestBody(required=false) String body) throws JsonProcessingException {
        return IoFogService.deleteProfile(authorization, body);
    }

    @PatchMapping(value = "/user/password", produces = "application/json")
    public String updatePassword(@RequestHeader("Authorization") String authorization,
                                @RequestBody String body) throws JsonProcessingException {
        return IoFogService.updatePassword(authorization, body);
    }

    @DeleteMapping(value = "/user/password", produces = "application/json")
    public String deletePassword(@RequestBody String body) throws JsonProcessingException {
        return IoFogService.deletePassword(body);
    }




}
