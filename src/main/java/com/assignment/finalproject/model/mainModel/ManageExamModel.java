package com.assignment.finalproject.model.mainModel;

import com.assignment.finalproject.Cradutil.CrudUtil;
import com.assignment.finalproject.db.DBConnection;
import com.assignment.finalproject.dto.sub.ExamDTO;
import com.assignment.finalproject.dto.sub.ExamScheduleDTO;
import com.assignment.finalproject.dto.sub.ExamSubjectIdDTO;
import com.assignment.finalproject.dto.tm.ManageExamTM;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageExamModel {

    ExamShedulModel examShedulModel = new ExamShedulModel();
    ExamSubjectModel examSubjectModel = new ExamSubjectModel();

    public ArrayList<ManageExamTM> getSelectExam(String grade) throws SQLException {
        String sql = "SELECT " +
                "es.exam_schedule_id AS examShedulID, " +
                "es.exam_id AS examID, " +
                "es.hall_id AS hallID, " +
                "e.e_name AS examName, " +
                "e.grade AS grade, " +
                "es.date AS date, " +
                "es.time AS time, " +
                "sed.sub_id AS subjectID " +
                "FROM exam e " +
                "JOIN exam_schedule es ON e.ex_id = es.exam_id " +
                "LEFT JOIN sub_exam_detail sed ON e.ex_id = sed.exa_id " +
                "WHERE e.grade = ?";

        ResultSet rst = CrudUtil.execute(sql, grade);

        ArrayList<ManageExamTM> manageExamTMS = new ArrayList<>();

        while (rst.next()) {
            ManageExamTM manageExamTM = new ManageExamTM(
                    rst.getString("examShedulID"),
                    rst.getString("examID"),
                    rst.getString("hallID"),
                    rst.getString("examName"),
                    rst.getString("grade"),
                    rst.getString("date"),
                    rst.getString("time"),
                    rst.getString("subjectID")
            );
            manageExamTMS.add(manageExamTM);
        }
        return manageExamTMS;
    }

    public boolean updateExamAndSchedule(String examID, String examName, String grade,String hallName, String examTime, String examDate, String examShedulID) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isExamUpdated = CrudUtil.execute(
                    "UPDATE exam SET e_name = ?, grade = ? WHERE ex_id = ?",
                    examName,
                    grade,
                    examID
            );

            if(isExamUpdated){
                boolean isScheduleUpdated = examShedulModel.updateExamSchedule(examID,hallName, examTime, examDate, examShedulID
                );

                if(isScheduleUpdated){
                    boolean isExamSubjectIdUpdate = examSubjectModel.updateExamSubjectId( examID, grade);

                    if(isExamSubjectIdUpdate){
                        connection.commit(); //
                        return true;
                    }
                    else{
                        connection.rollback();
                        return false;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        }
        return false;
    }

    public boolean deleteExam(String examID) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isDeleted = CrudUtil.execute("DELETE FROM sub_exam_detail WHERE exa_id = ?", examID);
            if (isDeleted) {
                boolean isScheduleDeleted = examShedulModel.deleteExamSchedule(examID);
                if (isScheduleDeleted) {
                    boolean isDeletedSubject = examSubjectModel.deleteSubject(examID);
                    if (isDeletedSubject) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        }
    }
}







