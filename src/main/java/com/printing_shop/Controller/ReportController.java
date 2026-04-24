package com.printing_shop.Controller;

import com.printing_shop.dtoRequest.ReportRequestDTO;
import com.printing_shop.dtoResponse.ReportResponseDTO;
import com.printing_shop.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin("*") // Added for frontend connectivity
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponseDTO> createReport(@RequestBody ReportRequestDTO dto) {
        // This will now work because ReportRequestDTO uses Long for IDs
        return ResponseEntity.ok(reportService.submitReport(dto));
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<ReportResponseDTO>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    // Changed from @PatchMapping to @PutMapping
    // Changed Integer id to Long id to match the Service
    @PutMapping("/admin/{id}")
    public ResponseEntity<ReportResponseDTO> updateReportStatus(
            @PathVariable Long id, 
            @RequestParam String status) {
        return ResponseEntity.ok(reportService.updateStatus(id, status));
    }
}