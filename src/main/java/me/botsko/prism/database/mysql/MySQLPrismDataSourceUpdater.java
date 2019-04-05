package me.botsko.prism.database.mysql;

import me.botsko.prism.database.PrismDataSourceUpdater;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created for use for the Add5tar MC Minecraft server
 * Created by benjamincharlton on 5/04/2019.
 */
public class MySQLPrismDataSourceUpdater implements PrismDataSourceUpdater {
    private MySQLPrismDataSource dataSource;

    public MySQLPrismDataSourceUpdater(MySQLPrismDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void v1_to_v2() {
    }

    public void v2_to_v3() {
    }

    public void v3_to_v4() {
    }

    public void v4_to_v5() {
    }

    public void v5_to_v6() {
        Connection conn = dataSource.getConnection();
        String prefix = dataSource.getPrefix();
        Statement st = null;
        String query;

        try {
            st = conn.createStatement();

            // Key must be dropped before we can edit colum types
            query = "ALTER TABLE `" + prefix + "data_extra` DROP FOREIGN KEY `" + prefix + "data_extra_ibfk_1`;";
            st.executeUpdate(query);

            query = "ALTER TABLE " + prefix + "data MODIFY id bigint(20) unsigned NOT NULL AUTO_INCREMENT";
            st.executeUpdate(query);

            query = "ALTER TABLE " + prefix
                    + "data_extra MODIFY extra_id bigint(20) unsigned NOT NULL AUTO_INCREMENT, MODIFY data_id bigint(20) unsigned NOT NULL";
            st.executeUpdate(query);

            // return foreign key
            /// BEGIN COPY PASTE Prism.setupDatabase()
            query = "ALTER TABLE `" + prefix + "data_extra` ADD CONSTRAINT `" + prefix
                    + "data_extra_ibfk_1` FOREIGN KEY (`data_id`) REFERENCES `" + prefix
                    + "data` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;";
            st.executeUpdate(query);
            /// END COPY PASTE
        }
        catch (SQLException e) {
            dataSource.handleDataSourceException(e);
        }
        finally {
            if (st != null)
                try {
                    st.close();
                }
                catch (SQLException e) {
                }
            if (conn != null)
                try {
                    conn.close();
                }
                catch (SQLException e) {
                }
        }
    }
}
