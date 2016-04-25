package ua.kpi.nc.service;

import ua.kpi.nc.persistence.model.Recruitment;

import java.util.List;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface RecruitmentService {

    Recruitment getRecruitmentById(Long id);

    List<Recruitment> getAll();
}
