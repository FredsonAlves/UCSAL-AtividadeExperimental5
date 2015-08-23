//Extraído de: http://fernando-aires.blogspot.com/2012/02/commandline-uma-classe-java-para.html#ixzz3VS1Oqvrd
package br.ucsal.moduloClienteAE4.uteis;

import java.io.*;

public class LinhadeComando {
	// Needed to run commands in the command line
	private Runtime runtime;
	private Process process;

	// Output of the executed command
	private String stdinData;
	private String stderrData;

	// Constructor
	public LinhadeComando() {
		runtime = Runtime.getRuntime();
		process = null;
		stdinData = "";
		stderrData = "";
	}

	// Return executed command output data
	public String getStdinData() {
		return stdinData;
	}

	public String getStderrData() {
		return stderrData;
	}

	// Execute a single command with all its arguments
	public void execute(String command) {
		try {
			process = runtime.exec(command);
		} catch (Exception ex) {
			System.err.println(ex);
		}

		pullProcessData();
	}

	// Execute a single command in cmdArray,
	// define environment settings in envp and
	// set working directory to dir
	public void execute(String[] cmdArray, String[] envp, File dir) {
		try {
			process = runtime.exec(cmdArray, envp, dir);
		} catch (Exception ex) {
			System.err.println(ex);
		}

		pullProcessData();
	}

	// Obtain all data sent by the process to stdout and stderr
	private void pullProcessData() {
		try {
			StringBuffer stdinBuffer = new StringBuffer();
			StringBuffer stderrBuffer = new StringBuffer();

			InputStream stdinStream = process.getInputStream();
			InputStream stderrStream = process.getErrorStream();

			new InputStreamHandler(stdinBuffer, stdinStream);
			new InputStreamHandler(stderrBuffer, stderrStream);

			process.waitFor();

			stdinData = stdinBuffer.toString();
			stderrData = stderrBuffer.toString();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	// Private inner class used to handle input streams
	private class InputStreamHandler extends Thread {
		private InputStream m_stream;
		private StringBuffer m_captureBuffer;

		InputStreamHandler(StringBuffer captureBuffer, InputStream stream) {
			m_stream = stream;
			m_captureBuffer = captureBuffer;
			start();
		}

		public void run() {
			try {
				int nextChar;
				while ((nextChar = m_stream.read()) != -1)
					m_captureBuffer.append((char) nextChar);
			} catch (IOException ioex) {
				System.err.println(ioex);
			}
		}
	}
}