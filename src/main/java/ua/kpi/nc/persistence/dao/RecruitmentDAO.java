package ua.kpi.nc.persistence.dao;

import ua.kpi.nc.persistence.model.Recruitment;

import java.sql.Connection;
import java.util.Set;

/**
 * Created by Vova on 21.04.2016.
 */
public interface RecruitmentDAO  {

    Recruitment getRecruitmentById(Long id);

    Recruitment getRecruitmentByName(String name);

    boolean updateRecruitment(Recruitment recruitment);

    boolean addRecruitment(Recruitment recruitment);

    int deleteRecruitment(Recruitment recruitment);

    Set<Recruitment> getAll();

}
