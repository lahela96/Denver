package com.colorado.denver.tools;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.slf4j.LoggerFactory;

import com.colorado.denver.model.BaseEntity;

public class HibernateInterceptor extends EmptyInterceptor {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(HibernateInterceptor.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -2259042000674109919L;

	@Override
	public boolean onFlushDirty(Object entity,
			Serializable id,
			Object[] currentState,
			Object[] previousState,
			String[] propertyNames,
			Type[] types) {
		LOGGER.debug("Interceptor working in update!");
		boolean worked = false;
		if (entity instanceof BaseEntity<?>) {
			for (int i = 0; i < propertyNames.length; i++) {

				LOGGER.debug("I'm in: " + i + " for property: " + propertyNames[i]);
				if (currentState[i] == null) {
					try {

						LOGGER.debug("I'M in for NULLCHECK: " + i + " for property: " + propertyNames[i]);
						currentState[i] = previousState[i];
						LOGGER.debug("SUCESSFULLY WROTE BACK: " + i + " for property: " + propertyNames[i]);
						worked = true;
					} catch (Exception e) {
						LOGGER.debug("ERROR in" + i);
					}

				}
			}
			LOGGER.debug("Interceptor worked in update!");
			return worked;
		}
		return worked;
	}

	@Override
	public void afterTransactionCompletion(Transaction tx) {
		super.afterTransactionCompletion(tx);
	}

}