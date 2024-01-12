package com.incresol.app.models;

import java.util.List;

import lombok.Data;

@Data
public class OrgDetails {
	private String orgId;
	private List<String> bpIds;
}
