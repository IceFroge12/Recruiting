package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.RecruitmentDAO;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.service.RecruitmentService;

import java.util.List;

/**
 * Created by Chalienko on 22.04.2016.
 */
public class RecruitmentServiceImpl implements RecruitmentService {

    private RecruitmentDAO recruitmentDAO;

    public RecruitmentServiceImpl(RecruitmentDAO recruitmentDAO) {
        this.recruitmentDAO = recruitmentDAO;
    }

    @Override
    public Recruitment getRecruitmentById(Long id) {
        return recruitmentDAO.getRecruitmentById(id);
    }

    @Override
    public List<Recruitment> getAll() {
        return recruitmentDAO.getAll();
    }
}
