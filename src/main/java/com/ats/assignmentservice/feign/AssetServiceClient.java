package com.ats.assignmentservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ats.assignmentservice.dto.UpdateAssetStatusRequest;
import com.ats.assignmentservice.feign.dto.AssetResponse;

@FeignClient(
	    name = "asset-service",
	    url = "${asset.service.url}",
	    configuration = {FeignClientConfig.class, FeignConfig.class})

public interface AssetServiceClient {

	@GetMapping("/api/assets/{id}")
    AssetResponse getAsset(@PathVariable Long id);

    @PatchMapping("/api/assets/{id}/status")
    AssetResponse updateAsset(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long id,
            @RequestBody UpdateAssetStatusRequest request);
}

