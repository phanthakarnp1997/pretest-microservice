package com.example.microservice.test.pre.constant;

import io.swagger.v3.oas.annotations.media.Schema;

public enum RequestStatus {
	@Schema(description = "Issue is in-progess")IN_PROGRESS,
	@Schema(description = "Issue is completed")COMPLETED,
	@Schema(description = "Issue is canceled")CANCELED
}
