package pl.mkotra.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mkotra.demo.core.service.PointsService;
import pl.mkotra.demo.core.model.PointsByMonth;

import java.util.List;

@RestController
@RequestMapping("/points")
public class PointController {

    private final PointsService pointsService;

    public PointController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @GetMapping("/database")
    public List<PointsByMonth> calculatePointsOnDatabase() {
        return pointsService.calculateOnDatabase();
    }

    @GetMapping("/java")
    public List<PointsByMonth> calculatePointsInJava() {
        return pointsService.calculatePointsInJava();
    }


}
