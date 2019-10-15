package com.kennatech.coating.appointment.dao;

import com.kennatech.coating.appointment.pojo.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppointmentDao extends JpaRepository<Appointment,String>, JpaSpecificationExecutor<Appointment> {


}
