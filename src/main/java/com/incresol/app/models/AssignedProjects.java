package com.incresol.app.models;

import java.util.List;

import lombok.Data;

@Data
public class AssignedProjects {
	private String bpId;
	private List<Integer> proIds;
	private List<Integer> taskIds;
}
