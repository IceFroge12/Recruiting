package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.RecruitmentDAO;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.service.RecruitmentService;

import java.util.List;

/**
 * @author Chalienko  22.04.2016.
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
    public Recruitment getRecruitmentByName(String name) {
        return recruitmentDAO.getRecruitmentByName(name);
    }

    @Override
    public int updateRecruitment(Recruitment recruitment) {
        return recruitmentDAO.updateRecruitment(recruitment);
    }

    @Override
    public boolean addRecruitment(Recruitment recruitment) {
        return recruitmentDAO.addRecruitment(recruitment);
    }

    @Override
    public int deleteRecruitment(Recruitment recruitment) {
        return recruitmentDAO.deleteRecruitment(recruitment);
    }

    @Override
    public List<Recruitment> getAll() {
        return recruitmentDAO.getAll();
    }

	@Override
	public Recruitment getCurrentRecruitmnet() {
		return recruitmentDAO.getCurrentRecruitmnet();
	}

}
