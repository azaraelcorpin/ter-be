package com.k12.ter.DataSource_TER.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.k12.ter.DataSource_TER.model.K12_Registration;


public interface GeneralRepo extends JpaRepository<K12_Registration,Long>{
    
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    @Query(value = "SELECT id FROM leave_application WHERE leave_type_id = :leave_type_id LIMIT 1",nativeQuery = true)
    List <Long> hasLeaveType(@Param("leave_type_id")Long leave_type_id);

    // @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    // @Query(value = "SELECT * FROM leave_application WHERE "+
    // " (:empId IS NULL OR emp_id LIKE :empId% )"
    // +" AND (:dateFrom IS NULL OR application_date >= :dateFrom )"
    // +" AND (:dateTo IS NULL OR application_date <= :dateTo )"
    // +" AND (:transactionReferrenceId IS NULL OR transaction_referrence_id LIKE %:transactionReferrenceId% )"
    // +" AND (:department IS NULL OR department LIKE %:department% )"
    // +" AND (:position IS NULL OR position LIKE %:position% )"
    // +" AND (:employeeName IS NULL OR (lastname LIKE %:employeeName% OR firstname LIKE %:employeeName% OR middlename LIKE %:employeeName%) )"
    // +" AND (:leaveTypeId IS NULL OR leave_type_id = :leaveTypeId )"
    // +" AND (:leaveDetails IS NULL OR leave_details LIKE %:leaveDetails% )"
    // +" AND (:leaveDate IS NULL OR leave_dates LIKE %:leaveDate% )"
    // +" AND (:commutation IS NULL OR commutation LIKE %:commutation% )"
    // +" AND (:status IS NULL OR status LIKE %:status% )"
    // ,nativeQuery = true)
    // Page <LeaveApplication> searchLeaveApplications(
    //     @Param("empId")String empId,
    //     @Param("dateFrom")Date dateFrom,
    //     @Param("dateTo")Date dateTo,
    //     @Param("transactionReferrenceId")String transactionReferrenceId,
    //     @Param("department")String department,
    //     @Param("position")String position,
    //     @Param("employeeName")String employeeName,
    //     @Param("leaveTypeId")Long leaveTypeId,
    //     @Param("leaveDetails")String leaveDetails,
    //     @Param("leaveDate")String leaveDate,
    //     @Param("commutation")String commutation,
    //     @Param("status")String status,
    //     Pageable pageable 
    //     );

        @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
        @Query(value = "SELECT r.subjcode, r.section, s.days,s.time,s.room, f.facultyid,f.fullname "
        + "FROM ter.k12_registration r, ter.k12_semsubject s, ter.k12_faculty f "
        + "WHERE r.email = 'azelamaye.arbilo@msugensan.edu.ph' "
        + "and r.sy = '2022-2023' "
        + "and r.subjcode =s.subjcode and r.section =s.section and r.sy =s.sy "
        + "and s.facultyid = f.facultyid ",
        countQuery = "SELECT r.subjcode, r.section, s.days,s.time,s.room, f.facultyid,f.fullname " ///countQuery is required for pageable response
        + "FROM ter.k12_registration r, ter.k12_semsubject s, ter.k12_faculty f "
        + "WHERE r.email = 'azelamaye.arbilo@msugensan.edu.ph' "
        + "and r.sy = '2022-2023' "
        + "and r.subjcode =s.subjcode and r.section =s.section and r.sy =s.sy "
        + "and s.facultyid = f.facultyid "  
        ,nativeQuery = true)
        List <Map<String,Object>> TestCOR();    
}
