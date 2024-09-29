package com.yuva.app.utils;

import java.util.List;

import com.yuva.app.dto.TaskDto;

public record TaskPageResponse(List<TaskDto> taskDtos,
		
		                      int pageNumber,
		
		                      int pageSize,
		
		                      long totalElements,
		
		                      int totalPages,
		
		                      boolean isLast) {

}
