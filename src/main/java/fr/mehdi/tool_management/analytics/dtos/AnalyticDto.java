package fr.mehdi.tool_management.analytics.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticDto {

    private List<AnalyticItemDto>       data;
    private AnalyticSummaryDto      summary;

    public AnalyticDto(List<AnalyticItemDto> data) {
        this.data = data;
    }
    
}
