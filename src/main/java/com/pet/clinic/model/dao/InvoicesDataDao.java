package com.pet.clinic.model.dao;

import com.pet.clinic.database.DbConnect;
import com.pet.clinic.model.InvoicesData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InvoicesDataDao {

    public static boolean insertInvoicesData(InvoicesData invoicesData){
        String query = "insert into invoicesData(invoiceId,item,itemType,description,quantity,price,priceTotal)" +
                " values(?,?,?,?,?,?,?)";
        Connection con = DbConnect.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,invoicesData.getInvoiceId());
            ps.setString(2,invoicesData.getItem());
            ps.setString(3,invoicesData.getItemType());
            ps.setString(4,invoicesData.getDescription());
            ps.setInt(5,invoicesData.getQuantity());
            ps.setDouble(6,invoicesData.getPrice());
            ps.setDouble(7,invoicesData.getPriceTotal());

            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
