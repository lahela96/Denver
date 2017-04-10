package com.colorado.denver.services.codeExecution;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.slf4j.LoggerFactory;

import com.colorado.denver.tools.DenverConstants;

import groovy.lang.GroovyClassLoader;

public interface JavaExecutor {
	final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JavaExecutor.class);

	public default String executeJava(String inputType, String outputType, String excInput, String code) throws SecurityException {

		System.out.println("IN TEST!");

		String result = "";

		try {

			// InputStream is = new FileInputStream("fibonacci.txt");
			// BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			// String line = buf.readLine();
			// StringBuilder sb = new StringBuilder();
			//
			// while (line != null) {
			// sb.append(line).append("\n");
			// line = buf.readLine();
			// }
			// buf.close();
			// String fileAsString = sb.toString();
			// System.out.println(fileAsString);
			System.out.println(code);
			GroovyClassLoader gcl = new GroovyClassLoader();
			Class<?> scriptClass = gcl.parseClass(code);
			String className = scriptClass.getSimpleName();
			Object scriptInstance = scriptClass.newInstance();
			Method[] methods = scriptClass.getDeclaredMethods();

			int input = Integer.parseInt(excInput);
			for (int i = 0; i < methods.length; i++) {
				try {

					switch (methods[i].getReturnType().getSimpleName()) {
					case "int":

						int res = (int) methods[i].invoke(scriptInstance, new Object[] { input });
						result = res + "";
						break;
					case "String":
						result = (String) methods[i].invoke(scriptInstance, new Object[] { input });
						break;

					case "boolean":

						boolean resultBool = (boolean) methods[i].invoke(scriptInstance, new Object[] { input });
						if (resultBool) {
							result = "true";
						} else {
							result = "false";
						}

						break;

					case "long":
						long resultLong = (long) methods[i].invoke(scriptInstance, new Object[] { input });
						result = resultLong + "";

						break;

					default:
						break;
					}
					System.out.println("Method name is: " + methods[i].getName());
					System.out.println("---");

				} catch (Exception e) {
					result = DenverConstants.JAVA_EXCEPTION_THROWN;
					result = result + "\n" + e.getMessage();
					e.printStackTrace();
				}

			}

			// int result = (int) scriptClass.getDeclaredMethod("fibonacci", int.class).invoke(scriptInstance, new Object[] { excInput });

			gcl.close();

			// CLeanup
			File fileTemp = new File(className + ".class");
			if (fileTemp.exists()) {
				fileTemp.delete();
			}

		} catch (

		InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Result is: " + result);
		return result;
	}
}
