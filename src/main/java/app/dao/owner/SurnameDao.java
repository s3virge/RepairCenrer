package app.dao.owner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SurnameDao {
    private int id;
    private String surname;

    //todo сформировать запросы к базе данных
    private static final String INSERT_SURNAME = "insert * from surname";
    private static final String SELECT_SURNAME = "select * from surname";

    private static final String GET_STREET = "SELECT street_code, street_name " +
            "FROM jc_street WHERE UPPER(street_name) LIKE UPPER(?)";

    private static final String INSERT_ORDER =
            "INSERT INTO jc_student_order(" +
                    " student_order_status, student_order_date, h_sur_name, " +
                    " h_given_name, h_patronymic, h_date_of_birth, h_passport_seria, " +
                    " h_passport_number, h_passport_date, h_passport_office_id, h_post_index, " +
                    " h_street_code, h_building, h_extension, h_apartment, h_university_id, h_student_number, " +
                    " w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_seria, " +
                    " w_passport_number, w_passport_date, w_passport_office_id, w_post_index, " +
                    " w_street_code, w_building, w_extension, w_apartment, w_university_id, w_student_number, " +
                    " certificate_id, register_office_id, marriage_date)" +
                    " VALUES (?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, " +
                    " ?, ?, ?, ?, ?, ?, " +
                    " ?, ?, ?);";

    /**
     * return id for surname in database
     * @param surname
     * @return id
     */
    public int getId(String surname) {
//        List<Street> result = new LinkedList<>();
//
//        try (Connection con = getConnection();
//             PreparedStatement stmt = con.prepareStatement(GET_STREET)) {
//
//            stmt.setString(1, "%" + pattern + "%");
//            ResultSet rs = stmt.executeQuery();

//            while (rs.next()) {
//                Street str = new Street(rs.getLong("street_code"),
//                        rs.getString("street_name"));
//                result.add(str);
//            }
//        }
//        catch (SQLException ex) {
//            logger.error(ex.getMessage(), ex);
//            throw new DaoException(ex);
//        }

        return id;
    }

    /**
     * save surname to database
     * @param surname
     */
    public void setSurname(String surname) {

//        Long result = -1L;
//
//        logger.debug("SO:{}", so);
//
//        try (Connection con = getConnection();
//             PreparedStatement stmt = con.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) {
//
//            con.setAutoCommit(false);
//            try {
//                // Header
//                stmt.setInt(1, StudentOrderStatus.START.ordinal());
//                stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
//
//                // Husband and wife
//                setParamsForAdult(stmt, 3, so.getHusband());
//                setParamsForAdult(stmt, 18, so.getWife());
//
//                // Marriage
//                stmt.setString(33, so.getMarriageCertificateId());
//                stmt.setLong(34, so.getMarriageOffice().getOfficeId());
//                stmt.setDate(35, java.sql.Date.valueOf(so.getMarriageDate()));
//
//                stmt.executeUpdate();
//
//                ResultSet gkRs = stmt.getGeneratedKeys();
//                if (gkRs.next()) {
//                    result = gkRs.getLong(1);
//                }
//                gkRs.close();
//
//                saveChildren(con, so, result);
//
//                con.commit();
//            } catch (SQLException ex) {
//                con.rollback();
//                throw ex;
//            }
//
//        } catch (SQLException ex) {
//            logger.error(ex.getMessage(), ex);
//            throw new DaoException(ex);
//        }

//        return result;
    }
}
