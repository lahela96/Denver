package com.colorado.denver.services.persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.LoggerFactory;

import com.colorado.denver.model.BaseEntity;
import com.colorado.denver.tools.DenverConstants;

public class HibernateIdGenerator implements IdentifierGenerator {
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HibernateIdGenerator.class);

	@Override
	public Serializable generate(SessionImplementor sessionImpl, Object obj) throws HibernateException {
		Serializable result = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			BaseEntity<?> clazz = (BaseEntity<?>) obj;

			String className = clazz.getClass().getSimpleName();
			className.toLowerCase();
			LOGGER.info("Generating ID for class: " + className);

			String prefix = clazz.getPrefix();
			String sequence = prefix + DenverConstants.UNDERSCORE + "sequence";
			connection = sessionImpl.connection();
			statement = connection.createStatement();
			statement.execute("DO\n$$\nBEGIN\nCREATE SEQUENCE public." + className
					+ "_sequence minvalue 0 start with 0;\nEXCEPTION WHEN duplicate_table THEN\nEND\n$$ LANGUAGE plpgsql;");
			resultSet = statement.executeQuery("SELECT  NEXTVAL('" + sequence + "')");
			if (resultSet.next()) {
				int nextValue = resultSet.getInt(1);
				String suffix = String.format("%05d", nextValue + 1);
				result = prefix.concat(DenverConstants.UNDERSCORE.concat(suffix)); // className_identifier
				System.out.println("Custom generated Sequence value : " + result);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
