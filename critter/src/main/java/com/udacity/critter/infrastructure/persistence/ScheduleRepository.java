package com.udacity.critter.infrastructure.persistence;

import java.util.List;
import java.util.Collection;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import com.udacity.critter.domain.model.schedule.Schedule;
import com.udacity.critter.domain.model.schedule.ScheduleRepositoryInterface;

@Component
public class ScheduleRepository implements ScheduleRepositoryInterface {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public Schedule add(Schedule schedule) {
        return manager.merge(schedule);
    }

    @Override
    public List<Schedule> list() {
        return manager.createQuery("from Schedule", Schedule.class).getResultList();
    }

    @Override
    public Collection<Schedule> findByPet(long id) {
        Query query = manager.createNativeQuery(
            new StringBuilder()
                .append(" SELECT schedules.*")
                .append("    FROM schedules")
                .append("    JOIN schedules_pets sp ON schedules.schedule_id = sp.schedule_id")
                .append(" WHERE sp.pet_id = :pet_id")
                .toString(),
            Schedule.class
        ).setParameter("pet_id",  id);

        return query.getResultList();
    }

    @Override
    public List<Schedule> findByCustomer(long id) {
        Query query = manager.createNativeQuery(
            new StringBuilder()
                .append(" SELECT schedules.*")
                .append("    FROM schedules")
                .append("    JOIN schedules_pets sp ON schedules.schedule_id = sp.schedule_id")
                .append("    JOIN pets p ON sp.pet_id = p.pet_id")
                .append(" WHERE p.owner_id = :owner_id")
                .toString(),
            Schedule.class
        ).setParameter("owner_id",  id);

        return query.getResultList();
    }

    @Override
    public Collection<Schedule> findByEmployee(long id) {
        Query query = manager.createNativeQuery(
            new StringBuilder()
                .append(" SELECT schedules.*")
                .append("    FROM schedules")
                .append("    JOIN schedules_employees ON schedules.schedule_id = schedules_employees.schedule_id")
                .append(" WHERE schedules_employees.employee_id = :employee_id")
                .toString(),
            Schedule.class
        ).setParameter("employee_id",  id);

        return query.getResultList();
    }

}
