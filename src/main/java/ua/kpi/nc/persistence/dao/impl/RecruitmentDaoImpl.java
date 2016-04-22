package ua.kpi.nc.persistence.dao.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.kpi.nc.persistence.dao.RecruitmentDAO;
import ua.kpi.nc.persistence.model.Recruitment;
import ua.kpi.nc.persistence.model.impl.real.RecruitmentImpl;
import ua.kpi.nc.persistence.util.JdbcTemplate;
import ua.kpi.nc.persistence.util.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Chalienko on 22.04.2016.
 */
public class RecruitmentDaoImpl extends JdbcDaoSupport implements RecruitmentDAO {
    private static Logger log = LoggerFactory.getLogger(RecruitmentDaoImpl.class.getName());

    public RecruitmentDaoImpl(DataSource dataSource) {
        this.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    private static final String sqlGetRecruitmentById = "SELECT r.id, r.name,r.start_date,r.end_date," +
            "r.max_general_group, r.max_advanced_group, r.registration_deadline, r.schedule_choices_deadline," +
            " r.schedule_choices_deadline, r.students_on_interview ,r.time_interview_soft, r.time_interview_tech," +
            "r.number_tech_interviewers, r.number_soft_interviewers, r.number_of_hours\n" +
            "FROM \"recruitment\" r\n" +
            "WHERE r.id = ?;";

    private static final String sqlGetRecruitmentByName = "SELECT r.id, r.name,r.start_date,r.end_date," +
            "r.max_general_group, r.max_advanced_group, r.registration_deadline, r.schedule_choices_deadline," +
            " r.schedule_choices_deadline, r.students_on_interview ,r.time_interview_soft, r.time_interview_tech," +
            "r.number_tech_interviewers, r.number_soft_interviewers, r.number_of_hours\n" +
            "FROM \"recruitment\" r\n" +
            "WHERE r.name = ?;";

    private static final String sqlGetAll = "SELECT r.id, r.name,r.start_date,r.end_date," +
            "r.max_general_group, r.max_advanced_group, r.registration_deadline, r.schedule_choices_deadline," +
            " r.schedule_choices_deadline, r.students_on_interview ,r.time_interview_soft, r.time_interview_tech," +
            "r.number_tech_interviewers, r.number_soft_interviewers, r.number_of_hours\n" +
            "FROM \"recruitment\" r\n";

    private static final String sqlUpdate = "UPDATE \"recruitment\" " +
            "SET name = ? , start_date = ?,\n" +
            "end_date = ?, max_general_group = ?, max_advanced_group = ?, registration_deadline = ?," +
            "schedule_choices_deadline = ?, students_on_interview = ?, time_interview_tech = ?, " +
            "time_interview_soft = ?, number_tech_interviews = ?, number_soft_interview = ?, number_of_hours = ?\n" +
            "WHERE recruitment.id = ?;";

    private static final String sqlInsert = "INSERT INTO \"recruitment\"(name, start_date," +
            "end_date, max_general_group, max_advanced_group, registration_deadline, schedule_choices_deadline, " +
            "students_on_interview, time_interview_tech, time_interview_soft, number_tech_interviews," +
            "number_soft_interview, number_of_hours) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";

    private static final String sqlDelete = "DELETE FROM \"recruitment\" WHERE \"recruitment\".id = ?;";

    @Override
    public Recruitment getRecruitmentById(Long id) {
        if (log.isInfoEnabled()){
            log.info("Looking for recruitment with id = " + id);
        }
        return this.getJdbcTemplate().queryWithParameters(sqlGetRecruitmentById, new RecruitmentExtractor(), id);
    }

    @Override
    public Recruitment getRecruitmentByName(String name) {
        if (log.isInfoEnabled()){
            log.info("Looking for recruitment with name = " + name);
        }
        return this.getJdbcTemplate().queryWithParameters(sqlGetRecruitmentByName, new RecruitmentExtractor(), name);
    }

    @Override
    public int updateRecruitment(Recruitment recruitment) {
        if (log.isInfoEnabled()){
            log.info("Update recruitment with name = " + recruitment.getName());
        }
        return this.getJdbcTemplate().update(sqlUpdate,recruitment.getName(), recruitment.getStartDate(),
                recruitment.getEndDate(), recruitment.getMaxGeneralGroup(), recruitment.getMaxAdvancedGroup(),
                recruitment.getRegistrationDeadline(), recruitment.getScheduleChoicesDeadline(),
                recruitment.getStudentsOnInterview(),recruitment.getTimeInterviewTech(),recruitment.getTimeInterviewSoft(),
                recruitment.getNumberTechInterviewers(),recruitment.getNumberSoftInterviewers(),
                recruitment.getNumberOfDays(), recruitment.getId());
    }

    @Override
    public boolean addRecruitment(Recruitment recruitment) {
        if (log.isInfoEnabled()){
            log.info("Insert recruitment with name = " + recruitment.getName());
        }
        return this.getJdbcTemplate().insert(sqlInsert,recruitment.getName(), recruitment.getStartDate(),
                recruitment.getEndDate(), recruitment.getMaxGeneralGroup(), recruitment.getMaxAdvancedGroup(),
                recruitment.getRegistrationDeadline(), recruitment.getScheduleChoicesDeadline(),
                recruitment.getStudentsOnInterview(),recruitment.getTimeInterviewTech(),recruitment.getTimeInterviewSoft(),
                recruitment.getNumberTechInterviewers(),recruitment.getNumberSoftInterviewers(),
                recruitment.getNumberOfDays()) > 0;
    }

    @Override
    public int deleteRecruitment(Recruitment recruitment) {
        if (log.isInfoEnabled()) {
            log.info("Delete Recruitment with id = " + recruitment.getId());
        }
        return this.getJdbcTemplate().update(sqlDelete, recruitment.getId());
    }

    @Override
    public List<Recruitment> getAll() {
        if (log.isInfoEnabled()){
            log.info("Get all recruitment");
        }
        return this.getJdbcTemplate().queryForList(sqlGetAll, new RecruitmentExtractor());
    }


    private final class RecruitmentExtractor implements ResultSetExtractor<Recruitment> {
        @Override
        public Recruitment extractData(ResultSet resultSet) throws SQLException {
            Recruitment recruitment = new RecruitmentImpl();
            recruitment.setId(resultSet.getLong("id"));
            recruitment.setEndDate(resultSet.getTimestamp("end_date"));
            recruitment.setName(resultSet.getString("name"));
            recruitment.setStartDate(resultSet.getTimestamp("start_date"));
            recruitment.setMaxGeneralGroup(resultSet.getInt("max_general_group"));
            recruitment.setMaxAdvancedGroup(resultSet.getInt("max_advanced_group"));
            recruitment.setRegistrationDeadline(resultSet.getTimestamp("registration_deadline"));
            recruitment.setScheduleChoicesDeadline(resultSet.getTimestamp("schedule_choices_deadline"));
            recruitment.setStudentsOnInterview(resultSet.getInt("students_on_interview"));
            recruitment.setTimeInterviewSoft(resultSet.getInt("time_interview_soft"));
            recruitment.setTimeInterviewTech(resultSet.getInt("time_interview_tech"));
            recruitment.setNumberSoftInterviewers(resultSet.getInt("number_soft_interview"));
            recruitment.setNumberTechInterviewers(resultSet.getInt("number_tech_interview"));
            recruitment.setNumberOfDays(resultSet.getInt("number_of_hours"));
            return recruitment;
        }
    }
}
