package com.domie.streamer.Service.util;

import com.domie.streamer.Model.Video;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CreateThumbnail  {

	public static void createThumbNail(String srcFile, String destFile) throws InterruptedException, IOException {
		Runtime run = Runtime.getRuntime();

		Process p = null;
		String cmd = "ffmpeg -i "+srcFile+" -ss 00:00:14.435 -vframes 1 "+destFile;
		p = run.exec(cmd);

		p.getErrorStream();
		p.waitFor();

		p.destroy();
	}

	public static StringBuilder getVideoDuration(String srcFile) throws InterruptedException, IOException {
		ProcessBuilder processBuilder = new ProcessBuilder();

		processBuilder.command("bash", "-c", "ffprobe -v error -show_entries format=duration -of default=noprint_wrappers=1:nokey=1 " +srcFile);

		Process process = processBuilder.start();

		StringBuilder output = new StringBuilder();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}

		int exitVal = process.waitFor();
		if (exitVal == 0)
			System.out.println("Success!");
			return output;
	}
}
