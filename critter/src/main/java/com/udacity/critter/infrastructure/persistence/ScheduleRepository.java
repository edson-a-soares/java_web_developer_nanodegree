package com.udacity.critter.infrastructure.persistence;

import java.util.List;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import javax.persistence.EntityNotFoundException;
import com.udacity.critter.domain.model.user.Customer;
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
    public Collection<Schedule> list() {
        return manager.createQuery("from Schedule", Schedule.class).getResultList();
    }

    @Override
    public Schedule findByPet(long id) {
        return null;
    }

    @Override
    public Collection<Schedule> findByCustomer(long id) {
        return null;
    }

    @Override
    public Schedule findByEmployee(long id) {
        return null;
    }

}
