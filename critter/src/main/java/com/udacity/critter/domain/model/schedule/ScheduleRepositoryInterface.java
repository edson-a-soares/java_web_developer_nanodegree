package com.udacity.critter.domain.model.schedule;

import java.util.Collection;

public interface ScheduleRepositoryInterface {

    public Schedule add(Schedule schedule);
    public Collection<Schedule> list();
    Collection<Schedule> findByPet(long id);
    public Collection<Schedule> findByCustomer(long id);
    public Collection<Schedule> findByEmployee(long id);

}
