package ua.kpi.nc.service.impl;

import ua.kpi.nc.persistence.dao.TimePriorityTypeDao;
import ua.kpi.nc.persistence.model.TimePriorityType;
import ua.kpi.nc.service.TimePriorityTypeService;

/**
 * @author Yaroslav Kruk on 4/23/16.
 *         e-mail: yakruck@gmail.com
 *         GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.7
 */
public class TimePriorityTypeServiceImpl implements TimePriorityTypeService {

    private TimePriorityTypeDao timePriorityTypeDao;

    public TimePriorityTypeServiceImpl(TimePriorityTypeDao timePriorityTypeDao) {
        this.timePriorityTypeDao = timePriorityTypeDao;
    }

    @Override
    public TimePriorityType getByID(Long id) {
        return timePriorityTypeDao.getByID(id);
    }

    @Override
    public TimePriorityType getByPriority(String priority) {
        return timePriorityTypeDao.getByPriority(priority);
    }
}
