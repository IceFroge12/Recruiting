package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.Recruitment;

import java.sql.Connection;
import java.util.Set;

/**
 * @author Korzh
 */
public interface RecruitmentDAO {

    Recruitment getRecruitmentById(Long id);

    Recruitment getRecruitmentByName(String name);

    int addRecruitment(Recruitment recruitment);

    int updateRecruitment(Recruitment recruitment);


    int deleteRecruitment(Recruitment recruitment);

    Set<Recruitment> getAll();

}
