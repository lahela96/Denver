package com.colorado.denver.services.codeExecution;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.slf4j.LoggerFactory;

import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

public interface JavaScriptExecutor {
	final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JavaExecutor.class);

	public default String executeJavaScript(String inputType, String outputType, String excInput, String code) throws SecurityException {
		String result = "";
		try {
			NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
			ScriptEngine nashorn = factory.getScriptEngine(new NoJavaFilter());
			JSObject object = (JSObject) nashorn.eval(code);
			Invocable invocable = (Invocable) nashorn;
			Object results = invocable.invokeFunction("test", excInput);

			// call that anon function
			System.out.println("Object way: " + object.call(null, excInput));
			System.out.println("Invoke way: " + result);
			System.out.println("Invoke way class: " + result.getClass());
			result = results.toString();
		} catch (ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return result;

	}

	public class NoJavaFilter implements ClassFilter {

		@Override
		public boolean exposeToScripts(String s) {
			return false;
		}
	}

}
