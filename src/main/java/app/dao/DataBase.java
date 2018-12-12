package app.dao;

public class DataBase {
    /**
     * создать базу данных
     */
    public void createDb() {
        //open sql script
        //execute sql script

//        URL url1 = DictionaryDaoImplTest.class.getClassLoader()
//                .getResource("student_project.sql");
//        URL url2 = DictionaryDaoImplTest.class.getClassLoader()
//                .getResource("student_data.sql");
//
//        List<String> str1 = Files.readAllLines(Paths.get(url1.toURI()));
//        String sql1 = str1.stream().collect(Collectors.joining());
//
//        List<String> str2 = Files.readAllLines(Paths.get(url2.toURI()));
//        String sql2 = str2.stream().collect(Collectors.joining());
//
//        try (Connection con = ConnectionBuilder.getConnection();
//             Statement stmt = con.createStatement();
//        ) {
//            stmt.executeUpdate(sql1);
//            stmt.executeUpdate(sql2);
//        }
    }

    /**
     * проверить существование базы данных
     * @return true if data base is exist
     */
    public boolean isExist() {
        return false;
    }
}
