package com.example.microservice.test.pre.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "routing_rules")
public class RoutingRule {
	@Id
	@Column(name = "request_type")
	private String reqeustType;

	@Column(name = "service_url")
	private String serviceUrl;
}
