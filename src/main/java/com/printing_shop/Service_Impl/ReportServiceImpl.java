package com.printing_shop.Service_Impl;

import com.printing_shop.Enity.Order;
import com.printing_shop.Enity.Report;
import com.printing_shop.Enity.User;
import com.printing_shop.Repositories.OrderRepository;
import com.printing_shop.Repositories.ReportRepository;
import com.printing_shop.Repositories.UserRepository;
import com.printing_shop.Service.ReportService;
import com.printing_shop.dtoRequest.ReportRequestDTO;
import com.printing_shop.dtoRespose.ReportResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Override
    public ReportResponseDTO submitReport(ReportRequestDTO request) {
        // 1. Fetch User (ensure RequestDTO uses Long)
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        // 2. Fetch Order (ensure RequestDTO uses Long)
        Order order = orderRepo.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + request.getOrderId()));

        // 3. Create and Save Report
        Report report = new Report();
        report.setUser(user);
        report.setOrder(order);
        report.setSubject(request.getSubject());
        report.setDescription(request.getDescription());
        report.setStatus("PENDING");

        Report savedReport = reportRepo.save(report);
        
        // 4. Map to DTO and return (Don't return null!)
        return mapToResponseDTO(savedReport);
    }

    @Override
    public List<ReportResponseDTO> getAllReports() {
        return reportRepo.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReportResponseDTO updateStatus(Long reportId, String status) {
        Report report = reportRepo.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));

        report.setStatus(status.toUpperCase().trim());
        
        Report updatedReport = reportRepo.save(report);
        
        return mapToResponseDTO(updatedReport);
    }

    private ReportResponseDTO mapToResponseDTO(Report report) {
        if (report == null) return null;

        User user = report.getUser();
        Order order = report.getOrder();

        return ReportResponseDTO.builder()
                .reportId(report.getReportId())
                .customerName(user != null ? user.getFullName() : "Unknown User")
                .customerEmail(user != null ? user.getEmail() : "N/A")
                .orderId(order != null ? order.getOrderId() : null)
                .orderTotalPrice(order != null ? order.getTotalPrice() : null)
                .subject(report.getSubject())
                .description(report.getDescription())
                .status(report.getStatus())
                .createdAt(report.getCreatedAt())
                .build();
    }
}