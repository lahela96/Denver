package com.colorado.denver.services.codeExecution;

import org.slf4j.LoggerFactory;

import com.colorado.denver.model.Solution;
import com.colorado.denver.tools.DenverConstants;

public class SolutionExecutor implements JavaExecutor, JavaScriptExecutor {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SolutionExecutor.class);

	Solution sol;
	String inputType;
	String outputType;
	String excInput;
	String code;

	public SolutionExecutor(Solution sol) {
		this.sol = sol;
		this.inputType = sol.getExercise().getInputType();
		this.outputType = sol.getExercise().getOutputType();
		this.excInput = sol.getExercise().getInput();
		this.code = sol.getCode();

	}

	public Solution execute() {
		String answer = "";
		String message = "";
		if (sol.getExercise().getLanguage().equals(DenverConstants.JAVA)) {
			answer = executeJava(inputType, outputType, excInput, code);
			if (answer.startsWith(DenverConstants.JAVA_EXCEPTION_THROWN)) {
				answer = DenverConstants.JAVA_EXCEPTION_THROWN;
				message = answer.replaceAll(DenverConstants.JAVA_EXCEPTION_THROWN, "");
			}
		} else {

		}

		if (sol.getExercise().getAnwswer().equals(answer)) {
			sol.setCorrect(true);
			LOGGER.info("Answer for entity " + sol.getId() + " correct with value: " + answer);
			message = DenverConstants.JAVA_RESULT_CORRECT;
		} else {
			LOGGER.info("Answer for entity " + sol.getId() + " NOT correct with value: " + answer);
			message = DenverConstants.JAVA_RESULT_WRONG;
			sol.setCorrect(false);
		}
		sol.setAnwswer(answer);
		sol.setMessage(message);
		// Reset
		sol.setHasBeenModified(false);
		return sol;
	}

}
