package pl.mkotra.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mkotra.demo.core.PointsService;
import pl.mkotra.demo.model.PointsByMonth;

import java.util.List;

@RestController
@RequestMapping("/points")
public class PointsController {

    private final PointsService pointsService;

    public PointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @GetMapping
    public List<PointsByMonth> calculatePoints() {
        return pointsService.pointsByMonth();
    }

}
