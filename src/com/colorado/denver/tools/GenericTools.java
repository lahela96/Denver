package com.colorado.denver.tools;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.management.ReflectionException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.reflections.Reflections;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.colorado.denver.model.BaseEntity;

public class GenericTools implements ApplicationContextAware {
	private static final Reflections reflections = new Reflections(DenverConstants.COLORADO_PACKAGE);
	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GenericTools.class);

	private SessionFactory sessionFactory;

	/** The context. */
	private static ApplicationContext context;

	private Class<? extends BaseEntity> objectClass;

	public static Reflections getReflections() {
		return reflections;
	}

	public static ApplicationContext getApplicationContext() throws BeansException {
		Objects.requireNonNull(context,
				"Unable to get Spring Application!");
		return context;
	}

	public static <T extends Object> Set<Class<? extends T>> getSubTypesOf(Class<T> clazz) {

		final Set<Class<? extends T>> classes = getReflections().getSubTypesOf(clazz);

		return classes;
	}

	@SuppressWarnings("unchecked") // cast is safe as only databaseentities are allowed
	public <T extends BaseEntity> Class<T> getObjectClass() {
		return (Class<T>) objectClass;
	}

	public static <T> T getBean(Class<T> beanClass) {
		LOGGER.debug("Getting bean for  " + beanClass);
		T bean = context.getBean(beanClass);
		LOGGER.debug("Returned bean: {} " + bean + " (Hash: {}" + bean.hashCode());
		return bean;
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getModelClassForName(String className) throws ReflectionException {
		try {
			className = className.substring(0, 1).toUpperCase() + className.substring(1);

			return (Class<T>) Class.forName("com.colorado.denver.model." + className);

		} catch (final ClassNotFoundException e) {
			throw new ReflectionException(
					e);
		}
	}

	public static String returnClassName(Object clazz) {
		return clazz.getClass().getSimpleName();
	}

	public static <T> T newInstanceOf(String clazz) throws ReflectionException {
		return newInstanceOf(getModelClassForName(clazz));
	}

	public static <T> T newInstanceOf(Class<T> clazz) throws ReflectionException {
		return newInstanceOf(clazz, false);
	}

	public static <T> Constructor[] getConstructorForClass(Class<T> clazz) {
		return clazz.getDeclaredConstructors();
	}

	public static <T> T newInstanceOf(Class<T> clazz, boolean allowNonPublicConstructors) throws ReflectionException {
		try {
			LOGGER.debug("Trying to initialize instance of {" + clazz + "} - allow non public constructors? {" + allowNonPublicConstructors + "}");
			try (Stream<Constructor<?>> stream = Arrays.stream(clazz.getDeclaredConstructors())) {

				final Optional<Constructor<?>> constructor = stream
						.filter(c -> c.getParameterCount() == 0) // find constructor with no parameters, no matter if public or non-public
						.findFirst();

				final Constructor<?> c = constructor.orElse(null);
				if (allowNonPublicConstructors && !c.isAccessible()) {
					c.setAccessible(true);
				} else {

				}

				return (T) c.newInstance();

			}

		} catch (ReflectiveOperationException e) {
			throw new ReflectionException(e, "Unable to instantiate " + clazz);
		}
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		Session session;

		if (!sessionFactory.getCurrentSession().isOpen()) {
			LOGGER.info("Opening new session");
			session = sessionFactory.openSession();
		} else {
			session = sessionFactory.getCurrentSession();
		}
		if (session.getTransaction().getStatus() != TransactionStatus.ACTIVE) {
			session.beginTransaction();
			LOGGER.debug("Initialized transaction.");
		}

		return sessionFactory.getCurrentSession();
	} // initTransAction

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		context = ctx;

	}

}
