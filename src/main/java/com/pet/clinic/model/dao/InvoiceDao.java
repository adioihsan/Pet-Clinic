package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.Invoice;

import java.sql.*;

public class InvoiceDao {

    public static int insertInvoice(Invoice invoice){
        int id =0;
        String query= "insert into invoice(petId,medicRecordId,totalAmount,createDate,userId)" +
                " values(?,?,?,?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,invoice.getPetId());
            ps.setInt(2,invoice.getMedicRecordId());
            ps.setDouble(3,invoice.getTotalAmount());
            ps.setDate(4, Date.valueOf(invoice.getCreateDate()));
            ps.setInt(5,invoice.getUserId());
            if(ps.executeUpdate() > 0){
                ResultSet res = con.createStatement().executeQuery("select LAST_INSERT_ID()");
                res.next();
                id = res.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }
}
