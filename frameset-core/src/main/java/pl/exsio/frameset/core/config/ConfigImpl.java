/* 
 * The MIT License
 *
 * Copyright 2014 exsio.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.exsio.frameset.core.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import pl.exsio.frameset.core.config.ex.ConfigException;

public class ConfigImpl implements Config {

    private final DataSource ds;

    private final String insertQuery;

    private final String selectQuery;

    private final String updateQuery;

    private final String deleteQuery;

    public ConfigImpl(DataSource ds, String insertQuery, String updateQuery, String selectQuery, String deleteQuery) {
        this.ds = ds;
        this.insertQuery = insertQuery;
        this.updateQuery = updateQuery;
        this.selectQuery = selectQuery;
        this.deleteQuery = deleteQuery;
    }

    @Override
    public int setValue(String key, String value) {
        boolean exists = this.getValue(key) != null;
        Connection conn = null;
        try {
            conn = this.ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(exists ? this.updateQuery : this.insertQuery);
            pstmt.setString(exists ? 2 : 1, key);
            pstmt.setString(exists ? 1 : 2, value);
            return pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new ConfigException(ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConfigImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getValue(String key) {
        Connection conn = null;
        try {
            conn = this.ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(this.selectQuery);
            pstmt.setString(1, key);
            pstmt.execute();
            ResultSet result = pstmt.getResultSet();
            result.first();
            try {
                return result.getString(1);
            } catch (SQLException ex) {
                return null;
            }
        } catch (SQLException ex) {
            throw new ConfigException(ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConfigImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean getBoolean(String key) {
        return Boolean.valueOf(this.getValue(key));
    }

    @Override
    public long getLong(String key) {
        return Long.valueOf(this.getValue(key));
    }

    @Override
    public double getDouble(String key) {
        return Double.valueOf(this.getValue(key));
    }

    @Override
    public int removeValue(String key) {
        boolean exists = this.getValue(key) != null;
        if (exists) {
            Connection conn = null;
            try {
                conn = this.ds.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(this.deleteQuery);
                pstmt.setString(1, key);
                return pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new ConfigException(ex);
            } finally {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ConfigImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            return -1;
        }

    }

}
