package com.productShop.inventarization.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productShop.inventarization.model.dto.GeneralStatistic;
import com.productShop.inventarization.service.GraphServiceImpl;
import com.productShop.inventarization.service.StatisticServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.productShop.inventarization.service.ConsumerTokenUtil.getUserToken;

@Controller
@RequestMapping("/statistic")
@AllArgsConstructor
public class StatisticController {
    private final StatisticServiceImpl statisticService;
    private final GraphServiceImpl graphService;

    @GetMapping("/")
    public String getStatistic(Model model, HttpSession session) {
        String token = getUserToken(session);
        List<GeneralStatistic> generalStatistics = statisticService.getGeneralStatistics(token);

        Map<LocalDate, Double> graphData = graphService.generateGeneralSellsGraph(generalStatistics);
        model.addAttribute("chartData", graphData);

        return "statistic-page";
    }

    @PostMapping("/update-chart-data")
    public void updateChartData(@RequestParam("category") String category, HttpSession session, HttpServletResponse response) {
        String token = getUserToken(session);

        Map<LocalDate, Double> graph = graphService.generateCategorySellHistoryGraph(category, token);

        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(response.getWriter(), graph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PutMapping("/update-chart-data")
    public void updateChartDataByDate(@RequestParam("startDate") String startDate,
                                      @RequestParam("endDate") String endDate,
                                      @RequestParam("category") String category,
                                      HttpSession session,
                                      HttpServletResponse response) {
        String token = getUserToken(session);

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        Map<LocalDate, Double> graph = graphService.generateCategorySellHistoryGraph(start, end, category, token);

        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(response.getWriter(), graph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
