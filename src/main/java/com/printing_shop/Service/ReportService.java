package com.printing_shop.Service;

import com.printing_shop.dtoRequest.ReportRequestDTO;
import com.printing_shop.dtoResponse.ReportResponseDTO;

import java.util.List;

public interface ReportService {
    ReportResponseDTO submitReport(ReportRequestDTO request);
    List<ReportResponseDTO> getAllReports();
    ReportResponseDTO updateStatus(Long reportId, String status);
}