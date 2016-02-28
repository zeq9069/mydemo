package com.kyrincloud.MysqlSharding;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * 数据源wrapper
 * @author kyrin
 *
 */
public abstract class AbstractShardingDataSourceWrapper implements DataSource {


	protected PrintWriter pw=new PrintWriter(System.out);
	
	public PrintWriter getLogWriter() throws SQLException {
		return pw;
	}

	public int getLoginTimeout() throws SQLException {
		throw new SQLException("not supper getLoginTimeout");
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		this.pw = out;
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		throw new SQLException("not supper setLoginTimeout");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return iface.isInstance(this);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		 if (isWrapperFor(iface)) {
	            return (T) this;
	        }
	        throw new SQLException(String.format("[%s] cannot be unwrapped as [%s]", getClass().getName(), iface.getName()));
	}

	public abstract Connection getConnection() throws SQLException;

	public abstract Connection getConnection(String username, String password) throws SQLException;

}
